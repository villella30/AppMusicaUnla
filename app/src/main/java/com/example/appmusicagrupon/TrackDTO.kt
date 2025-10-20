package com.example.appmusicagrupon

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeezerResponse(
    val tracks: Tracks
)

@JsonClass(generateAdapter = true)
data class Tracks(
    val data: List<TrackDTO>
)

@JsonClass(generateAdapter = true)
data class TrackDTO(
    val id: Long,
    val title: String,
    val preview: String,
    val artist: Artist,
    val album: Album
)

@JsonClass(generateAdapter = true)
data class Artist(
    val name: String
)

@JsonClass(generateAdapter = true)
data class Album(
    val title: String,
    val cover_xl: String
)
