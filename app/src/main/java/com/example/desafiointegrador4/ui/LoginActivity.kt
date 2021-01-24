package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
}