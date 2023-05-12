package com.example.marvelverse.app.ui.search

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvelverse.utilites.DataState
import com.example.marvelverse.app.ui.base.BaseViewModel
import com.example.marvelverse.app.ui.bottomSheet.BottomSheetListener
import com.example.marvelverse.app.ui.interfaces.CharacterInteractionListener
import com.example.marvelverse.app.ui.interfaces.ComicInteractionListener
import com.example.marvelverse.app.ui.interfaces.EventInteractionListener
import com.example.marvelverse.data.repositories.MarvelRepository
import com.example.marvelverse.domain.entities.main.Character
import com.example.marvelverse.domain.entities.main.Comic
import com.example.marvelverse.domain.entities.main.Event


enum class SearchFilter {
    Character,
    Comic,
    Event,
}

@SuppressLint("CheckResult")
class SearchViewModel :
    BaseViewModel(), BottomSheetListener, CharacterInteractionListener,
    ComicInteractionListener, EventInteractionListener {

    private val repositry = MarvelRepository

    var searchFilterOption: MutableLiveData<SearchFilter> =
        MutableLiveData<SearchFilter>(SearchFilter.Character)


    private val _itemList = MutableLiveData<DataState<Any>>()
    val itemList: LiveData<DataState<Any>>
        get() = _itemList

    private val _searchEvent = MutableLiveData<SearchEvent>()
    val searchEvent: LiveData<SearchEvent> get() = _searchEvent

    init {
        searchFilterOption.postValue(SearchFilter.Character)
    }

    fun comicSearch(limit: Int?, title: String?) {
        _itemList.postValue(DataState.Loading)
            repositry.searchComics(limit, title)
                .applySchedulers()
                .subscribe(::onComicsSearchSuccess, ::onSearchError)
                .addTo(disposables)
    }

    fun characterSearch(limit: Int?, title: String?) {
        _itemList.postValue(DataState.Loading)
            repositry.searchCharacters(limit, title)
                .applySchedulers()
                .subscribe(::onCharacterSearchSuccess, ::onSearchError)
                .addTo(disposables)
    }

    fun eventSearch(limit: Int?, title: String?) {
        _itemList.postValue(DataState.Loading)
            repositry.searchEvents(limit, title)
                .applySchedulers()
                .subscribe(::onEventSearchSuccess, ::onSearchError)
                .addTo(disposables)
    }

    private fun onComicsSearchSuccess(comics: List<Comic>) {
        _itemList.postValue(DataState.Success(comics))
    }

    private fun onCharacterSearchSuccess(characters: List<Character>) {
        _itemList.postValue(DataState.Success(characters))
    }

    private fun onEventSearchSuccess(events: List<Event>) {
        _itemList.postValue(DataState.Success(events))
    }

    private fun onSearchError(throwable: Throwable) {
        _itemList.postValue(DataState.Error(throwable))
    }

    override fun onSearchFilterOptionSelected(searchFilter: SearchFilter) {
        this.searchFilterOption.postValue(searchFilter)
        _itemList.postValue(DataState.Empty)
    }

    override fun onCharacterClick(character: Character) {
        _searchEvent.postValue(SearchEvent.ClickCharacterEvent(character))
    }

    override fun onComicClick(comic: Comic) {
        _searchEvent.postValue(SearchEvent.ClickComicEvent(comic))
    }

    override fun onEventClick(event: Event) {
        _searchEvent.postValue(SearchEvent.ClickEventEvent(event))
    }
    fun clearEvents() {
        if (_searchEvent.value != SearchEvent.ReadyState)
            _searchEvent.postValue(SearchEvent.ReadyState)
    }

    fun setItemListStateEmpty(){
        _itemList.postValue(DataState.Empty)
    }
}
