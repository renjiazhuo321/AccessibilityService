package com.example.accessibilityserviceproject.service

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.*
import android.view.View.OnTouchListener
import com.example.accessibilityserviceproject.R
import com.example.accessibilityserviceproject.util.DensityUtil
import com.example.accessibilityserviceproject.util.WindowUtils

class FloatingService: Service() {
    private var mWindowManager: WindowManager? = null
    private var mFloatingView: View? = null
    private var floatLayoutParams: WindowManager.LayoutParams? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mFloatingView = creatView<View>(R.layout.layout_window)
        //设置WindowManger布局参数以及相关属性
        val d: Int = DensityUtil.dip2px(this, 50f)
        floatLayoutParams = WindowUtils.newWmParams(d, d)
        //初始化位置
        floatLayoutParams!!.gravity = Gravity.TOP or Gravity.START
        floatLayoutParams!!.x = WindowUtils.getScreenWidth(this) - DensityUtil.dip2px(this, 80f)
        floatLayoutParams!!.y = WindowUtils.getScreenHeight(this) - DensityUtil.dip2px(this, 200f)
        //获取WindowManager对象
        mWindowManager = WindowUtils.getWindowManager(this)
        addViewToWindow(mFloatingView, floatLayoutParams)
        //FloatingView的拖动事件
        mFloatingView!!.isClickable = true
        mFloatingView!!.setOnTouchListener(object : OnTouchListener {
            private var x = 0
            private var y = 0

            //是否在移动
            private var isMoving = false

            @SuppressLint("ClickableViewAccessibility")
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        x = event.rawX.toInt()
                        y = event.rawY.toInt()
                        isMoving = false
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val nowX = event.rawX.toInt()
                        val nowY = event.rawY.toInt()
                        val moveX = nowX - x
                        val moveY = nowY - y
                        if (Math.abs(moveX) > 0 || Math.abs(moveY) > 0) {
                            isMoving = true
                            floatLayoutParams!!.x += moveX
                            floatLayoutParams!!.y += moveY
                            //更新View的位置
                            mWindowManager!!.updateViewLayout(mFloatingView, floatLayoutParams)
                            x = nowX
                            y = nowY
                            return true
                        }
                    }
                    MotionEvent.ACTION_UP -> if (!isMoving) {
                        onShowSelectDialog()
                        return true
                    }
                }
                return false
            }
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onShowSelectDialog() {

    }

    override fun onDestroy() {
        super.onDestroy()
        removeViewFromWinddow(mFloatingView)
    }

    private fun hideDialog(dialog: Dialog?) {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }

    private fun addViewToWindow(view: View?, params: WindowManager.LayoutParams?) {
        if (mWindowManager != null) {
            mWindowManager!!.addView(view, params)
        }
    }

    private fun removeViewFromWinddow(view: View?) {
        if (mWindowManager != null && view != null && view.isAttachedToWindow) {
            mWindowManager!!.removeView(view)
        }
    }

    private fun <T : View?> creatView(layout: Int): T {
        return LayoutInflater.from(this).inflate(layout, null) as T
    }
}