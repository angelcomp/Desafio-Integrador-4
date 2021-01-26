package com.example.desafiointegrador4.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiointegrador4.R
import com.example.desafiointegrador4.adapter.GameAdapter
import com.example.desafiointegrador4.models.Game
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity(), GameAdapter.onGameClickListener {

    private var listaGames = MutableLiveData<ArrayList<Game>>()
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: GameAdapter
    val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = GridLayoutManager(this, 2)

        rvGames.layoutManager = layoutManager
        rvGames.setHasFixedSize(true)

        listaGames.observe(this, {
            adapter = GameAdapter(it, this)
            rvGames.adapter = adapter
        })

        addNewGame.setOnClickListener {
            callGameRegister()
        }

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                //adapter.filter.filter(newText)
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getGames()
    }

    fun callGameRegister() {
        val intent = Intent(this, GameRegisterActivity::class.java)
        startActivity(intent)
    }

    fun getGames() {
        val bancoDados = Firebase.firestore.collection("InfoGame")
        val listaGamesLocal = ArrayList<Game>()
        scope.launch {
            val listaGamesRemoto = bancoDados.get().await()
                listaGamesRemoto.forEach { doc->
                listaGamesLocal.add(doc.toObject())
            }
            listaGames.postValue(listaGamesLocal)
        }
    }

    override fun GameClick(position: Int) {
        val intent = Intent(this, GameDetailsActivity::class.java)
        val game = listaGames.value?.get(position)

        intent.putExtra("game", game)

        startActivity(intent)
    }
}
