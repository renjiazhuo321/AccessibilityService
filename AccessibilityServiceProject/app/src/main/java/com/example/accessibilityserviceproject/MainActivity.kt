package com.example.accessibilityserviceproject
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.accessibilityserviceproject.activity.GrabTicketsActivity
import com.example.accessibilityserviceproject.fw_permission.FloatWinPermissionCompat
import com.example.accessibilityserviceproject.service.AutoTouchService
import com.example.accessibilityserviceproject.util.AccessibilityUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initClick()
    }

    private fun initClick() {
        grab_tickets_btn.setOnClickListener {
            startActivity(Intent(this, GrabTicketsActivity::class.java))
        }
        jd_rush_btn.setOnClickListener {
            Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show()
        }
        emd_automatic_str.setOnClickListener {
            Toast.makeText(this, "暂未开放", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        checkState()
        tip_tv.setOnClickListener {
            when (tip_tv.text) {
                "开始" -> {
//                    startService(Intent(this, FloatingService::class.java))
                    moveTaskToBack(true)
                }
                "通知栏权限" -> {
                    requestPermissionAndShow()
                }
                "无障碍权限" -> {
                    requestAcccessibility()
                }
            }
        }
    }

    private fun checkState() {
        val hasAccessibility: Boolean =
            AccessibilityUtil.isSettingOpen(AutoTouchService::class.java, this@MainActivity)
        val hasWinPermission: Boolean = FloatWinPermissionCompat.getInstance().check(this)
        if (hasAccessibility) {
            if (hasWinPermission) {
                tip_tv.text = "开始"
            } else {
                tip_tv.text = "通知栏权限"
            }
        } else {
            tip_tv.text = "无障碍权限"
        }
    }

    /**
     * 开启悬浮窗权限
     */
    private fun requestPermissionAndShow() {
        AlertDialog.Builder(this).setTitle("悬浮窗权限未开启")
            .setMessage(getString(R.string.app_name) + "获得悬浮窗权限，才能正常使用应用")
            .setPositiveButton("去开启") { dialog, which -> // 显示授权界面
                try {
                    FloatWinPermissionCompat.getInstance().apply(this@MainActivity)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.setNegativeButton("取消", null).show()
    }


    private fun requestAcccessibility() {
        AlertDialog.Builder(this).setTitle("无障碍服务未开启")
            .setMessage("你的手机没有开启无障碍服务，" + getString(R.string.app_name) + "将无法正常使用")
            .setPositiveButton("去开启") { dialog, which -> // 显示授权界面
                try {
                    AccessibilityUtil.jumpToSetting(this@MainActivity)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }.setNegativeButton("取消", null).show()
    }
}
