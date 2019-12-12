package com.architectcoders.openweather.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.architectcoders.openweather.R
import com.architectcoders.openweather.ui.commun.getImageFromString
import com.architectcoders.openweather.ui.commun.getViewModel
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val WEATHER = "DetailActivity:weather"
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        viewModel = getViewModel {
            DetailViewModel(intent.getParcelableExtra(WEATHER))
        }
        viewModel.model.observe(this, Observer(::updateUI))
    }

    private fun updateUI(model: DetailViewModel.UiModel) = with(model.detail) {
        weatherDetailToolbar.title = city
        showImages(main)

        weatherDetailSummary.text = main
        weatherDetailInfo.setMovie(this)
    }

    private fun showImages(image: String) {
        weatherDetailImage.setImageDrawable(
            getImageFromString(
                image,
                this
            )
        )
        weatherImageView.setImageDrawable(
            getImageFromString(
                image,
                this
            )
        )
    }
}
