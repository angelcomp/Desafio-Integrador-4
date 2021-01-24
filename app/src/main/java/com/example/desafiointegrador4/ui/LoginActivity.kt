package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iv_btnLogin.setOnClickListener {
            realizarLogin()
            callMain()
        }

        tv_create.setOnClickListener {
            callRegister()
        }
    }

    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun callRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun realizarLogin() {
            val usuario = getUsuario()
            if (usuario != null) {
                sendFirebaseLogin(usuario)
            } else {
                sendMsg("Preencha o campo email e senha corretamente!")
            }
    }

    private fun sendMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                .show()
    }

    private fun sendFirebaseLogin(usuario: Usuario) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(usuario.email, usuario.senha)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val firebaseUser = it.result?.user!!
                        val usarFire = Usuario(firebaseUser.email.toString(), "", firebaseUser.uid)
                        sendMsg("Login realizado com sucesso!")
                    }
                }
    }

    private fun getUsuario(): Usuario? {
        var email = et_emailLogin.text.toString()
        var senha = et_passwordLogin.text.toString()
        return if (!email.isNullOrBlank() and !senha.isNullOrBlank())
            Usuario("", email, senha)
        else
            null
    }
}