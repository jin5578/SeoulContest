package com.tistory.jeongs0222.seoulcontest.model.goModel

object GoModel {

    data class storeItem(val CGG_CODE: String,
                         val ASGN_YY: String,
                         val ASGN_SNO: String,
                         val APPL_YMD: String,
                         val ASGN_YMD: String,
                         val UPSO_NM: String,
                         val SITE_ADDR_RD: String,
                         val SITE_ADDR: String,
                         val PERM_NT_NO: String,
                         val SNT_UPTAE_NM: String,
                         val MAIN_EDF: String,
                         val TRDP_AREA: Float,
                         val ADMDNG_NM: String,
                         val GRADE_FACIL_GBN: String,
                         val UPSO_SITE_TELNO: String)

    data class goModel(val row: MutableList<GoModel.storeItem>)
}