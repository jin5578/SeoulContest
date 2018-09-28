package com.tistory.jeongs0222.seoulcontest.ui.place

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.tistory.jeongs0222.seoulcontest.api.GoApiClient
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers



class PlacePresenter: PlaceContract.Presenter, TextWatcher {

    private lateinit var view: PlaceContract.View
    private lateinit var context: Context

    private lateinit var pAdapter: PlaceAdapter

    private var compositeDisposable = CompositeDisposable()

    private var order: Int = 0

    private var s_index = 1
    private var e_index = 100

    private var isLoading = false

    private val goApiClient by lazy { GoApiClient.create(order) }

    override fun setView(view: PlaceContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun setUpSearchFunc() {
        view.nameEditText().addTextChangedListener(this@PlacePresenter)
    }

    override fun setUpRecyclerView(order: Int) {
        this.order = order
        pAdapter = PlaceAdapter(context)

        view.recyclerView().apply {
            adapter = pAdapter
            layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    override fun setUpData() {
        isLoading = true

        when(order) {
            0 -> {
                compositeDisposable
                        .add(goApiClient.gangseoApi(s_index, e_index)
                                .subscribeOn(Schedulers.io())
                                .doOnNext {
                                    if(it.GangseoModelRestaurantDesignate.row.isNotEmpty()) {
                                        pAdapter.addAllItems(it.GangseoModelRestaurantDesignate.row)
                                        s_index += 100
                                        e_index += 100
                                    }
                                }
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnComplete {

                                    pAdapter.notifyChanged()

                                    isLoading = false
                                }
                                .doOnError {
                                    it.printStackTrace()

                                    isLoading = false
                                }
                                .subscribe {  }
                        )
            }

            1 -> {
                compositeDisposable
                        .add(goApiClient.yangcheonApi(s_index, e_index)
                                .subscribeOn(Schedulers.io())
                                .doOnNext {
                                    if(it.YcModelRestaurantDesignate.row.isNotEmpty()) {
                                        pAdapter.addAllItems(it.YcModelRestaurantDesignate.row)
                                        s_index += 100
                                        e_index += 100
                                    }
                                }
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnComplete {

                                    pAdapter.notifyChanged()

                                    isLoading = false
                                }
                                .doOnError {
                                    it.printStackTrace()

                                    isLoading = false
                                }
                                .subscribe {  }
                        )
            }

            2 -> {
                compositeDisposable
                        .add(goApiClient.guroApi(s_index, e_index)
                                .subscribeOn(Schedulers.io())
                                .doOnNext {
                                    if(it.GuroModelRestaurantDesignate.row.isNotEmpty()) {
                                        pAdapter.addAllItems(it.GuroModelRestaurantDesignate.row)
                                        s_index += 100
                                        e_index += 100
                                    }
                                }
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnComplete {

                                    pAdapter.notifyChanged()

                                    isLoading = false
                                }
                                .doOnError {
                                    it.printStackTrace()

                                    isLoading = false
                                }
                                .subscribe {  }
                        )
            }

        }
    }

    override fun loadMore() {
        val linearLayoutManager: LinearLayoutManager = view.recyclerView().layoutManager as LinearLayoutManager
        view.recyclerView().addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if(!isLoading && linearLayoutManager.itemCount - 1 == linearLayoutManager.findLastCompletelyVisibleItemPosition()) {
                    setUpData()
                }
            }
        })
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

    override fun disposableClear() = compositeDisposable.clear()
}