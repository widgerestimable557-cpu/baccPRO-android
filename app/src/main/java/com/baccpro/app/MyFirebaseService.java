package com.baccpro.app;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
public class MyFirebaseService extends FirebaseMessagingService {
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        getSharedPreferences("FCM", MODE_PRIVATE).edit().putString("token", token).apply();
    }
    @Override
    public void onMessageReceived(RemoteMessage msg) {
        super.onMessageReceived(msg);
        String title = "baccPRO", body = "Nouveau message", type = "general";
        if (!msg.getData().isEmpty()) {
            if (msg.getData().containsKey("title"))   title = msg.getData().get("title");
            if (msg.getData().containsKey("message")) body  = msg.getData().get("message");
            if (msg.getData().containsKey("type"))    type  = msg.getData().get("type");
        }
        if (msg.getNotification() != null) {
            if (msg.getNotification().getTitle() != null) title = msg.getNotification().getTitle();
            if (msg.getNotification().getBody()  != null) body  = msg.getNotification().getBody();
        }
        createChannels();
        showNotif(title, body, type);
    }
    private void createChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;
        NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        m.createNotificationChannel(new NotificationChannel("rappel",     "Rappels", NotificationManager.IMPORTANCE_DEFAULT));
        m.createNotificationChannel(new NotificationChannel("streak",     "Streak",  NotificationManager.IMPORTANCE_HIGH));
        m.createNotificationChannel(new NotificationChannel("motivation", "Motiv",   NotificationManager.IMPORTANCE_DEFAULT));
    }
    private void showNotif(String title, String body, String type) {
        String ch = type.equals("streak") ? "streak" : type.equals("rappel") ? "rappel" : "motivation";
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i,
            PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, ch)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pi);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
            .notify((int) System.currentTimeMillis(), b.build());
    }
}
