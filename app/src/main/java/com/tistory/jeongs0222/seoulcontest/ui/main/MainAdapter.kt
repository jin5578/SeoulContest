package com.tistory.jeongs0222.seoulcontest.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tistory.jeongs0222.seoulcontest.R
import com.tistory.jeongs0222.seoulcontest.model.hostModel.HoModel
import com.tistory.jeongs0222.seoulcontest.util.DynamicViewUtil


class MainAdapter(internal val context: Context, val callback: () -> Unit): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var item: MutableList<HoModel.recommendItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recommend, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        val items = item[position]

        Glide.with(context)
                .asBitmap()
                .load(items.image)
                .apply(RequestOptions()
                        .encodeFormat(Bitmap.CompressFormat.PNG)
                        .placeholder(R.drawable.ic_refresh_black_24dp)
                        .error(R.drawable.ic_refresh_black_24dp)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                )
                .into(holder.recommend_imageView)

        holder.recommend_favorite_imageView.setOnClickListener {
            callback
        }

        holder.recommend_favorite_textView.text = items.like.toString() + "개"

        holder.recommend_tag_layout.addView(DynamicViewUtil.dynamicTagText(context, context.assets, "# " + items.store))

        holder.recommend_tag_layout.addView(DynamicViewUtil.dynamicTagText(context, context.assets, "  # " + items.menu))
    }

    fun addAllItems(e: MutableList<HoModel.recommendItem>) = item.addAll(e)

    fun notifyChanged() = notifyDataSetChanged()

    override fun getItemCount(): Int {
        return if(item.isEmpty()) {
            0
        } else {
            item.size
        }
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val recommend_imageView: ImageView
        val recommend_favorite_imageView: ImageView
        val recommend_favorite_textView: TextView
        val recommend_tag_layout: LinearLayout

        init {
            recommend_imageView = itemView.findViewById(R.id.recommend_imageView)
            recommend_favorite_imageView = itemView.findViewById(R.id.recommend_favorite_imageView)
            recommend_favorite_textView = itemView.findViewById(R.id.recommend_favorite_textView)
            recommend_tag_layout = itemView.findViewById(R.id.recommend_tag_layout)
        }
    }
}