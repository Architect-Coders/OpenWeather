package com.architectcoders.openweather.ui.detail

import com.architectcoders.openweather.model.detail.Detail

interface DetailView {
    fun updateUI(detail: Detail)
}