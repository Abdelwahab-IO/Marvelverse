package com.example.marvelverse.app.ui.search

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.marvelverse.R
import com.example.marvelverse.app.ui.base.BaseAdapter
import com.example.marvelverse.app.ui.base.BottomNavFragment
import com.example.marvelverse.app.ui.characters.CharactersAdapter
import com.example.marvelverse.app.ui.comics.ComicAdapter
import com.example.marvelverse.app.ui.events.EventsAdapter
import com.example.marvelverse.app.ui.search.utils.SearchEvent
import com.example.marvelverse.app.ui.search.utils.SearchFilter
import com.example.marvelverse.databinding.FragmentSearchBinding
import com.example.marvelverse.domain.entities.Character
import com.example.marvelverse.domain.entities.Comic
import com.example.marvelverse.domain.entities.Event
import com.example.marvelverse.domain.entities.SearchKeyword
import com.example.marvelverse.utilites.DataState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SearchFragment : BottomNavFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()
    private val disposable = CompositeDisposable()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getAllSearchKeyword()

        viewModel.searchEvent.observe(viewLifecycleOwner) { event ->
            event.getUnHandledData()?.let {
                handleEvent(it)
            }
        }

        viewModel.searchFilterOption.observe(viewLifecycleOwner) {
            handleRecyclerAdapters()

        }
        viewModel.searchKeyword.observe(viewLifecycleOwner){searchKeywordsToChips(it)}
        onChangeSelectedChip()
        searchViewListener()

    }

    private fun handleEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.ClickCharacterEvent -> navigateToDetails(event.character)
            is SearchEvent.ClickComicEvent -> navigateToDetails(event.comic)
            is SearchEvent.ClickEventEvent -> navigateToDetails(event.event)
        }
    }

    private fun <T> navigateToDetails(element: T) {
        when (element) {
            is Character -> findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToDetailsCharacterFragment(
                    element
                )
            )

            is Comic -> findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToComicDetailsFragment(
                    element
                )
            )

            is Event -> findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToEventDetailsFragment(
                    element
                )
            )
        }

    }


    private fun handleRecyclerAdapters(
    ) {
        val charactersAdapter = CharactersAdapter(viewModel)
        val comicAdapter = ComicAdapter(viewModel)
        val eventsAdapter = EventsAdapter(viewModel)
        val text = binding.searchViewLayout.editText?.text.toString()
        when (viewModel.searchFilterOption.value!!) {
            SearchFilter.Character -> {
                makeSearch(text, SearchFilter.Character)
                setupRecyclerView(viewModel.characterList, charactersAdapter)
            }

            SearchFilter.Comic -> {
                makeSearch(text, SearchFilter.Comic)
                setupRecyclerView(viewModel.comicList, comicAdapter)
            }

            SearchFilter.Event -> {
                makeSearch(text, SearchFilter.Event)
                setupRecyclerView(viewModel.eventList, eventsAdapter)
            }

        }
    }

    private fun <T> setupRecyclerView(items: LiveData<DataState<T>>, adapter: BaseAdapter<T>) {
        val recyclerView = binding.recyclerView
        items.observe(viewLifecycleOwner) {
            if (it is DataState.Success) {
                adapter.setItems(it.toData()!!)
                recyclerView.adapter = adapter
            }
        }
    }

    private fun onChangeSelectedChip() {
        binding.chipGroupSearchOption.setOnCheckedStateChangeListener { group, _ ->
            val selectedSearchFilter = when (group.checkedChipId) {
                binding.chipCharacter.id -> SearchFilter.Character
                binding.chipComics.id -> SearchFilter.Comic
                binding.chipEvent.id -> SearchFilter.Event
                else -> SearchFilter.Character
            }
            viewModel.onSearchFilterOptionSelected(selectedSearchFilter)

        }
    }

    private fun searchViewListener() {
        val observable = Observable.create { emitter ->
            binding.searchViewLayout.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    viewModel.showKeywordSuggests()
                } else {
                    emitter.onNext(text.toString())
                }
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(1, TimeUnit.SECONDS)
            .subscribe { text ->
                val current = binding.searchViewLayout.editText?.text.toString()
                if (current == text) {
                    viewModel.cacheKeyword(SearchKeyword(text))
                    makeSearch(text, viewModel.searchFilterOption.value!!)
                }
            }
        disposable.add(observable)
    }

    private fun makeSearch(text: String?, searchFilter: SearchFilter) {
        if (!text.isNullOrEmpty()) {
            when (searchFilter) {
                SearchFilter.Character -> viewModel.characterSearch(null, text)
                SearchFilter.Comic -> viewModel.comicSearch(null, text)
                SearchFilter.Event -> viewModel.eventSearch(null, text)
            }
        }
    }

    private fun searchKeywordsToChips(items: List<SearchKeyword>?) {
        binding.searchSuggest.removeAllViews()

        items?.take(10)?.forEach {searchKeyword->
            val c = Chip(context).apply {
                text = searchKeyword.keyword
                isCloseIconVisible = false
                setTextColor(ContextCompat.getColor(context, R.color.black))
                setOnClickListener{
                    binding.searchViewLayout.editText?.setText(searchKeyword.keyword)
                    makeSearch(searchKeyword.keyword,viewModel.searchFilterOption.value!!)
                }
            }
            binding.searchSuggest.addView(c)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

}