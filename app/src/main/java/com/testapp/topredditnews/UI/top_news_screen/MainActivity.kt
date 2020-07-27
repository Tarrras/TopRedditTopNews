package com.testapp.topredditnews.UI.top_news_screen

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.testapp.topredditnews.R
import com.testapp.topredditnews.UI.adapters.PaginationScrollListener
import com.testapp.topredditnews.UI.adapters.PaginationScrollListener.Companion.PAGE_START
import com.testapp.topredditnews.UI.adapters.TopNewsRecyclerViewAdapter
import com.testapp.topredditnews.UI.image_screen.ImageFragment
import com.testapp.topredditnews.data.network.RedditApiService
import com.testapp.topredditnews.data.repositories.TopRedditNewsRepositoryImpl
import com.testapp.topredditnews.data.response.Post
import kotlinx.android.synthetic.main.main_screen.*

const val KEY_RECYCLER_STATE = "recycler_state"

class MainActivity : AppCompatActivity(), MainScreenView, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mainScreenPresenter: MainScreenPresenter
    private lateinit var topNewsRecyclerView: RecyclerView
    private lateinit var newsAdapter: TopNewsRecyclerViewAdapter
    private lateinit var postsList: List<Post>


    private var currentPage: Int = PAGE_START
    private var isLastPage = false
    private val totalPage = 10
    private var isLoading = false
    var itemCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        init()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val listState = topNewsRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_RECYCLER_STATE, listState)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        if (savedInstanceState.containsKey(KEY_RECYCLER_STATE)) {
            var mPosition = savedInstanceState.getInt(KEY_RECYCLER_STATE);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            topNewsRecyclerView.smoothScrollToPosition(mPosition);
        }

        super.onRestoreInstanceState(savedInstanceState)
    }


    private fun init() {
        swipe_refresh.setOnRefreshListener(this)
        topNewsRecyclerView = main_recycler
        topNewsRecyclerView.setHasFixedSize(true)
        newsAdapter = TopNewsRecyclerViewAdapter { urlImage: String ->
            val imageFragment = ImageFragment.newInstance(urlImage)
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment, imageFragment).addToBackStack(null).commit()
            Toast.makeText(this, urlImage, Toast.LENGTH_SHORT).show()
        }
        val linLayoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        topNewsRecyclerView.apply {
            layoutManager = linLayoutManager
            adapter = newsAdapter
        }

        val apiService = RedditApiService()
        val newsRepository = TopRedditNewsRepositoryImpl(apiService = apiService)
        mainScreenPresenter = MainScreenPresenterImpl(newsRepository)
        mainScreenPresenter.attachView(this)
        mainScreenPresenter.loadNews()

        topNewsRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(linLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                mainScreenPresenter.loadNews(after = postsList[postsList.lastIndex].postId)
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScreenPresenter.detachView()
    }

    override fun showTopNews(newsList: List<Post>) {
        postsList = newsList
        if (currentPage != PAGE_START) newsAdapter.removeLoading()
        newsAdapter.setupNewsList(newList = newsList)
        swipe_refresh.isRefreshing = false

        if (currentPage < totalPage) {
            newsAdapter.addLoading()
        } else isLastPage = true

        isLoading = false
    }

    override fun showLoading() {
        main_recycler.visibility = View.GONE
    }

    override fun stopLoading() {
        main_recycler.visibility = View.VISIBLE
    }

    override fun onRefresh() {
        itemCount = 0
        currentPage = PAGE_START;
        isLastPage = false;
        newsAdapter.clearList()
        mainScreenPresenter.loadNews()
    }
}