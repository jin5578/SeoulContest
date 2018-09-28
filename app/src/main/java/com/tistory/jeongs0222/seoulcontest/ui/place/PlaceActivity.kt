package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.tistory.jeongs0222.seoulcontest.R
import kotlinx.android.synthetic.main.activity_place.*


class PlaceActivity : AppCompatActivity(), PlaceContract.View {

    private lateinit var mPresenter: PlacePresenter

    private var order = 0

    private var selected_name = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        init()
    }

    private fun init() {
        getValue()

        mPresenter = PlacePresenter()

        mPresenter.setView(this, this)

        mPresenter.setUpSearchFunc()

        mPresenter.setUpRecyclerView(order) {
            selected_name = it

            val intent = Intent()
            intent.putExtra("name", it)
            setResult(RESULT_OK, intent)

            finish()
        }

        mPresenter.setUpData()

        mPresenter.loadMore()
    }

    private fun getValue() {
        order = getIntent().extras.getInt("order")
    }

    override fun nameEditText(): EditText = place_name_editText

    override fun searchVisibility(sort: Int) {
        when(sort) {
            0 -> place_search_textView.visibility = View.GONE

            1 -> place_search_textView.visibility = View.VISIBLE
        }
    }

    override fun recyclerView(): RecyclerView = place_recyclerView

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }

    override fun onDestroy() {
        mPresenter.disposableClear()

        super.onDestroy()

    }
}
