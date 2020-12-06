package com.zfhrp6.androidapp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("DEBUG", "onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("DEBUG", "onStart")

        startJob()
    }

    private val jobId = 1
    private fun startJob(): Unit {
        Log.d("DEBUG", DebugUtils.getEnclosingNames().methodName)
        val intent = Intent(applicationContext, ScheduledTask::class.java)
        ContextCompat.startForegroundService(
            this,
            intent
        )
    }
}

