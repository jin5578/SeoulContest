package com.tistory.jeongs0222.seoulcontest.api

import com.tistory.jeongs0222.seoulcontest.util.ArrayUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GoApiClient {

    companion object {

        private var baseUrl = ""

        fun create(district: Int): GoApiService {

            baseUrl = ArrayUtil.urlList[district]

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(GoApiService::class.java)


        }
    }
}