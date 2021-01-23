package com.example.desafiointegrador4.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiointegrador4.R
import kotlinx.android.synthetic.main.activity_game_register.*

class GameRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_register)

        registerGameBack.setOnClickListener {
            finish()
        }
    }
}