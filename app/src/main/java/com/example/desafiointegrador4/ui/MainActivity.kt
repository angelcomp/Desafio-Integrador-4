package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiointegrador4.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addNewGame.setOnClickListener {
            callGameRegister()
        }
    }

    fun callGameRegister() {
        val intent = Intent(this, GameRegisterActivity::class.java)
        startActivity(intent)
    }
}