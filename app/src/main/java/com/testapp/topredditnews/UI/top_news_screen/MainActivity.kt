package com.testapp.topredditnews.UI.top_news_screen

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testapp.topredditnews.R
import com.testapp.topredditnews.UI.adapters.TopNewsRecyclerViewAdapter
import com.testapp.topredditnews.UI.image_screen.ImageFragment
import com.testapp.topredditnews.data.network.RedditApiService
import com.testapp.topredditnews.data.repositories.TopRedditNewsRepositoryImpl
import com.testapp.topredditnews.data.response.Post
import kotlinx.android.synthetic.main.main_screen.*

const val KEY_RECYCLER_STATE = "recycler_state"
const val KEY_RECYCLER_LIST_STATE = "recycler_list_state"
class MainActivity : AppCompatActivity(), MainScreenView {

    private lateinit var mainScreenPresenter: MainScreenPresenter
    private lateinit var topNewsRecyclerView: RecyclerView
    private lateinit var newsAdapter: TopNewsRecyclerViewAdapter
    private var isSavedStateNotNull = false
    private lateinit var postsList: List<Post>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)
        if(savedInstanceState != null) isSavedStateNotNull = true
        init()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        val listState = topNewsRecyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_RECYCLER_STATE, listState)
//        outState.putParcelableArrayList(KEY_RECYCLER_LIST_STATE, postsList)
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
        topNewsRecyclerView = main_recycler
        newsAdapter = TopNewsRecyclerViewAdapter{ urlImage: String ->
            val imageFragment = ImageFragment.newInstance(urlImage)
            supportFragmentManager.beginTransaction()
                .add(R.id.main_fragment, imageFragment).addToBackStack(null).commit()
            Toast.makeText(this,urlImage, Toast.LENGTH_SHORT).show()
        }
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
        postsList = newsList
        newsAdapter.setupNewsList(newList = newsList)
    }

    override fun showLoading() {
        main_recycler.visibility = View.GONE
    }

    override fun stopLoading() {
        main_recycler.visibility = View.VISIBLE
    }
}