package com.example.mycashier;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessegingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        if (remoteMessage.getData().size()>0){
            String scrren =remoteMessage.getData().get("screen");
            String userId =remoteMessage.getData().get("userId");
            if("profile".equals(scrren)){
                sendProfileNotification(userId);
            }
        }

    }
    private void sendProfileNotification(String userId){
        Intent intent=new Intent(this,MyProfileActivity.class);
        intent.putExtra("userId",userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);
        String channelId ="profile_channel";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.profile).setContentTitle("open profile ")
                .setContentText("Click to open the Profile page ")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel= new NotificationChannel(channelId,"Profile Notificattion",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(0,notificationBuilder.build());
}
}