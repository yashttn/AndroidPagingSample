package com.example.mypagingpoc

import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDataSource : PageKeyedDataSource<Int, Item>() {

    companion object {
        val PAGE_SIZE = 50
        private val FIRST_PAGE = 1
        private val SITE_NAME = "stackoverflow"
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        RetrofitClient.instance?.api?.getAnswers(FIRST_PAGE, PAGE_SIZE, SITE_NAME)
            ?.enqueue(object : Callback<StackApiResponse?> {
                override fun onResponse(
                    call: Call<StackApiResponse?>, response: Response<StackApiResponse?>
                ) {
                    response.body()?.items?.let {
                        callback.onResult(it, null, FIRST_PAGE + 1)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse?>, t: Throwable) {}
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        RetrofitClient.instance?.api?.getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            ?.enqueue(object : Callback<StackApiResponse?> {
                override fun onResponse(
                    call: Call<StackApiResponse?>, response: Response<StackApiResponse?>
                ) {
                    response.body()?.items?.let {
                        val key = if (params.key > 1) (params.key - 1) else null
                        callback.onResult(it, key)
                    }
                }

                override fun onFailure(call: Call<StackApiResponse?>, t: Throwable) {}
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        RetrofitClient.instance?.api?.getAnswers(params.key, PAGE_SIZE, SITE_NAME)
            ?.enqueue(object : Callback<StackApiResponse?> {
                override fun onResponse(
                    call: Call<StackApiResponse?>, response: Response<StackApiResponse?>
                ) {
                    response.body()?.let {
                        val key = if (it.has_more) (params.key + 1) else null
                        it.items?.let { it1 ->
                            callback.onResult(it1, key)
                        }
                    }
                }

                override fun onFailure(call: Call<StackApiResponse?>, t: Throwable) {}
            })
    }

}