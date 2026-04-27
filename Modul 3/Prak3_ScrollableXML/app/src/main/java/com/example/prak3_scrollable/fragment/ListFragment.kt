package com.example.prak3_scrollable.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.prak3_scrollable.adapter.CharacterAdapter
import com.example.prak3_scrollable.adapter.FeaturedAdapter
import com.example.prak3_scrollable.databinding.FragmentListBinding
import com.example.prak3_scrollable.model.Character
import com.example.prak3_scrollable.model.CharacterData

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characters = CharacterData.characters

        // Setup Featured (horizontal) RecyclerView
        val featuredAdapter = FeaturedAdapter(characters) { character ->
            navigateToDetail(character)
        }
        binding.rvFeatured.adapter = featuredAdapter

        // Setup All Characters (vertical) RecyclerView
        val characterAdapter = CharacterAdapter(
            characters = characters,
            onDetailClick = { character -> navigateToDetail(character) }
        )
        binding.rvAllCharacters.adapter = characterAdapter
    }

    private fun navigateToDetail(character: Character) {
        val action = ListFragmentDirections.actionListFragmentToDetailFragment(character)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
