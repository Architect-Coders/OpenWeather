package com.architectcoders.openweather.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.openweather.model.detail.Detail

class DetailViewModel(private val detail: Detail) : ViewModel() {

    class UiModel(val detail: Detail)

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel(detail)
            return _model
        }
}