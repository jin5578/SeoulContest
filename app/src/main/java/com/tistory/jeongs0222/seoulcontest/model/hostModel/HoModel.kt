package com.tistory.jeongs0222.seoulcontest.model.hostModel

object HoModel {

    data class recommendItem(val order: Int,
                             val store: String,
                             val menu: String,
                             val like: Int,
                             val image: String)
}