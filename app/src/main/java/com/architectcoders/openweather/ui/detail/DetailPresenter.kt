package com.architectcoders.openweather.ui.detail

import com.architectcoders.openweather.model.detail.Detail

class DetailPresenter {

    private var view: DetailView? = null

    fun onCreate(view: DetailView, detail: Detail) {
        this.view = view
        view.updateUI(detail)
    }

    fun onDestroy() {
        view = null
    }
}