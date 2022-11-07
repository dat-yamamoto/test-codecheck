/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.util.*

/**
 * TwoFragment で使う
 */
class OneViewModel : ViewModel() {
    val lastSearchDate : MutableLiveData<Date> by lazy {
        MutableLiveData<Date>()
    }

    // 検索結果
    fun searchResults(inputText: String): List<item> = runBlocking {
        val items = mutableListOf<item>()
        items.clear()

        return@runBlocking GlobalScope.async {
            try {
                val client = HttpClient(Android)
                val response: HttpResponse = client.get("https://api.github.com/search/repositories") {
                    header("Accept", "application/vnd.github.v3+json")
                    parameter("q", inputText)
                }

                val jsonBody = JSONObject(response.receive<String>())

                val jsonItems = jsonBody.optJSONArray("items")!!

                /**
                 * アイテムの個数分ループする
                 */
                for (i in 0 until jsonItems.length()) {
                    val jsonItem = jsonItems.optJSONObject(i)!!
                    val name = jsonItem.optString("full_name")
                    val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
                    val language = jsonItem.optString("language")
                    val stargazersCount = jsonItem.optLong("stargazers_count")
                    val watchersCount = jsonItem.optLong("watchers_count")
                    val forksCount = jsonItem.optLong("forks_conut")
                    val openIssuesCount = jsonItem.optLong("open_issues_count")

                    items.add(
                        item(
                            name = name,
                            ownerIconUrl = ownerIconUrl,
                            language = language,
                            stargazersCount = stargazersCount,
                            watchersCount = watchersCount,
                            forksCount = forksCount,
                            openIssuesCount = openIssuesCount
                        )
                    )
                }
                lastSearchDate.postValue(Date())
            } catch (e: Exception) {
                //
            }
            return@async items.toList()
        }.await()
    }

    fun getDate() : String {
        var rtnval = "日時不明"
        if (lastSearchDate.value != null){
            rtnval = lastSearchDate.value.toString()
        }
        return rtnval
    }
}