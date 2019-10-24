package com.example.makeus.Module;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.makeus.MainActivity;
import com.example.makeus.Model.Soldier;
import com.example.makeus.R;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


public class Notifier {
    private final static AtomicInteger random = new AtomicInteger(0);
    final String CHANNEL_ID = "293856";
    private Context context;

    public Notifier(Context context) {

        this.context = context;
        createNotificationChannel();
    }

    public void Notify(Soldier soldier, String test, Date dueDate) {
        int dayCount = (int) (dueDate.getTime() - Calendar.getInstance().getTimeInMillis()) / (24 * 60 * 60 * 1000);

        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final String dueDateString = year + "년 " + month + "월 " + day + "일";

        sendNotification(soldier.rank + " " + soldier.name, test + " " + dueDateString + "까지 D-" +dayCount);
    }

    private void sendNotification(String title, String text) {
        NotificationManagerCompat.from(context).notify(random.incrementAndGet(), buildNotification(title, text).build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Make Us";
            String description = "Make us notification channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent CreateIntent() {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(new Intent(context, MainActivity.class));
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        return resultPendingIntent;
    }

    private NotificationCompat.Builder buildNotification(String title, String text) {

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(CreateIntent());
    }
}