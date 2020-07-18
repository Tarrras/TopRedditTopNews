package com.testapp.topredditnews.UI.top_news_screen

import com.testapp.topredditnews.data.repositories.TopRedditNewsRepository
import com.testapp.topredditnews.data.response.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainScreenPresenterImpl(private val repository: TopRedditNewsRepository) : MainScreenPresenter {
    private var mainScreenView: MainScreenView? = null

    override fun attachView(mainView: MainScreenView) {
        mainScreenView = mainView
    }

    override fun detachView() {
        mainScreenView = null
    }

    override fun loadNews() {
        mainScreenView?.showLoading()
        val postList = ArrayList<Post>()
        CoroutineScope(Main).launch {
            val response = repository.loadNews().data.children
            response.forEach { item ->
                postList.add(item.data)
            }
            mainScreenView?.stopLoading()
            mainScreenView?.showTopNews(postList)
        }

    }


}