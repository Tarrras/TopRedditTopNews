package com.testapp.topredditnews.data.repositories

import com.testapp.topredditnews.data.network.RedditApiService
import com.testapp.topredditnews.data.response.Response
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class TopRedditNewsRepositoryImpl(val apiService: RedditApiService): TopRedditNewsRepository {

    override suspend fun loadNews() = withContext(Default) {
        apiService.getTopPosts()
    }

}