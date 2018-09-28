package com.tistory.jeongs0222.seoulcontest.api

import com.tistory.jeongs0222.seoulcontest.model.hostModel.HoModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface HoApiService {

    //recommendItem
    @FormUrlEncoded
    @POST("recommend.php")
    fun bringRecommend(@Field("category_order_page") pageNumber: Int): Observable<recommendItems>

    data class recommendItems(val recommend: MutableList<HoModel.recommendItem>)
}