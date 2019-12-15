package com.architectcoders.openweather.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.database.Weather
import com.architectcoders.openweather.ui.common.basicDiffUtil
import com.architectcoders.openweather.ui.common.getDateTime
import com.architectcoders.openweather.ui.common.getImageFromString
import com.architectcoders.openweather.ui.common.inflate
import kotlinx.android.synthetic.main.detail_item.view.*

class DetailAdapter(var context : Context) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {
    var weatherList: List<Weather> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.detail_item, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = weatherList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.bind(weather, context)
        //holder.itemView.setOnClickListener { listener(movie) }
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather, context :Context) {
            itemView.dayItem.text = getDateTime(weather.timestamp)
            itemView.mainItem.setImageDrawable(
                getImageFromString(
                    weather.main,
                    context
                )
            )
        }
    }
}