package com.testapp.topredditnews.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.testapp.topredditnews.R
import com.testapp.topredditnews.data.response.Data
import com.testapp.topredditnews.data.response.Post
import kotlinx.android.synthetic.main.news_list_item.view.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList

class TopNewsRecyclerViewAdapter(val clickEvent: (String) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private val VIEW_TYPE_LOADING = 0
        private val VIEW_TYPE_NORMAL = 1
    }

    private var isLoaderVisible = false
    private val topNewsList: ArrayList<Post> = ArrayList()

    fun setupNewsList(newList: List<Post>) {
        topNewsList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        topNewsList.add(Post())
        notifyItemInserted(topNewsList.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = topNewsList.size - 1
        val item = topNewsList[position]
        if (item != null) {
            topNewsList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clearList() {
        topNewsList.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Post = topNewsList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        var view: View? = null
        return when (viewType) {
            VIEW_TYPE_NORMAL -> TopNewsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false), clickEvent)
            VIEW_TYPE_LOADING -> ProgressHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.news_list_loader, parent, false))
            else -> TopNewsViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false), clickEvent)
        }

//        return TopNewsViewHolder(it, clickEvent)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == topNewsList.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        } else VIEW_TYPE_NORMAL
    }

    override fun getItemCount(): Int {
        return if (topNewsList.isEmpty()) 0 else topNewsList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class TopNewsViewHolder(itemView: View, val clickEvent: (String) -> Unit) :
        BaseViewHolder(itemView) {
        private val memeImg = itemView.meme_img
        private val postedTimeAndAuthor = itemView.posted_by_and_time_tv
        private val commentsCount = itemView.comments_count_tv
//        fun bindItem(item: Post) {
//            Glide.with(itemView).load(item.urlOverriddenByDest).into(memeImg)
//            postedTimeAndAuthor.text =
//                "Posted by u/${item.author} ${countTime(item.createdUtc)} hours ago"
//            commentsCount.text = "${item.numComments}k Comments"
//            memeImg.setOnClickListener {
//                clickEvent(item.urlOverriddenByDest)
//            }
//        }
        override fun onBind(position: Int) {
            super.onBind(position)
            val item = topNewsList[position]

            Glide.with(itemView).load(item.urlOverriddenByDest).into(memeImg)
            postedTimeAndAuthor.text =
                "Posted by u/${item.author} ${countTime(item.createdUtc)} hours ago"
            commentsCount.text = "${item.numComments}k Comments"
            memeImg.setOnClickListener {
                clickEvent(item.urlOverriddenByDest)
            }
        }

        private fun countTime(unixTime: Long): Long {
            val currentTime = Calendar.getInstance().time
            val pastTime = Date(unixTime * 1000)
            val timeDiff = currentTime.time - pastTime.time
            return timeDiff / 3600000
        }

        override fun clear() {
        }
    }


    inner class ProgressHolder(itemView: View) :
        BaseViewHolder(itemView) {
        override fun clear() {
        }
    }
}


