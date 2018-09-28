package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.model.goModel.GoModel


class PlaceAdapter(internal val context: Context): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    var item: MutableList<GoModel.storeItem> = ArrayList()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_search, parent, false))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("PlaceAdapter", item.size.toString())

        holder.place_name_textView.text = item[position].UPSO_NM
        holder.place_address_textView.text = item[position].SITE_ADDR_RD
        holder.place_number_textView.text = item[position].SNT_UPTAE_NM
    }

    override fun getItemCount(): Int {
        return if (item.isEmpty()) {
            0
        } else {
            item.size
        }
    }

    fun addAllItems(e: MutableList<GoModel.storeItem>) = item.addAll(e)

    fun notifyChanged() = notifyDataSetChanged()

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