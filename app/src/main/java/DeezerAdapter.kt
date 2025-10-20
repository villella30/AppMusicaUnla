package com.example.appmusicagrupon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class DeezerAdapter
    (var lista: List<TrackDTO>,
     var onClick: (TrackDTO) -> Unit,
            ) : RecyclerView.Adapter<DeezerAdapter.TrackViewHolder>() {

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCover: ImageView = view.findViewById(R.id.imgCover)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvArtist: TextView = view.findViewById(R.id.tvArtist)
        val tvAlbum: TextView = view.findViewById(R.id.tvAlbum)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cancion, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = lista[position]
        holder.tvTitle.text = track.title
        holder.tvArtist.text = track.artist.name
        holder.tvAlbum.text = track.album.title

        Picasso.get()
            .load(track.album.cover_xl)
            .into(holder.imgCover)

        holder.itemView.setOnClickListener {
            onClick(track)  // sigue pasando info a DetalleActivity
        }

    }

    override fun getItemCount() = lista.size
}
