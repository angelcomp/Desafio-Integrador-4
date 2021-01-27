package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.desafiointegrador4.databinding.ActivityGameDetailsBinding
import com.example.desafiointegrador4.models.Game
import com.squareup.picasso.Picasso

class GameDetailsActivity : AppCompatActivity() {
    private var game: Game = Game()
    lateinit var binding: ActivityGameDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailsBinding.inflate(layoutInflater)

        var jogo = intent.getSerializableExtra("game") as? Game

        //se nao for nulo, vai colocar todas as informações do game no layout
        if (jogo != null) {
            game = jogo
            updateInfoGame(game)
        } else {
            Toast.makeText(this, "Não foi possível carregar suas as informações!", Toast.LENGTH_SHORT).show()
            finish()
        }

        //botao para voltar à activity anterior
        binding.ivBackDetail.setOnClickListener {
            finish()
        }

        //botão para editar as informações do game
        binding.ivEditGame.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("game", game)

            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
    }

    //colocando as informações do game no layout
    fun updateInfoGame(game: Game) {
        binding.tvGameName.text = game.nome
        binding.tvDataGame.text = game.dataCriacao
        binding.tvDescricaoGame.text = game.descricao

        Picasso.get().load(game.urlImg).into(binding.ivBgImg)
    }
}