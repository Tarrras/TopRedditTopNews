package com.testapp.topredditnews.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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

class TopNewsRecyclerViewAdapter : RecyclerView.Adapter<TopNewsViewHolder>() {

    private val topNewsList: ArrayList<Post> = ArrayList()

    fun setupNewsList(newList: List<Post>) {
        topNewsList.clear()
        topNewsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopNewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_list_item, parent, false)
        return TopNewsViewHolder(view)
    }

    override fun getItemCount() = topNewsList.size

    override fun onBindViewHolder(holder: TopNewsViewHolder, position: Int) {
        holder.bindItem(item = topNewsList[position])
    }
}

class TopNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val memeImg = itemView.meme_img
    private val postedTimeAndAuthor = itemView.posted_by_and_time_tv
    private val commentsCount = itemView.comments_count_tv
    fun bindItem(item: Post) {
        Picasso.get().load(item.thumbnail).into(memeImg)
        postedTimeAndAuthor.text =
            "Posted by u/${item.author} ${countTime(item.createdUtc.toString())} hours ago"
        commentsCount.text = "${item.numComments}k Comments"
    }

    private fun countTime(unixTime: String): Long {
//        val timeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//        val currentTime = Calendar.getInstance().time
//        val pastTime = Date(unixTime.toLong() * 1000)
//        val timeDiff = currentTime.time - pastTime.time
//        return timeDiff / 3600
        return 3600 / 3600
    }
}
