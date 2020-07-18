package com.testapp.topredditnews.UI.top_news_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testapp.topredditnews.R
import com.testapp.topredditnews.UI.adapters.TopNewsRecyclerViewAdapter
import com.testapp.topredditnews.data.network.RedditApiService
import com.testapp.topredditnews.data.repositories.TopRedditNewsRepositoryImpl
import com.testapp.topredditnews.data.response.Post
import kotlinx.android.synthetic.main.main_screen.*

class MainActivity : AppCompatActivity(), MainScreenView {

    private lateinit var mainScreenPresenter: MainScreenPresenter
    private lateinit var topNewsRecyclerView: RecyclerView
    private lateinit var newsAdapter: TopNewsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        init()
    }

    private fun init() {
        topNewsRecyclerView = main_recycler
        newsAdapter = TopNewsRecyclerViewAdapter()
        topNewsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }

        val apiService = RedditApiService()
        val newsRepository = TopRedditNewsRepositoryImpl(apiService = apiService)
        mainScreenPresenter = MainScreenPresenterImpl(newsRepository)
        mainScreenPresenter.attachView(this)
        mainScreenPresenter.loadNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScreenPresenter.detachView()
    }

    override fun showTopNews(newsList: List<Post>) {
        newsAdapter.setupNewsList(newList = newsList)
    }

    override fun showLoading() {
        main_recycler.visibility = View.GONE
    }

    override fun stopLoading() {
        main_recycler.visibility = View.VISIBLE
    }
}