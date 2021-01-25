package com.example.desafiointegrador4.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.models.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_register.*

class GameAdapter(private val listaGames: ArrayList<Game>, val listener: onGameClickListener): RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): GameAdapter.GameViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(itemView)
    }

    override fun getItemCount() = listaGames.size

    override fun onBindViewHolder(holder: GameAdapter.GameViewHolder, position: Int) {
        var game = listaGames.get(position)

        holder.tvNome.text = game.nome
        holder.tvData.text = game.dataCriacao
        Picasso.get().load(game.urlImg).into(holder.ivImg)
        Log.i("teste no adapter", game.urlImg)
    }

    interface onGameClickListener {
        fun GameClick( position: Int )
    }

    inner class GameViewHolder(itemView: View): RecyclerView.ViewHolder (itemView), View.OnClickListener {
        val tvNome: TextView = itemView.findViewById(R.id.nomeGame)
        val tvData: TextView = itemView.findViewById(R.id.dataGame)
        val ivImg: ImageView = itemView.findViewById(R.id.imgGame)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (RecyclerView.NO_POSITION != position) {
                listener.GameClick(position)
            }
        }
    }
}