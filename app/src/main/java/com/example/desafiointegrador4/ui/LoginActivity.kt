package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiointegrador4.databinding.ActivityLoginBinding
import com.example.desafiointegrador4.models.Usuario
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        //botao para logar
        binding.ivBtnLogin.setOnClickListener {
            realizarLogin()
            callMain()
        }

        //botao para criar um cadastro
        binding.tvCreate.setOnClickListener {
            callRegister()
        }

        setContentView(binding.root)
    }

    //função para ir para a Main
    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //função para ir para o cadastro
    private fun callRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    //função para pegar as informações do usuario e depois efetuar a tentativa de login no Firebase Auth
    private fun realizarLogin() {
            val usuario = getUsuario()
            if (usuario != null) {
                sendFirebaseLogin(usuario)
            } else {
                sendMsg("Preencha o campo email e senha corretamente!")
            }
    }

    //função apenas de toast :)
    private fun sendMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //executando o login no Firebase
    private fun sendFirebaseLogin(usuario: Usuario) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val firebaseUser = it.result?.user!!
                    Usuario(firebaseUser.email.toString(), "", firebaseUser.uid)
                    sendMsg("Login realizado com sucesso!")
                }
                if (it.isCanceled) {
                    sendMsg("Não foi possível fazer o login, tente novamente!")
                }
            }
    }

    //função que pegará todos os campos que foram preenchidos
    private fun getUsuario(): Usuario? {
        var email = binding.etEmailLogin.text.toString()
        var senha = binding.etPasswordLogin.text.toString()

        return if (!email.isNullOrBlank() and !senha.isNullOrBlank()) {
            Usuario("", email, senha)
        } else {
            null
        }
    }
}