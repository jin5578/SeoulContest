package com.tistory.jeongs0222.seoulcontest.ui.main

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import com.tistory.jeongs0222.seoulcontest.api.HoApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainPresenter: MainContract.Presenter {

    private lateinit var view: MainContract.View
    private lateinit var context: Context

    private lateinit var mAdapter: MainAdapter

    private var isLoading = false

    private var compositeDisposable = CompositeDisposable()

    private var pageNumber: Int = 0

    private var isFirstLoad = true

    private val apiClient by lazy { HoApiClient.create() }


    override fun setView(view: MainContract.View, context: Context) {
        this.view = view
        this.context = context
    }

    override fun setUpRecyclerView() {
        mAdapter = MainAdapter(context) {
            //like 이미지 바꾸는거 보여줘야한다

            postLike(it)
        }

        view.recyclerView().apply {
            adapter = mAdapter

            layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    override fun setUpData() {
        isLoading = true

        compositeDisposable
                .add(apiClient.bringRecommend(pageNumber)
                        .subscribeOn(Schedulers.io())
                        .doOnNext {
                            if(it.recommend.isNotEmpty()) {
                                mAdapter.addAllItems(it.recommend)
                                pageNumber += it.recommend.size
                            }
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete {
                            if(isFirstLoad) {
                                isFirstLoad = false
                            }
                            mAdapter.notifyChanged()
                            isLoading = false
                        }
                        .doOnError {
                            it.printStackTrace()
                            isLoading = false
                        }
                        .subscribe {

                        }
                )
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

    private fun postLike(order: Int) {
        compositeDisposable
                .add(apiClient.recommendLike(order)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnError {
                            it.printStackTrace()
                        }
                        .subscribe()
                )
    }
}