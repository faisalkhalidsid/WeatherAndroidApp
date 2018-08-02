package faisalkhalid.weatherappandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    private static final int MY_NOTIFICATION_ID=1;
    Intent in;
    PendingIntent pendingIntent;
    Notification mBuilder;

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
/*
            long when = System.currentTimeMillis();
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("LOL")
                    .setContentText("LOL").setSound(alarmSound)
                    .setAutoCancel(true).setWhen(when)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationManager.notify(0, mNotifyBuilder.build());

*/

            Log.d("notification","called");
            in=new Intent(context,MainActivity.class);
            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pendingIntent=PendingIntent.getActivity(context, 0, in, 0);


            String notificationText = context.getSharedPreferences("Preference",Context.MODE_PRIVATE).getString("notification","-1");



            mBuilder=new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("Weather Update")
                    .setContentText(notificationText)
                    .setChannelId("test")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            createNotificationChannel(context);
            mNotificationManager.notify(MY_NOTIFICATION_ID, mBuilder);


            Log.d("Notification", "Alarm"); //Optional, used for debuging.
            /*
            if (intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
                mNotificationManager.notify(100,mBuilder);
                Log.d("Notification", "Alarm"); //Optional, used for debuging.
            }


*/




        }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test";
            String description = "Des";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
        }
    }



}
