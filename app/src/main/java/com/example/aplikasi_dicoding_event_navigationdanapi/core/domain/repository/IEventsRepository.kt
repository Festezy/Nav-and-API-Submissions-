package com.example.aplikasi_dicoding_event_navigationdanapi.core.domain.repository

import androidx.lifecycle.LiveData
import com.example.aplikasi_dicoding_event_navigationdanapi.core.data.Resource
import com.example.aplikasi_dicoding_event_navigationdanapi.core.domain.model.Events

interface IEventsRepository {
    fun getEvents(): LiveData<Resource<List<Events>>>
    fun getFavoriteEvent(): LiveData<List<Events>>
    fun setFavoriteEvent(events: Events, favoriteState: Boolean)
}