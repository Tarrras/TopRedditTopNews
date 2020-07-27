package com.testapp.topredditnews.data.repositories

import com.testapp.topredditnews.data.response.Response

interface TopRedditNewsRepository {
    suspend fun loadNews(count: Int, after: String): Response
}