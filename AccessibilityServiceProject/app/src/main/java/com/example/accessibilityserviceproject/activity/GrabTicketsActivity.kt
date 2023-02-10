package com.example.accessibilityserviceproject.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.accessibilityserviceproject.DataStore.dataStore
import com.example.accessibilityserviceproject.R
import com.example.accessibilityserviceproject.util.DensityUtil
import com.example.accessibilityserviceproject.util.ext.observe
import com.example.accessibilityserviceproject.viewmodel.GrabTicketViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_grab_tickets.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//火车票配置页面
@AndroidEntryPoint
class GrabTicketsActivity : BaseActivity() {
    override val layoutId: Int
        get() = R.layout.activity_grab_tickets
    private val mViewModel: GrabTicketViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initClick()
        binds()
    }

    private fun binds() {
        observe(mViewModel.grabDataListState) { state ->
            if (state.data != null) {
                val data = state.data as Set<String>
                for (i in data.indices) {
                    val nameEditText = name_cl.getChildAt(i) as EditText
                    nameEditText.setText(data.toList()[i])
                }
            }
        }
        observe(mViewModel.tripListState) { state ->
            if (state.data != null) {
                val data = state.data as Set<String>
                for (i in 0 until data!!.size) {
                    val tripsEditText = trips_ll.getChildAt(i) as EditText
                    tripsEditText.setText(data.toList()[i])
                }
            }
        }
    }


    private fun initClick() {
        save_btn.setOnClickListener {
            val nameList = mutableListOf<String>()
            val tripsList = mutableListOf<String>()
            for (i in 0 until name_cl.childCount) {
                val nameEditText = name_cl.getChildAt(i) as EditText
                if (nameEditText.text.toString().isNotEmpty()) {
                    nameList.add(nameEditText.text.toString().trim())
                }
            }
            for (i in 0 until trips_ll.childCount) {
                val tripsEditText = trips_ll.getChildAt(i) as EditText
                if (tripsEditText.text.toString().isNotEmpty()) {
                    tripsList.add(tripsEditText.text.toString().trim())
                }
            }
            mViewModel.saveNameAndTrip(nameList, tripsList)

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun initView() {
        for (i in 1..5) {
            val nameEditText = EditText(this)
            nameEditText.width = DensityUtil.dip2px(this, 100f)
            nameEditText.height = DensityUtil.dip2px(this, 50f)
            name_cl.addView(nameEditText)
        }
        for (i in 1..5) {
            val nameEditText = EditText(this)
            nameEditText.width = DensityUtil.dip2px(this, 100f)
            nameEditText.height = DensityUtil.dip2px(this, 50f)
            trips_ll.addView(nameEditText)
        }

        mViewModel.queryNameAndTrip()
    }


}


