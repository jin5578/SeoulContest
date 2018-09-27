package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.ui.place.PlaceActivity
import com.tistory.jeongs0222.seoulcontest.util.ArrayUtil
import com.tistory.jeongs0222.seoulcontest.util.SquareView


class MapAdapter(internal val context: Context): RecyclerView.Adapter<MapAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_map, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.map_gu_textView.text = ArrayUtil.guList[position]

        holder.map_entire_layout.setOnClickListener {
            val intent = Intent(it.context, PlaceActivity::class.java)
            intent.putExtra("order", position)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = 25

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val map_entire_layout: SquareView
        val map_gu_textView: TextView

        init {
            map_entire_layout = itemView.findViewById(R.id.map_entire_layout)
            map_gu_textView = itemView.findViewById(R.id.map_gu_textView)
        }
    }
}