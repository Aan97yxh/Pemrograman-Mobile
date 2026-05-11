package com.example.prak4_viewmodel.viewmodel

import androidx.lifecycle.ViewModel
import com.example.prak4_viewmodel.model.Character
import com.example.prak4_viewmodel.model.CharacterData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class CharacterViewModel(private val appName: String) : ViewModel() {

    // ── Data list StateFlow ──
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    // ── Selected character StateFlow (untuk event navigasi ke Detail) ──
    private val _selectedCharacter = MutableStateFlow<Character?>(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        val data = CharacterData.characters
        _characters.value = data
        // a. Log saat data item masuk ke dalam list
        Timber.tag(appName).d("Data berhasil dimuat: ${data.size} karakter masuk ke dalam list")
        data.forEach { character ->
            Timber.tag(appName).d("Item masuk: ${character.name} | ${character.element} | ${character.weaponType}")
        }
    }

    // ── Event: tombol Detail ditekan ──
    fun onDetailClicked(character: Character) {
        // b. Log saat tombol Detail ditekan
        Timber.tag(appName).d("Tombol Detail ditekan untuk karakter: ${character.name}")
        // c. Log data karakter yang dipilih saat berpindah ke halaman Detail
        Timber.tag(appName).d(
            "Berpindah ke Detail -> Nama: ${character.name}, " +
            "Elemen: ${character.element}, Weapon: ${character.weaponType}, " +
            "Rarity: ${character.rarity}"
        )
        _selectedCharacter.value = character
    }

    // ── Event: tombol YouTube (explicit intent) ditekan ──
    fun onYoutubeClicked(character: Character) {
        // b. Log saat tombol Explicit Intent ditekan
        Timber.tag(appName).d("Tombol YouTube ditekan untuk karakter: ${character.name} | URL: ${character.youtubeUrl}")
    }

    // ── Reset selected character setelah navigasi ──
    fun resetSelectedCharacter() {
        _selectedCharacter.value = null
    }
}
