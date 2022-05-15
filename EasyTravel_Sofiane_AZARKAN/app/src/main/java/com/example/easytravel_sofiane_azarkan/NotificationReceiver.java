package com.example.easytravel_sofiane_azarkan;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "personnal notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent landingIntent = new Intent(context, MainActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent landingPendingIntent = PendingIntent.getActivity(context, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable. ic_vacation_notification)
                .setContentTitle("It's your first day of vacation !")
                .setContentText("It's time to find a good vacation destination ...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(landingPendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
