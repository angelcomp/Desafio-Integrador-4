package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.models.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_details.*
import kotlinx.android.synthetic.main.activity_game_register.*

class GameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        var game = intent.getSerializableExtra("game") as? Game

        if (game != null) {
            tv_gameName.text = game.nome
            tv_dataGame.text = game.dataCriacao
            tv_descricaoGame.text = game.descricao

            Picasso.get().load(game.urlImg).into(iv_bgImg)
        }

        iv_backDetail.setOnClickListener {
            finish()
        }

        iv_editGame.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("game", game)

            startActivity(intent)
        }
    }
}