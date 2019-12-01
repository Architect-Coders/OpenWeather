package com.architectcoders.openweather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.model.image.CreateImage
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val WEATHER = "DetailActivity:weather"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        with(intent.getParcelableExtra<Detail>(WEATHER)) {
            movieDetailToolbar.title = city
            showImage(main)
            movieDetailSummary.text = main
            movieDetailInfo.setMovie(this)
        }
    }

    private fun showImage(image: String) {
        movieDetailImage.setImageDrawable(
            CreateImage().getImageFromString(
                image,
                this
            )
        )
    }
}
