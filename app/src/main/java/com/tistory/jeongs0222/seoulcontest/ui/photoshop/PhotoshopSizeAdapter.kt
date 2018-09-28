package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.tistory.jeongs0222.seoulcontest.R


class PhotoshopSizeAdapter(internal val context: Context, val callback: (Int) -> Unit): RecyclerView.Adapter<PhotoshopSizeAdapter.ViewHolder>(), SeekBar.OnSeekBarChangeListener {

    //var item = ArrayUtil.stampList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_size, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        //val items = item[position]

        /*Glide.with(context)
                .asBitmap()
                .load(R.drawable.temporary)
                .into(holder.stamp_imageView)

        holder.stamp_textView.text = item[position].toString()*/


        /*holder.stamp_entire_constraintLayout.setOnClickListener {
            callback(position)
        }*/

        holder.stamp_progressBar.setOnSeekBarChangeListener(this@PhotoshopSizeAdapter)
    }

    override fun getItemCount(): Int = 1


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val stamp_entire_constraintLayout: ConstraintLayout
        //val stamp_imageView: ImageView
        //val stamp_textView: TextView
        val stamp_progressBar: SeekBar

        init {
            stamp_entire_constraintLayout = itemView.findViewById(R.id.stamp_entire_constraintLayout)
            //stamp_imageView = itemView.findViewById(R.id.stamp_imageView)
            //stamp_textView = itemView.findViewById(R.id.stamp_textView)
            stamp_progressBar = itemView.findViewById(R.id.stamp_progressBar)
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        callback(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }
}