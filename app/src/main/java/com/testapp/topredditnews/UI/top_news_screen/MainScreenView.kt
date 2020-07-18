package com.testapp.topredditnews.UI.top_news_screen

import com.testapp.topredditnews.data.response.Post

interface MainScreenView {
    fun showTopNews(newsList: List<Post>)
    fun showLoading()
    fun stopLoading()
}