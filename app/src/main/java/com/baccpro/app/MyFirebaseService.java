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
        getSharedPreferences("FCM",MODE_PRIVATE).edit().putString("token",token).apply();
    }
    @Override
    public void onMessageReceived(RemoteMessage msg) {
        super.onMessageReceived(msg);
        String title="baccPRO", body="Nouveau message", type="general";
        if(!msg.getData().isEmpty()){
            if(msg.getData().containsKey("title"))   title=msg.getData().get("title");
            if(msg.getData().containsKey("message")) body=msg.getData().get("message");
            if(msg.getData().containsKey("type"))    type=msg.getData().get("type");
        }
        if(msg.getNotification()!=null){
            if(msg.getNotification().getTitle()!=null) title=msg.getNotification().getTitle();
            if(msg.getNotification().getBody()!=null)  body=msg.getNotification().getBody();
        }
        createChannels();
        showNotif(title,body,type);
    }
    private void createChannels(){
        if(Build.VERSION.SDK_INT
    
Copier la commande

  
sed -i 's||    \n|' app/src/main/AndroidManifest.xml && echo "✅ Manifest OK"
git add . && git commit -m "Add Firebase FCM notifications" && git push
