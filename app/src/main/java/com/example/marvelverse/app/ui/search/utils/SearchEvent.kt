package com.example.marvelverse.app.ui.search.utils

import com.example.marvelverse.domain.entities.Character
import com.example.marvelverse.domain.entities.Comic
import com.example.marvelverse.domain.entities.Event

sealed interface SearchEvent {

    data class ClickCharacterEvent(val character: Character) : SearchEvent
    data class ClickComicEvent(val comic: Comic) : SearchEvent
    data class ClickEventEvent(val event: Event) : SearchEvent
}