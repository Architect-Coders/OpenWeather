package com.architectcoders.openweather.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.ui.commun.getImageFromString
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity(),DetailView {

    companion object {
        const val WEATHER = "DetailActivity:weather"
    }

    private val presenter = DetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        presenter.onCreate(this, intent.getParcelableExtra(WEATHER))

    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateUI(detail: Detail) {
        weatherDetailToolbar.title = detail.city
        showImages(detail.main)

        weatherDetailSummary.text = detail.main
        weatherDetailInfo.setMovie(detail)
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
