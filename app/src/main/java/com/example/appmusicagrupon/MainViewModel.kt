package com.example.appmusicagrupon


import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val api: DeezerApiService) : ViewModel() {


    private val _tracks = MutableLiveData<List<TrackDTO>>()
    val tracks: LiveData<List<TrackDTO>> get() = _tracks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun cargarCanciones(limit: Int = 100) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("DEBUG", "Hilo actual: ${Thread.currentThread().name}")
            try {
                val response = api.getTracks(limit) // <--- sin .execute()
                if (response.isSuccessful) {
                    _tracks.postValue(response.body()?.tracks?.data ?: emptyList())
                } else {
                    _error.postValue("Error HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error: ${e.message}")
            }
        }
    }

}
