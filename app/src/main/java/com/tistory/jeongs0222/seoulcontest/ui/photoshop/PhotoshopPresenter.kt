package com.tistory.jeongs0222.seoulcontest.ui.photoshop

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.tistory.jeongs0222.seoulcontest.api.HoApiClient
import com.tistory.jeongs0222.seoulcontest.util.DynamicViewUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class PhotoshopPresenter: PhotoshopContract.Presenter {

    private val TAG = "PhotoshopPresenter"

    private lateinit var view: PhotoshopContract.View
    private lateinit var context: Context
    private lateinit var assets: AssetManager

    private lateinit var hAdapter: PhotoshopPlaceAdapter
    private lateinit var sAdapter: PhotoshopStampAdapter
    private lateinit var mAdapter: PhotoshopSizeAdapter
    private lateinit var pAdapter: PhotoshopPaintAdapter
    private lateinit var fAdapter: PhotoshopFontAdapter

    private var compositeDisposable = CompositeDisposable()

    private var parts: MutableList<MultipartBody.Part> = ArrayList()

    private val apiClient by lazy { HoApiClient.create() }


    override fun setView(view: PhotoshopContract.View, context: Context, assets: AssetManager) {
        this.view = view
        this.context = context
        this.assets = assets
    }

    @SuppressLint("ResourceType")
    override fun setUpRecyclerView(sort: Int) {

        when(sort) {
            0 -> hAdapter = PhotoshopPlaceAdapter(context) { String, it ->
                if(String.equals("1")) {
                    dynamicStoreTextView(it)
                } else {
                    dynamicMenuTextView(it)
                }
            }

            1 -> sAdapter = PhotoshopStampAdapter(context) {

            }

            2 -> mAdapter = PhotoshopSizeAdapter(context) {
                if(existTextView(1)) {
                    DynamicViewUtil.dynamicTextSize(it, view.tempRelativeLayout().findViewById(1))
                }
            }

            3 -> pAdapter = PhotoshopPaintAdapter(context) {
                dividePaint(it)
            }

            4 -> fAdapter = PhotoshopFontAdapter(context) {
                divideFont(it)
            }
        }

        view.recyclerView().apply {
            adapter = when(sort) {
                0 -> hAdapter

                1 -> sAdapter

                2 -> mAdapter

                3 -> pAdapter

                4 -> fAdapter

                else -> null
            }

            layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            scrollToPosition(0)
        }
    }

    override fun postData(path: String) {
        setMultiImageSelected(path)

        bringDate()

        compositeDisposable.add(apiClient.recommendWriting(parts, getRecommendData())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete {
                    view.confirmClickable(1)
                    view.viewFinish()
                }
                .doOnError { it.printStackTrace() }
                .subscribe()
        )
    }

    private fun setMultiImageSelected(path: String) {
        val selectedUriList = ArrayList<Uri>()

        selectedUriList.add(Uri.fromFile(File(path)))

        var stringArray = ArrayList<String>()

        for(i in selectedUriList.indices) {
            stringArray.add(selectedUriList[i].path.toString())
        }

        var imagess: Array<String>? = null

        imagess = stringArray.toTypedArray()

        parts = ArrayList<MultipartBody.Part>()

        for(i in imagess!!.indices) {
            parts.add(prepareFilePart(imagess!![i]))
        }
    }

    //MultipartBody로 바꿔주는 부분
    private fun prepareFilePart(uri: String): MultipartBody.Part {
        val file = File(uri)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        return MultipartBody.Part.createFormData("up_image[]", file.name, requestBody)
    }

    private fun bringDate(): String {
        val mNow = System.currentTimeMillis()

        val mDate = Date(mNow)

        val mFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

        return mFormat.format(mDate)
    }

    @SuppressLint("ResourceType")
    private fun getRecommendData(): HashMap<String, RequestBody> {
        return HashMap<String, RequestBody>().apply {
            if(existTextView(1)) {
                this["store"] = toRequestBody(view.tempRelativeLayout().findViewById<TextView>(1).text.toString())
            } else {
                this["store"] = toRequestBody(" ")
            }

            if(existTextView(2)) {
                this["menu"] = toRequestBody(view.tempRelativeLayout().findViewById<TextView>(2).text.toString())
            } else {
                this["menu"] = toRequestBody(" ")
            }

            this["date"] = toRequestBody(bringDate())
        }
    }

    private fun toRequestBody(value: String): RequestBody =
            RequestBody.create(MediaType.parse("text/plain"), value)

    @SuppressLint("ResourceType")
    private fun dividePaint(it: Int) {
        if(existTextView(0)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempRelativeLayout().findViewById(1))
            DynamicViewUtil.dynamicFontColor(it, view.tempRelativeLayout().findViewById(2))
        } else if(existTextView(1)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempRelativeLayout().findViewById(1))
        } else if(existTextView(2)) {
            DynamicViewUtil.dynamicFontColor(it, view.tempRelativeLayout().findViewById(2))
        }
    }

    @SuppressLint("ResourceType")
    private fun divideFont(it: Int) {
        if (existTextView(0)) {
            DynamicViewUtil.dynamicFont(it, view.tempRelativeLayout().findViewById(1), assets)
            DynamicViewUtil.dynamicFont(it, view.tempRelativeLayout().findViewById(2), assets)
        } else if (existTextView(1)) {
            DynamicViewUtil.dynamicFont(it, view.tempRelativeLayout().findViewById(1), assets)
        } else if(existTextView(2)) {
            DynamicViewUtil.dynamicFont(it, view.tempRelativeLayout().findViewById(2), assets)
        }
    }


    @SuppressLint("ResourceType")
    fun dynamicStoreTextView(charSequence: CharSequence) {

        view.tempRelativeLayout().removeAllViews()

        val storeLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        storeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)

        val storeTextView = DynamicViewUtil.dynamicStoreText(context, assets, charSequence)

        storeTextView.layoutParams = storeLayoutParams

        view.tempRelativeLayout().addView(storeTextView)

        if(charSequence.isBlank()) {
            view.tempRelativeLayout().removeView(view.tempRelativeLayout().findViewById(1))
        }



        //GrayScale
        //view.tempImageView().setColorFilter(DynamicViewUtil.grayScale())
    }

    @SuppressLint("ResourceType")
    private fun dynamicMenuTextView(charSequence: CharSequence) {

        val menuLayoutParams = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        menuLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)

        menuLayoutParams.addRule(RelativeLayout.BELOW, 1)

        val menuTextView = DynamicViewUtil.dynamicMenuText(context, assets, charSequence)

        menuTextView.layoutParams = menuLayoutParams

        if(existTextView(2)) {
            view.tempRelativeLayout().removeView(view.tempRelativeLayout().findViewById(2))
        }

        view.tempRelativeLayout().addView(menuTextView)
    }

    @SuppressLint("ResourceType")
    private fun existTextView(sort: Int): Boolean {
        when(sort) {
            0 -> return view.tempRelativeLayout().findViewById<TextView>(1) != null && view.tempRelativeLayout().findViewById<TextView>(2) != null


            1 -> return view.tempRelativeLayout().findViewById<TextView>(1) != null


            2 -> return view.tempRelativeLayout().findViewById<TextView>(2) != null
        }

        return false
    }

    override fun disposableClear() = compositeDisposable.clear()
}