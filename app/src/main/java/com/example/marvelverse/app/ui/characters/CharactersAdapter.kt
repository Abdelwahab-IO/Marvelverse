package com.example.marvelverse.app.ui.characters

import com.example.marvelverse.R
import com.example.marvelverse.app.ui.base.BaseInteractionListener
import com.example.marvelverse.domain.entities.main.Character
import com.example.marvelverse.app.ui.base.BaseAdapter

class CharactersAdapter(private val listener: BaseInteractionListener) :
    BaseAdapter<Character>(listener) {
    override val layoutID: Int = R.layout.item_character
}
