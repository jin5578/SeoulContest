package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.R


class PlaceAdapter(internal val context: Context): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_search, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.place_name_textView.text = "광양불고기본가"
        holder.place_address_textView.text = "서울특별시 강남구 영동대로 325, (대치동,지상1층)"
        holder.place_number_textView.text = "02 5675877"
    }

    override fun getItemCount(): Int {
        return 5
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val place_name_textView: TextView
        val place_address_textView: TextView
        val place_number_textView: TextView

        init {
            place_name_textView = itemView.findViewById(R.id.place_name_textView)
            place_address_textView = itemView.findViewById(R.id.place_address_textView)
            place_number_textView = itemView.findViewById(R.id.place_number_textView)
        }
    }
}