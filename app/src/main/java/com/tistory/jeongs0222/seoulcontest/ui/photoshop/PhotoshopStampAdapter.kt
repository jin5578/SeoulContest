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


class PhotoshopStampAdapter(internal val context: Context, val callback: (Int) -> Unit) : RecyclerView.Adapter<PhotoshopStampAdapter.ViewHolder>() {

    var item = ArrayUtil.stampList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stamp, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = item[position]

        Glide.with(context)
                .asBitmap()
                .load(R.drawable.temporary)
                .into(holder.stamp_imageView)

        holder.stamp_textView.text = item[position].toString()


        holder.stamp_entire_constraintLayout.setOnClickListener {
            callback(position)
        }
    }

    override fun getItemCount(): Int = item.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stamp_entire_constraintLayout: ConstraintLayout
        val stamp_imageView: ImageView
        val stamp_textView: TextView

        init {
            stamp_entire_constraintLayout = itemView.findViewById(R.id.stamp_entire_constraintLayout)
            stamp_imageView = itemView.findViewById(R.id.stamp_imageView)
            stamp_textView = itemView.findViewById(R.id.stamp_textView)
        }
    }
}