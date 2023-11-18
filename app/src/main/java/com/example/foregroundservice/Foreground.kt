package com.example.foregroundservice

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class Foreground : Service() {

    val CHANNEL_ID = "FGS153"
    val NOTI_ID = 153
    private lateinit var manager: NotificationManager

    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // createNotificationChannel()
        showNotification()

        // runBackground()

        return super.onStartCommand(intent, flags, startId)
    }

    private val notificationManager by lazy {
        NotificationManagerCompat.from(this)
    }



    private fun showNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            // val manager = getSystemService(NotificationManager::class.java)
           // notificationManager.createNotificationChannel(serviceChannel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingItent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingItent)

        var i = 0

        CoroutineScope(Dispatchers.Main).launch {
            repeat(1000) {i ->
                notification.setProgress(1000, i, false)
                startForeground(NOTI_ID, notification.build())
                delay(1000)
            }
        }
        val handler = Handler(Looper.getMainLooper())

       /* thread{
            for(i in 0..1000){
                *//*handler.post {
                    *//**//*if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@post
                    }
                    notificationManager.notify(NOTI_ID, notification.setProgress(100, i, false).build())*//**//*
                    notification.setProgress(1000, i, false)
                    // manager.notify(NOTI_ID, notification.build())
                    startForeground(NOTI_ID, notification.build())
                }*//*
                notification.setProgress(1000, i, false)
                startForeground(NOTI_ID, notification.build())
                Thread.sleep(1000)
            }
        }*/


    }

    private fun runBackground() {
        /*thread(start=true){
            for(i in 0..1000){
                Thread.sleep(1000)
                Log.d("서비스", "COUNT ===> $i")
            }
        }*/
        Thread(){
            for (i in 0..1000){
                Thread.sleep(1000)

            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}