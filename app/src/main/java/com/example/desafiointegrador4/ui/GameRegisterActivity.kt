package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.models.Game
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_register.*

class GameRegisterActivity : AppCompatActivity() {
    private val CODE_IMG: Int = 100
    private lateinit var storageReference: StorageReference
    private var game: Game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_register)

        registerGameBack.setOnClickListener {
            finish()
        }

        photo.setOnClickListener {
            carregarImagem()
        }

        iv_btnCadastroGame.setOnClickListener {
            game.nome = gameName.text.toString()
            game.dataCriacao = date.text.toString()
            game.descricao = description.text.toString()

            salvarDados()
            finish()
        }
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

    fun carregarImagem() {
        storageReference = FirebaseStorage.getInstance().getReference(getUniqueKey())
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Get Image"), CODE_IMG)
    }

    private fun getUniqueKey() = FirebaseFirestore.getInstance().collection("pegando chave").document().id

    //término - funçoes para upload da imagem

    fun salvarDados() {
        val bancoDados = FirebaseFirestore.getInstance().collection("InfoGame")
        val id = getUniqueKey()
        game.id = id
        bancoDados.document(id).set(game)
    }
}