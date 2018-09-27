package com.tistory.jeongs0222.seoulcontest.api

import com.tistory.jeongs0222.seoulcontest.util.ArrayUtil
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GoApiClient {

    companion object {

        private var baseUrl = "http://openapi.gangnam.go.kr:8088/674744486b6a696e35336977436147/json/GnModelRestaurantDesignate/"

        fun create(district: Int): GoApiService {

            baseUrl = ArrayUtil.urlList[district]

            /*when(district) {
                //urlList("강서구", "양천구", "구로구", "영등포구", "동작구", "금천구", "관악구", "서초구", "강남구", "송파구", "강동구", "광진구", "성동구", "용산구", "중구", "마포구", "서대문구", "은평구", "종로구", "성북구", "동대문구", "중랑구", "강북구", "도봉구", "노원구")

                //강서구
                0 -> baseUrl = "http://openAPI.gangseo.seoul.kr:8088/674744486b6a696e35336977436147/json/GangseoModelRestaurantDesignate/"

                //양천구
                1 -> baseUrl = "http://openAPI.yangcheon.go.kr:8088/674744486b6a696e35336977436147/json/YcModelRestaurantDesignate/"

                //구로구
                2 -> baseUrl = "http://openAPI.guro.go.kr:8088/674744486b6a696e35336977436147/json/GuroModelRestaurantDesignate/"

                //영등포구
                3 -> baseUrl = "http://openAPI.ydp.go.kr:8088/674744486b6a696e35336977436147/json/YdpModelRestaurantDesignate/"

                //동작구
                4 -> baseUrl = "http://openAPI.dongjak.go.kr:8088/674744486b6a696e35336977436147/json/DjModelRestaurantDesignate/"

                //금천구
                5 -> baseUrl = "http://openAPI.geumcheon.go.kr:8088/674744486b6a696e35336977436147/json/GeumcheonModelRestaurantDesignate/"

                //관악구
                6 -> baseUrl = "http://openAPI.gwanak.go.kr:8088/674744486b6a696e35336977436147/json/GaModelRestaurantDesignate/"

                //서초구
                7 -> baseUrl = "http://openAPI.seocho.go.kr:8088/674744486b6a696e35336977436147/json/ScModelRestaurantDesignate/"

                //강남구
                8 -> baseUrl = "http://openapi.gangnam.go.kr:8088/674744486b6a696e35336977436147/json/GnModelRestaurantDesignate/"

                //송파구
                9 -> baseUrl = "http://openAPI.songpa.seoul.kr:8088/674744486b6a696e35336977436147/json/SpModelRestaurantDesignate/"

                //강동구
                10 -> baseUrl = "http://openAPI.gd.go.kr:8088/674744486b6a696e35336977436147/json/GdModelRestaurantDesignate/"

                //광진구
                11 -> baseUrl = "http://openAPI.gwangjin.go.kr:8088/674744486b6a696e35336977436147/json/GwangjinModelRestaurantDesignate/"

                //성동구
                12 -> baseUrl = "http://openAPI.sd.go.kr:8088/674744486b6a696e35336977436147/json/SdModelRestaurantDesignate/"

                //용산구
                13 -> baseUrl = "http://openAPI.yongsan.go.kr:8088/674744486b6a696e35336977436147/json/YsModelRestaurantDesignate/"

                //중구
                14 -> baseUrl = "http://openAPI.junggu.seoul.kr:8088/674744486b6a696e35336977436147/json/JungguModelRestaurantDesignate/"

                //마포구
                15 -> baseUrl = "http://openAPI.mapo.go.kr:8088/674744486b6a696e35336977436147/json/MpModelRestaurantDesignate/"

                //서대문구
                16 -> baseUrl = "http://openAPI.sdm.go.kr:8088/674744486b6a696e35336977436147/json/SeodaemunModelRestaurantDesignate/"

                //은평구
                17 -> baseUrl = "http://openAPI.ep.go.kr:8088/674744486b6a696e35336977436147/json/EpModelRestaurantDesignate/"

                //종로구
                18 -> baseUrl = "http://openAPI.jongno.go.kr:8088/674744486b6a696e35336977436147/json/JongnoModelRestaurantDesignate/"

                //성북구
                19 -> baseUrl = "http://openAPI.sb.go.kr:8088/674744486b6a696e35336977436147/json/SbModelRestaurantDesignate/"

                //동대문구
                20 -> baseUrl = "http://openAPI.ddm.go.kr:8088/674744486b6a696e35336977436147/json/DongdeamoonModelRestaurantDesignate/"

                //중랑구
                21 -> baseUrl = "http://openAPI.jungnang.seoul.kr:8088/674744486b6a696e35336977436147/json/JungnangModelRestaurantDesignate/"

                //강북구
                22 -> baseUrl = "http://openAPI.gangbuk.go.kr:8088/674744486b6a696e35336977436147/json/GbModelRestaurantDesignate/"

                //도봉구
                23 -> baseUrl = "http://openAPI.dobong.go.kr:8088/674744486b6a696e35336977436147/json/DobongModelRestaurantDesignate/"

                //노원구
                24 -> baseUrl = "http://openAPI.nowon.go.kr:8088/674744486b6a696e35336977436147/json/NwModelRestaurantDesignate/"
            }*/

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()

            return retrofit.create(GoApiService::class.java)


        }
    }
}