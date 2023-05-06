package com.example.marvelverse.app.ui.comics

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.marvelverse.app.ui.abstracts.BaseFragment
import com.example.marvelverse.databinding.FragmentComicsBinding
import com.example.marvelverse.domain.entities.main.Comic


class ComicsFragment : BaseFragment<FragmentComicsBinding>(FragmentComicsBinding::inflate) {

    private val viewModel: ComicViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ComicAdapter(viewModel)
        binding.comicRecyclerView.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.comicEvent.observe(viewLifecycleOwner) {
            it?.let {
                handleComic(it)
                viewModel.clearEvents()
            }
        }
    }

    fun handleComic(event: ComicEvent) {
        when (event) {
            is ComicEvent.ClickComicEvent -> navigateToDetails(event.comic)
            ComicEvent.BackToHome -> BackToHome()
            else -> {}
        }
        viewModel.clearEvents()
    }

    fun navigateToDetails(comic: Comic) {
        val direction = ComicsFragmentDirections.actionComicsFragmentToComicDetailsFragment(comic)
        findNavController().navigate(direction)
    }

    fun BackToHome() {
        Log.d("ComicFragment", "BackToHome")
    }

}