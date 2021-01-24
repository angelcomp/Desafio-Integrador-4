package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_create.setOnClickListener {
            cadastrarUsuario()
            callMain()
        }

        registerBack.setOnClickListener {
            finish()
        }
    }

    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun cadastrarUsuario() {

        val usuario = getUsuario()
        if (usuario != null) {
            sendFirebaseCad(usuario)
        }
        else {
            sendMsg("Preencha o campo email e senha corretamente!")
        }

    }

    private fun sendFirebaseCad(usuario: Usuario) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.email,usuario.senha)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val firebaseUser = it.result?.user!!
                    val usarFire = Usuario(firebaseUser.email.toString(),"",firebaseUser.uid)
                    sendMsg("Usuario cadastrado com sucesso!")
                }

            }.addOnFailureListener {
                    sendMsg("$it lascou")
                }
    }

    private fun sendMsg(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                .show()
    }

    private fun getUsuario(): Usuario? {
        var nome = et_nameCadastro.text.toString()
        var email = et_emailCadastro.text.toString()
        var senha = et_senhaCadastro.text.toString()
        return if (!email.isNullOrEmpty() and !senha.isNullOrEmpty())
            Usuario(nome, email, senha)
        else
            null
    }
}