package com.tistory.jeongs0222.seoulcontest.api

import com.tistory.jeongs0222.seoulcontest.model.goModel.GoModel
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GoApiService {

    /*@GET("1/10/")
    fun bringStore(): Observable<StoreItems>

    data class StoreItems(val GuroModelRestaurantDesignate: GoModel.GuroModel)*/
    @GET("{START_INDEX}/{END_INDEX}")
    fun gangseoApi(@Path("START_INDEX") s_index: Int,
                @Path("END_INDEX") e_index: Int): Observable<GangseoItems>

    data class GangseoItems(val GangseoModelRestaurantDesignate: GoModel.goModel)

    @GET("{START_INDEX}/{END_INDEX}")
    fun yangcheonApi(@Path("START_INDEX") s_index: Int,
                @Path("END_INDEX") e_index: Int): Observable<YangcheonItems>

    data class YangcheonItems(val YcModelRestaurantDesignate: GoModel.goModel)

    @GET("{START_INDEX}/{END_INDEX}")
    fun guroApi(@Path("START_INDEX") s_index: Int,
                @Path("END_INDEX") e_index: Int): Observable<GuroItems>

    data class GuroItems(val GuroModelRestaurantDesignate: GoModel.goModel)


}