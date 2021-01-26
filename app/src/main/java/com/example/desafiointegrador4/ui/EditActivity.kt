package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.databinding.ActivityEditBinding
import com.example.desafiointegrador4.models.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*

class EditActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)

        var game = intent.getSerializableExtra("game") as? Game

        if (game != null) {
            binding.gameName.setText(game.nome)
            binding.date.setText(game.dataCriacao)
            binding.description.setText(game.descricao)

            //Picasso.get().load(game.urlImg).into(binding.)
        }

        binding.editGameBack.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }
}