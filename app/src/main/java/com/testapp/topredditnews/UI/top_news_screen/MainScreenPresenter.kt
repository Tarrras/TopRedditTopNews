package com.testapp.topredditnews.UI.top_news_screen

import com.testapp.topredditnews.data.response.Post

interface MainScreenPresenter {
    fun attachView(mainView: MainScreenView)
    fun detachView()
    fun loadNews(count: Int = 25, after: String = "")
}