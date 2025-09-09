package com.example.appmusicagrupon

import Cancion
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.CancionAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val lista = listOf(
            Cancion(1, "Already Dead", "Juice WRLD", "https://i.scdn.co/image/ab67616d0000b2730cdfd4e1d092e29c6d28e3ba", "Fighting Demons"),
            Cancion(2, "Blinding Lights", "The Weeknd", "https://i.scdn.co/image/ab67616d0000b273a3eff72f62782fb589a492f9", "After Hours"),
            Cancion(3, "Levitating", "Dua Lipa", "https://i.scdn.co/image/ab67616d0000b2732172b607853fa89cefa2beb4", "Future Nostalgia"),
            Cancion(4, "Bad Guy", "Billie Eilish", "https://i.scdn.co/image/ab67616d0000b27350a3147b4edd7701a876c6ce", "When We All Fall Asleep, Where Do We Go?"),
            Cancion(5, "Peaches", "Justin Bieber", "https://i.scdn.co/image/ab67616d0000b273e6f407c7f3a0ec98845e4431", "Justice"),
            Cancion(6, "Industry Baby", "Lil Nas X & Jack Harlow", "https://i.scdn.co/image/ab67616d0000b2735fb8476e56c5bdd788150889", "Montero"),
            Cancion(7, "Watermelon Sugar", "Harry Styles", "https://i.scdn.co/image/ab67616d0000b2737cf2b9825bb43083d123ac22", "Fine Line"),
            Cancion(8, "Stay", "The Kid LAROI & Justin Bieber", "https://i.scdn.co/image/ab67616d0000b27341e31d6ea1d493dd77933ee5", "Stay - Single"),
            Cancion(9, "As It Was", "Harry Styles", "https://i.scdn.co/image/ab67616d0000b2732e8ed79e177ff6011076f5f0", "Harry's House"),
            Cancion(10, "Flowers", "Miley Cyrus", "https://i.scdn.co/image/ab67616d0000b273f429549123dbe8552764ba1d", "Endless Summer Vacation"),
            Cancion(11, "Anti-Hero", "Taylor Swift", "https://i.scdn.co/image/ab67616d0000b273eb7289d463cd524a93b722c9", "Midnights"),
            Cancion(12, "Lucid Dreams", "Juice WRLD", "https://i.scdn.co/image/ab67616d0000b2735eb0085f2770818792c3c970", "Goodbye & Good Riddance"),
            Cancion(13, "Easy On Me", "Adele", "https://i.scdn.co/image/ab67616d0000b27350dba34377a595e35f81b0e4", "30"),
            Cancion(14, "Savage Love", "Jason Derulo & Jawsh 685", "https://i.scdn.co/image/ab67616d0000b2737954a1adb0fbe511e5c3ef24", "Savage Love - Single"),
            Cancion(15, "Shape of You", "Ed Sheeran", "https://i.pinimg.com/736x/e6/5f/0c/e65f0c6ede57573306205af9dd7a115c.jpg", "Divide")
        )
        val recycler = findViewById<RecyclerView>(R.id.recyclerCanciones)
        recycler.adapter = CancionAdapter(lista) { cancion ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("titulo", cancion.titulo)
            intent.putExtra("artista", cancion.artista)
            intent.putExtra("album", cancion.album)
            intent.putExtra("portada", cancion.portada)
            startActivity(intent)

        }
    }
}