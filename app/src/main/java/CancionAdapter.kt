package com.example.adapter
import Cancion
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.RecyclerView
import com.example.appmusicagrupon.R


class CancionAdapter (
    var lista: List<Cancion>,
    var onClick: (Cancion) -> Unit
) : RecyclerView.Adapter<CancionAdapter.CancionViewHolder>() {
    class CancionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo : TextView = itemView.findViewById(R.id.tvTitulo)
        val tvArtista : TextView = itemView.findViewById(R.id.tvArtista)
        val imgPortada : ImageView = itemView.findViewById(R.id.imgPortada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cancion, parent, false)
        return CancionViewHolder(view)
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val cancion = lista[position]
        holder.tvTitulo.text = cancion.titulo
        holder.tvArtista.text = cancion.artista
        Picasso.get().load(cancion.portada).into(holder.imgPortada)

        holder.itemView.setOnClickListener { onClick(cancion)}
    }

    override fun getItemCount() = lista.size
}