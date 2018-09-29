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
    fun bringRecommend(@Field("category_order_page") pageNumber: Int): Observable<bringRecommends>

    data class bringRecommends(val recommend: MutableList<HoModel.recommendItem>)

    //recommendLike
    @FormUrlEncoded
    @POST("nice.php")
    fun recommendLike(@Field("order") order: Int): Observable<recommendLikes>

    data class recommendLikes(val value: Int, val message: String)   //0: 성공, 1: 실패

}