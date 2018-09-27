package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.ui.place.PlaceActivity


class PhotoshopPlaceAdapter(internal val context: Context, val callback: (String, CharSequence) -> Unit) : RecyclerView.Adapter<PhotoshopPlaceAdapter.ViewHolder>(), TextWatcher {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PhotoshopPlaceAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.place_store_editText.addTextChangedListener(this@PhotoshopPlaceAdapter)

        holder.place_menu_editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                callback("2", p0!!)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        holder.place_search_button.setOnClickListener {
            val intent = Intent(it.context, PlaceActivity::class.java)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = 1


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val place_store_editText: EditText
        val place_menu_editText: EditText
        val place_search_button: Button

        init {
            place_store_editText = itemView.findViewById(R.id.place_store_editText)
            place_menu_editText = itemView.findViewById(R.id.place_menu_editText)
            place_search_button = itemView.findViewById(R.id.place_search_button)
        }
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        callback("1", p0!!)
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }


}