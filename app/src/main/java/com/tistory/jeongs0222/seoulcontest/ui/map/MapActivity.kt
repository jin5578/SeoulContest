package com.tistory.jeongs0222.seoulcontest.ui.map

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.tistory.jeongs0222.seoulcontest.R
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity(), MapContract.View {

    private lateinit var mPresenter: MapPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        init()
    }

    private fun init() {
        mPresenter = MapPresenter()

        mPresenter.setView(this, this)

        mPresenter.setUpRecyclerView()
    }

    override fun recyclerView(): RecyclerView = map_recyclerView

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == RESULT_OK) {
            if(requestCode == 3) {
                val intent = Intent()
                intent.putExtra("name", data!!.getStringExtra("name"))
                setResult(RESULT_OK, intent)

                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}
