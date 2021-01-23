package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiointegrador4.R
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