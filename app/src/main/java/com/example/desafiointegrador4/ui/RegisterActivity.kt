package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiointegrador4.databinding.ActivityRegisterBinding
import com.example.desafiointegrador4.models.Usuario
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        //botão para salvar e finalizar cadastro
        binding.btnCreate.setOnClickListener {
            cadastrarUsuario()
            callMain()
        }

        //botão para voltar à tela anterior
        binding.registerBack.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }

    //função para ir pra Main/ tela Home
    private fun callMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //função para pegar as informações do usuario e depois efetuar a tentativa de cadastro no Firebase Auth
    private fun cadastrarUsuario() {
        val usuario = getUsuario()
        if (usuario != null) {
            sendFirebaseCad(usuario)
        }
        else {
            sendMsg("Preencha o campo email e senha corretamente!")
        }

    }

    //função para tentar cadastrar o usuario no Firebase Auth
    private fun sendFirebaseCad(usuario: Usuario) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val firebaseUser = it.result?.user!!
                    Usuario(firebaseUser.email.toString(),"",firebaseUser.uid)
                    sendMsg("Usuario cadastrado com sucesso!")
                }

            }.addOnFailureListener {
                    sendMsg("deu erro.. $it ")
                }
    }

    //função apenas de toast :)
    private fun sendMsg(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    //função para pegar todas as informações que o usuario digitou nos campos
    private fun getUsuario(): Usuario? {
        var nome = binding.etNameCadastro.text.toString()
        var email = binding.etEmailCadastro.text.toString()
        var senha = binding.etSenhaCadastro.text.toString()

        return if (!email.isNullOrEmpty() and !senha.isNullOrEmpty()) {
            Usuario(nome, email, senha)
        } else {
            null
        }
    }
}