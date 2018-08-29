package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.ArrayUtil


class PhotoshopFontAdapter(internal val context: Context, val callback: (Int) -> Unit): RecyclerView.Adapter<PhotoshopFontAdapter.ViewHolder>() {

    var item = ArrayUtil.fontList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PhotoshopFontAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_font, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        val items = item[position]

        Glide.with(context)
                .asBitmap()
                .load(R.drawable.temporary)
                .into(holder.font_imageView)

        holder.font_textView.text = item[position]

        holder.font_entire_constraintLayout.setOnClickListener {
            callback(position)
        }
    }

    override fun getItemCount(): Int = item.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val font_entire_constraintLayout: ConstraintLayout
        val font_imageView: ImageView
        val font_textView: TextView

        init {
            font_entire_constraintLayout = itemView.findViewById(R.id.font_entire_constraintLayout)
            font_imageView = itemView.findViewById(R.id.font_imageView)
            font_textView = itemView.findViewById(R.id.font_textView)
        }
    }
}