package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.util.ArrayUtil


class PhotoshopPaintAdapter(internal val context: Context, val callback: (Int) -> Unit): RecyclerView.Adapter<PhotoshopPaintAdapter.ViewHolder>() {

    var item = ArrayUtil.paintColorList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PhotoshopPaintAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_paint, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        holder.paint_imageView.setBackgroundColor(Color.parseColor(item[position]))

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.paint_imageView.setBackground(ShapeDrawable(OvalShape()))
        }*/

        holder.paint_entire_constraintLayout.setOnClickListener {
            callback(position)
        }
    }

    override fun getItemCount(): Int = item.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val paint_entire_constraintLayout: ConstraintLayout
        val paint_imageView: ImageView

        init {
            paint_entire_constraintLayout = itemView.findViewById(R.id.paint_entire_constraintLayout)
            paint_imageView = itemView.findViewById(R.id.paint_imageView)
        }
    }
}