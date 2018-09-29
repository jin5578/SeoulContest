package com.tistory.jeongs0222.seoulcontest.api

import com.tistory.jeongs0222.seoulcontest.model.hostModel.HoModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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

    //recommendWriting
    @Multipart
    @POST("recommendWriting.php")
    fun recommendWriting(@Part up_image: List<MultipartBody.Part>,
                         @PartMap params: Map<String, @JvmSuppressWildcards RequestBody>): Observable<recommendWritings>

    data class recommendWritings(val value: Int, val message: String)

}