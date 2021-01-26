package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.databinding.ActivityEditBinding
import com.example.desafiointegrador4.models.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_game_register.*

class EditActivity : AppCompatActivity() {
    private val CODE_IMG: Int = 100
    private lateinit var storageReference: StorageReference
    lateinit var binding: ActivityEditBinding
    private var game: Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)

        var jogo = intent.getSerializableExtra("game") as? Game

        if (jogo != null) {
            game = jogo

            setInfo(game)
        }

        binding.editGameBack.setOnClickListener {
            finish()
        }

        binding.ivSaveGame.setOnClickListener {
            if (game != null) {
                salvarDados(newInfoGame(game))
                Toast.makeText(this, "Informações atualizadas com sucesso!", Toast.LENGTH_SHORT)
                finish()
            } else {
                Toast.makeText(this, "Não foi possível atualizar as informações!", Toast.LENGTH_SHORT)
                finish()
            }
        }

        binding.photo.setOnClickListener {
            carregarImagem()
        }

        setContentView(binding.root)
    }

    fun carregarImagem() {
        storageReference = FirebaseStorage.getInstance().getReference(getUniqueKey())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Get Image"), CODE_IMG)
    }

    private fun getUniqueKey() = FirebaseFirestore.getInstance().collection("pegando chave").document().id

    fun salvarDados(game: Game) {
        val bancoDados = FirebaseFirestore.getInstance().collection("InfoGame")

        bancoDados.document(game.id).set(game)
    }

    fun newInfoGame(game: Game): Game {
        val nome = binding.gameName.text.toString()
        val data = binding.date.text.toString()
        val desc = binding.description.text.toString()

        return Game(nome, data, desc, game.urlImg, game.id)
    }

    fun setInfo(game: Game) {
        binding.gameName.setText(game.nome)
        binding.date.setText(game.dataCriacao)
        binding.description.setText(game.descricao)

        Picasso.get().load(game.urlImg).into(binding.cam)
    }

    //funçoes para upload da imagem
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE_IMG) {

            val uploadFile = storageReference.putFile(data!!.data!!)
            uploadFile.continueWithTask { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT)
                        .show()
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