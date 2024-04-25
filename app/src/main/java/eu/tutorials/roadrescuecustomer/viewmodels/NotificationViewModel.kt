package eu.tutorials.roadrescuecustomer.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import eu.tutorials.roadrescuecustomer.util.NotificationHelper

class NotificationViewModel : ViewModel() {

    fun initNotificationChannel(context: Context) {
        NotificationHelper.createNotificationChannel(context)
    }

    fun sendNotification(context: Context, title: String, message: String) {
        NotificationHelper.sendNotification(context, title, message)
    }
}
