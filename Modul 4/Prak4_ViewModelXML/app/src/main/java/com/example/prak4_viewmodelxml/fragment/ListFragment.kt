package com.example.prak4_viewmodelxml.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.prak4_viewmodelxml.R
import com.example.prak4_viewmodelxml.adapter.CharacterAdapter
import com.example.prak4_viewmodelxml.adapter.FeaturedAdapter
import com.example.prak4_viewmodelxml.databinding.FragmentListBinding
import com.example.prak4_viewmodelxml.viewmodel.CharacterViewModel
import com.example.prak4_viewmodelxml.viewmodel.CharacterViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    // ViewModel dibuat via Factory dengan parameter String appName
    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(getString(R.string.app_name))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        observeSelectedCharacter()
    }

    private fun setupAdapters() {
        // Observe characters StateFlow
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.characters.collectLatest { characters ->

                // Setup Featured (horizontal) RecyclerView
                binding.rvFeatured.adapter = FeaturedAdapter(characters) { character ->
                    viewModel.onDetailClicked(character)
                }

                // Setup All Characters (vertical) RecyclerView
                binding.rvAllCharacters.adapter = CharacterAdapter(
                    characters = characters,
                    onYoutubeClick = { character -> viewModel.onYoutubeClicked(character) },
                    onDetailClick = { character -> viewModel.onDetailClicked(character) }
                )
            }
        }
    }

    private fun observeSelectedCharacter() {
        // Observe selectedCharacter StateFlow untuk navigasi ke Detail
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.selectedCharacter.collectLatest { character ->
                character?.let {
                    val action = ListFragmentDirections.actionListFragmentToDetailFragment(it)
                    findNavController().navigate(action)
                    viewModel.resetSelectedCharacter()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
