package com.example.appmusicagrupon

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.activity.viewModels


class MainActivity : AppCompatActivity() {



    private lateinit var toolbar: Toolbar
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: DeezerAdapter

    private val api by lazy {
        RetrofitClient.retrofit.create(DeezerApiService::class.java)
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(api)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.titulo)
        toolbar.setTitleTextColor(Color.WHITE)

        recycler = findViewById(R.id.recyclerCanciones)
        adapter = DeezerAdapter(emptyList(),
            onClick = { track ->
                val intent = Intent(this, DetalleActivity::class.java)
                intent.putExtra("titulo", track.title)
                intent.putExtra("artista", track.artist.name)
                intent.putExtra("album", track.album.title)
                intent.putExtra("portada", track.album.cover_xl)
                intent.putExtra("preview", track.preview)
                startActivity(intent)
            }

        )
        recycler.adapter = adapter
        // --- Observadores ---
        viewModel.tracks.observe(this) { canciones ->
            adapter.lista = canciones
            adapter.notifyDataSetChanged()
        }

        viewModel.error.observe(this) { mensaje ->
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            Log.e("MainActivity", mensaje)
        }
        viewModel.cargarCanciones()

        saludarUsuario()
    }

    private fun saludarUsuario() {
        val bundle: Bundle? = intent.extras
        bundle?.getString(resources.getString(R.string.nombre))?.let { usuario ->
            Toast.makeText(this, "Hola $usuario", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cerrar_sesion, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cerrar_sesion) {
            val prefs = getSharedPreferences(resources.getString(R.string.sp_credenciales), MODE_PRIVATE)
            prefs.edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}
