package com.example.grocerylist

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.security.acl.NotOwnerException

const val notificationid  = 1;
const val channelid = "channel1"
class Notification : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent?) {


        val notification : Notification = NotificationCompat.Builder(context, channelid)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hi ... you have a notification")
            .setContentText("amandsffssefewfewbg")
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationid,notification);



    }


}