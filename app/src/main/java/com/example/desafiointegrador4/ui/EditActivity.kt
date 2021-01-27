package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiointegrador4.databinding.ActivityEditBinding
import com.example.desafiointegrador4.models.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_register.*

class EditActivity : AppCompatActivity() {
    private val CODE_IMG: Int = 100
    private lateinit var storageReference: StorageReference
    lateinit var binding: ActivityEditBinding
    private var game: Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)

        //pegando o objeto que veio da activity anterior
        var jogo = intent.getSerializableExtra("game") as? Game

        //se não for nulo, vai carregar todas essas infos no layout
        if (jogo != null) {
            game = jogo

            setInfo(game)
        } else {
            sendMsg("Não foi possível carregar suas as informações!")
            finish()
        }

        //botão voltar
        binding.editGameBack.setOnClickListener {
            finish()
        }

        //botão para salvar todas as informações
        binding.ivSaveGame.setOnClickListener {
            if (game != null) {
                salvarDados(newInfoGame(game)) //pegar as informaçoes que forma editadas para dps salvar
                sendMsg("Informações atualizadas com sucesso!")
                finish()
            } else {
                sendMsg("Não foi possível atualizar as informações!")
                finish()
            }
        }

        //botão para trocar a imagem do game
        binding.photo.setOnClickListener {
            carregarImagem()
        }

        setContentView(binding.root)
    }

    //carregar no layout as informações que veio pela activity anterior
    fun setInfo(game: Game) {
        binding.gameName.setText(game.nome)
        binding.date.setText(game.dataCriacao)
        binding.description.setText(game.descricao)

        Picasso.get().load(game.urlImg).into(binding.cam)
    }

    //trocar por uma nova imagem do game
    fun carregarImagem() {
        storageReference = FirebaseStorage.getInstance().getReference(getUniqueKey())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Get Image"), CODE_IMG)
    }

    //pegando um id unico para a imagem do game
    private fun getUniqueKey() = FirebaseFirestore.getInstance().collection("pegando chave").document().id

    //salvando todas as informações que foram alteradas
    fun salvarDados(game: Game) {
        val bancoDados = FirebaseFirestore.getInstance().collection("InfoGame")

        bancoDados.document(game.id).set(game) //ele pegará o id do jogo que está sendo editado e vai sobreescrever as antigas informações pelas novas
    }

    //pegando todas as informações do game que foram e não foram alteradas e criando um novo Game, para retornar à função salvarDados()
    fun newInfoGame(game: Game): Game {
        val nome = binding.gameName.text.toString()
        val data = binding.date.text.toString()
        val desc = binding.description.text.toString()

        return Game(nome, data, desc, game.urlImg, game.id)
    }

    //função apenas de toast :)
    private fun sendMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //funçoes para upload da imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {

            val uploadFile = storageReference.putFile(data!!.data!!)
            uploadFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    sendMsg("Imagem carrregada com sucesso!")
                }
                storageReference!!.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val url = downloadUri!!.toString()
                        .substring(0, downloadUri.toString().indexOf("&token"))

                    Log.i("URL da Imagem", url)
                    game.urlImg = url

                    Picasso.get().load(url).into(cam)
                }
            }
        }
    }
}