package com.dnights.randomImage_restfullapi

import com.dnights.randomImage_restfullapi.api.API
import com.dnights.randomImage_restfullapi.api.AccessKey
import com.dnights.randomImage_restfullapi.api.RetrofitAdapter
import com.dnights.randomImage_restfullapi.api.Urls
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Test

class APIUnitTest {
    val compositeDisposable = CompositeDisposable()

    @Test
    fun apiTest() {
        RetrofitAdapter.getInstance(Urls.getBaseUrl())
            .create(API::class.java)
            .fetchPhotos(AccessKey.accessKey, 1)
            .subscribeOn(TrampolineSchedulerProvider().io())
            .observeOn(TrampolineSchedulerProvider().ui())
            .subscribe({response ->
                println(response.headers())
                val list = response.body()?: emptyList()
                list.map {data ->
                    println(data.toString())
                }
            },{
                it.printStackTrace()
            }).let {
                compositeDisposable.add(it)
            }
    }

    @After
    fun after() {
        compositeDisposable.clear()
    }
}