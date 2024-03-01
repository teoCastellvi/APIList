package com.example.myapplication.viewmodel



import android.util.Log
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.api.Data
import com.example.myapplication.api.Mapa
import com.example.myapplication.api.ValorantMaps
import com.example.myapplication.retrofitapp.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIViewModel : ViewModel() {
    val font = FontFamily(Font(R.font.valorant))
    private val repository = Repository()
    private val _loading = MutableLiveData(true)
    val loading = _loading
    private val _maps = MutableLiveData<ValorantMaps>()
    val maps = _maps
    val map = MutableLiveData<Mapa>()

    var id = ""
    private val _favoutites = MutableLiveData<MutableList<Data>>()
    val favourites = _favoutites
    private val _isFavourite = MutableLiveData(false)
    val isFavourite = _isFavourite
    private val _searchText = MutableLiveData<String>()
    val searchText = _searchText
    private val _mapsAPI = MutableLiveData<ValorantMaps>()
    val mapsAPI = _mapsAPI

    fun onSearchTextChange(nom:String) {
        _searchText.value = nom
        var mapsFiltrados =
            ValorantMaps(_mapsAPI.value!!.data.filter { it.displayName.lowercase().contains(nom.lowercase())}, 0)
        _maps.value = mapsFiltrados
        if (nom.isEmpty()) _maps.value = _mapsAPI.value
    }

    fun changeFavourite() {
        _isFavourite.value = !_isFavourite.value!!
    }

    fun getFavourites() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getFavourites()
            withContext(Dispatchers.Main) {
                _favoutites.value = response
                _loading.value = false
            }
        }
    }

    fun isFavorite(map: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.isFavourite(map)
            withContext(Dispatchers.Main) {
                _isFavourite.value = response
            }
        }
    }


    fun deleteFavourite(map: Data) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteFavourite(map)
        }
    }

        fun saveAsFavourite(map: Data) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.saveAsFavourite(map)
            }
        }

        fun getMaps() {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.getAllCharacters()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _maps.value = response.body()
                        _mapsAPI.value = _maps.value
                        _loading.value = false
                    } else {
                        Log.e("Error :", response.message())
                    }
                }
            }
        }

        fun getMap(id: String) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = repository.getAllMap(id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        map.value = response.body()
                        _loading.value = false
                    } else {
                        Log.e("Error :", response.message())
                    }
                }
            }
        }

        fun getIdX(): String {
            return this.id
        }

        fun setIdX(id: String) {
            this.id = id
        }

    }


