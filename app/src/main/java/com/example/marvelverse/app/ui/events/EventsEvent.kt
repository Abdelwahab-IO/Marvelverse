package com.example.marvelverse.app.ui.events



import com.example.marvelverse.domain.entities.main.Event

sealed interface EventsEvent{
    object BackToHome : EventsEvent
    object ReadyState :EventsEvent
    data class ClickEventsEvent(val event: Event) : EventsEvent
}