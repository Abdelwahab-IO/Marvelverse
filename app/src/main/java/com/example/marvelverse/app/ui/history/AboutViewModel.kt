package com.example.marvelverse.app.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvelverse.app.ui.base.BaseViewModel
import com.example.marvelverse.data.repositories.MarvelRepository
import com.example.marvelverse.domain.entities.About
import com.example.marvelverse.utilites.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class AboutViewModel @Inject constructor(private val repository: MarvelRepository) :
    BaseViewModel() {
    private val _currentItem = MutableLiveData<DataState<About>>()
    val currentItem: LiveData<DataState<About>>
        get() = _currentItem

    init {
        getAboutItems()
    }

    private fun getAboutItems() = _currentItem.postValue(DataState.Success(repository.getItems()))

}