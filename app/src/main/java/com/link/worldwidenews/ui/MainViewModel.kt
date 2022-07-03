package com.link.worldwidenews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.link.worldwidenews.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    private val _searchQuerySingleLiveEvent = SingleLiveEvent<String?>()
    val searchQueryLiveData: LiveData<String?> = _searchQuerySingleLiveEvent

    fun searchInNewsList(query: String) {
        _searchQuerySingleLiveEvent.value=query
    }


}