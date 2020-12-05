package com.zfhrp6.androidapp1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlin.concurrent.thread
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.UnsupportedOperationException
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class ScheduledTask : Service() {
    suspend fun get() {
        withContext(Dispatchers.IO) {
            Log.d("D", "get")
            "http://192.168.0.118:8000/".httpGet().response()
        }
    }

    private suspend fun post() {
        withContext(Dispatchers.IO) {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(Req::class.java)
            val loc = LatLong(1.11f, 2.22f)
            val now = LocalDateTime.now()
            val hoge = adapter.toJson(
                Req(
                    loc,
                    now.format(DateTimeFormatter.ISO_DATE_TIME),
                    ZoneId.systemDefault().toString(),
                    listOf(BtId(UUID.randomUUID().toString()))
                )
            )
            Log.d("f", hoge)
            "http://192.168.0.118:8000/"
                .httpPost()
                .header("Content-Type", "application/json")
                .body(hoge).response()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d("DEBUG", object {}.javaClass.enclosingMethod.name)
        throw UnsupportedOperationException("not yet")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("DEBUG", object {}.javaClass.enclosingMethod.name)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val name = "notification title"
        val id = "zfhrp6_hoge"
        val notifyChannelDescription = "zfhrp6_channel"

        if (manager.getNotificationChannel(id) == null) {
            val channel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
            channel.apply {
                description = notifyChannelDescription
                lightColor = Color.BLACK
            }
            channel.enableVibration(false)
            channel.enableLights(false)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, id).apply {
            setContentTitle("")
            setContentText("")
            setSmallIcon(android.R.drawable.btn_star)
            setWhen(System.currentTimeMillis())
            setAutoCancel(true)
        }.build()
        startForeground(1, notification)

        thread {
            while (true) {
                GlobalScope.launch { get() }
                GlobalScope.launch { post() }
                Thread.sleep(5 * 1000)
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DEBUG", object {}.javaClass.enclosingMethod.name)
    }
}