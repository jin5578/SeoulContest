package com.tistory.jeongs0222.seoulcontest.ui.place

import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.R
import kotlinx.android.synthetic.main.activity_place.*

class PlaceActivity : AppCompatActivity(), PlaceContract.View {

    private lateinit var mPresenter: PlacePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        init()
    }

    private fun init() {
        mPresenter = PlacePresenter()

        mPresenter.setView(this, this)

        mPresenter.setUpSearchFunc()

        mPresenter.setUpRecyclerView()
    }

    override fun nameEditText(): EditText = place_name_editText

    override fun searchVisibility(sort: Int) {
        when(sort) {
            0 -> place_search_textView.visibility = View.GONE

            1 -> place_search_textView.visibility = View.VISIBLE
        }
    }

    override fun recyclerView(): RecyclerView = place_recyclerView
}
