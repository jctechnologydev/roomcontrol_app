package pt.ipca.smartrooms.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import pt.ipca.smartrooms.MainActivity
import pt.ipca.smartrooms.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseCM"
    private var token = ""

    fun start() : Task<String>{
        return FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    Log.w(TAG, "Fetchign FCM registration token failed", task.exception)
                    return@addOnCompleteListener
                }

                token = task.result
                Log.d(TAG, token)
            }
    }

    // Subscrever a um tópico para enviar a todos
    fun subscribeTopics(context: Context) {
        FirebaseMessaging.getInstance().subscribeToTopic("ALERT")
            .addOnCompleteListener { task ->
                var msg = "Subscribed"
                if (!task.isSuccessful) {
                    msg = "Subscribe failed"
                }
                Log.d(TAG, msg)
                //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
    }


    fun start(context: Context){
        start().addOnCompleteListener {
            subscribeTopics(context)
            //FirebaseMessaging.getInstance().unsubscribeFromTopic("ALERT")
            //Toast.makeText(context, "Token: $token", Toast.LENGTH_LONG).show()
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        this.token = token
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "From: ${message.from}")
        Log.d(TAG, "To: ${message.to}")
        Log.d(TAG, "data: ${message.data}")
        Log.d(TAG, "RawData: ${message.rawData}")

        message.notification?.let { notification ->
            Log.d(TAG, "Title: ${notification.title}")
            Log.d(TAG, "Body: ${notification.body}")
            sendNotification(notification.title ?: "Title", notification.body ?: "body")
        }
    }

    fun sendNotification(title: String, message: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val channelId = "fcm_default_channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}