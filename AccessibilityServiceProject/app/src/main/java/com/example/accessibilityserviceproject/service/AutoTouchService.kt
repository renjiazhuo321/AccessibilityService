package com.example.accessibilityserviceproject.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class AutoTouchService: AccessibilityService() {
    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
    }
}