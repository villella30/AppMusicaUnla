package com.example.appmusicagrupon

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class DetalleActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var previewUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val titulo = intent.getStringExtra("titulo")
        val artista = intent.getStringExtra("artista")
        val album = intent.getStringExtra("album")
        val portada = intent.getStringExtra("portada")

        val tvTitulo = findViewById<TextView>(R.id.tvDetalleTitulo)
        val tvArtista = findViewById<TextView>(R.id.tvDetalleArtista)
        val tvAlbum = findViewById<TextView>(R.id.tvDetalleAlbum)
        val imgPortada = findViewById<ImageView>(R.id.imgDetallePortada)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        tvTitulo.text = titulo
        tvArtista.text = artista
        tvAlbum.text = album
        Picasso.get().load(portada).into(imgPortada)

        btnVolver.setOnClickListener {

            finish()
        }

        previewUrl = intent.getStringExtra("preview") ?: ""

        val btnPlay = findViewById<FloatingActionButton>(R.id.btnPlayPreview)
        val btnStop = findViewById<FloatingActionButton>(R.id.btnStopPreview)

        btnPlay.setOnClickListener {
            reproducirPreview(previewUrl)
        }
        btnStop.setOnClickListener {
            detenerPreview()
        }


    }
    private fun reproducirPreview(url: String) {
        if (url.isEmpty()) return

        mediaPlayer?.release()

        mediaPlayer = MediaPlayer().apply {
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { mp ->
                mp.start()
            }
            setOnCompletionListener { mp ->
                mp.release()
            }
        }
    }
    private fun detenerPreview() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }



}