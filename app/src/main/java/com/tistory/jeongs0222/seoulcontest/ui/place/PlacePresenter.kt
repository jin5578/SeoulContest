package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.Editable
import android.text.TextWatcher


class PlacePresenter: PlaceContract.Presenter, TextWatcher {

    private lateinit var view: PlaceContract.View
    private lateinit var context: Context

    private lateinit var pAdapter: PlaceAdapter

    override fun setView(view: PlaceContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun setUpSearchFunc() {
        view.nameEditText().addTextChangedListener(this@PlacePresenter)
    }

    override fun setUpRecyclerView() {
        pAdapter = PlaceAdapter(context)

        view.recyclerView().apply {
            adapter = pAdapter
            layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if(p0.toString().length == 0) {
            view.searchVisibility(0)
        } else {
            view.searchVisibility(1)
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}