package com.example.appmusicagrupon

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_entity")
data class Usuario(
    @ColumnInfo(name = "user") var user:String,
    @ColumnInfo(name = "password") var password:String
){
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}
