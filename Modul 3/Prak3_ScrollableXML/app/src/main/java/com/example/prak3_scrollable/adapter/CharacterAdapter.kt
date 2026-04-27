package com.example.prak3_scrollable.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prak3_scrollable.databinding.ItemCharacterBinding
import com.example.prak3_scrollable.model.Character

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDetailClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            with(binding) {
                ivCharacterImage.setImageResource(character.imageRes)

                // Row 1: Name | Element
                tvCharacterName.text = character.name
                tvCharacterElement.text = character.element

                // Row 2: Weapon | Rarity stars
                tvCharacterWeapon.text = character.weaponType
                tvCharacterRarity.text = "★".repeat(character.rarity)

                // Description
                tvCharacterDescription.text = character.description

                // Button YouTube → explicit intent ke browser
                btnYoutube.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(character.youtubeUrl))
                    it.context.startActivity(intent)
                }

                // Button Detail → navigation component
                btnDetail.setOnClickListener {
                    onDetailClick(character)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size
}
