package com.baranusluel.songannouncer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.IntentFilter;
import android.os.IBinder;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {

    private MusicBroadcastReceiver musicReceiver;
    private final static int NOTIFICATION_ID = 233872; // chosen by finger bashing - guaranteed to be random

    @Override
    public void onCreate() {
        Log.v("SongAnnouncer", "Service.onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("SongAnnouncer", "Service.onStartCommand()");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
                .setDefaults(~Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.baseline_library_music_24)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(String.format(getResources().getString(R.string.notification_text), ""))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        startForeground(NOTIFICATION_ID, notifBuilder.build());

        musicReceiver = new MusicBroadcastReceiver(this, NOTIFICATION_ID, notifBuilder);

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.jrtstudio.music.metachanged");
        iF.addAction("com.jrtstudio.music.playstatechanged");
        iF.addAction("com.jrtstudio.AnotherMusicPlayer.metachanged");
        iF.addAction("com.jrtstudio.AnotherMusicPlayer.playstatechanged");
        iF.addAction("com.htc.music.metachanged");
        iF.addAction("com.htc.music.playstatechanged");
        iF.addAction("com.rdio.android.metachanged");
        iF.addAction("com.rdio.android.playstatechanged");
        iF.addAction("fm.last.android.metachanged");
        iF.addAction("com.miui.player.metachanged");
        iF.addAction("com.miui.player.playstatechanged");
        iF.addAction("com.miui.player.service.metachanged");
        iF.addAction("com.miui.player.service.playstatechanged");
        iF.addAction("com.real.IMP.metachanged");
        iF.addAction("com.samsung.MusicPlayer.metachanged");
        iF.addAction("com.samsung.MusicPlayer.playstatechanged");
        iF.addAction("com.samsung.sec.metachanged");
        iF.addAction("com.samsung.sec.playstatechanged");
        iF.addAction("com.samsung.music.metachanged");
        iF.addAction("com.samsung.music.playstatechanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.metachanged");
        iF.addAction("com.samsung.sec.android.MusicPlayer.playstatechanged");
        iF.addAction("com.samsung.sec.android.metachanged");
        iF.addAction("com.samsung.sec.android.playstatechanged");
        iF.addAction("com.sec.android.app.music.metachanged");
        iF.addAction("com.sec.android.app.music.playstatechanged");
        iF.addAction("com.lge.music.metachanged");
        iF.addAction("com.lge.music.playstatechanged");
        iF.addAction("com.rhapsody.metachanged");
        iF.addAction("com.rhapsody.playstatechanged");
        iF.addAction("com.maxmpz.audioplayer.playstatechanged");
        iF.addAction("com.adam.aslfms.notify.playstatechanged");
        iF.addAction("com.andrew.apollo.metachanged");
        iF.addAction("com.amazon.mp3.playstatechanged");
        iF.addAction("com.amazon.mp3.metachanged");
        iF.addAction("com.spotify.music.metadatachanged");
        iF.addAction("com.spotify.music.playbackstatechanged");
        iF.addAction("com.nullsoft.winamp.metachanged");
        iF.addAction("com.nullsoft.winamp.playstatechanged");
        iF.addAction("com.jetappfactory.jetaudio.playstatechanged");
        iF.addAction("com.jetappfactory.jetaudio.metachanged");
        iF.addAction("com.jetappfactory.jetaudioplus.playstatechanged");
        iF.addAction("com.jetappfactory.jetaudioplus.metachanged");
        iF.addAction("com.e8tracks.playstatechanged");
        iF.addAction("com.e8tracks.metachanged");
        iF.addAction("com.doubleTwist.androidPlayer.metachanged");
        iF.addAction("com.doubleTwist.androidPlayer.playstatechanged");
        iF.addAction("com.tbig.playerpro.metachanged");
        iF.addAction("com.tbig.playerpro.playstatechanged");
        iF.addAction("com.tbig.playerprotrial.metachanged");
        iF.addAction("com.tbig.playerprotrial.playstatechanged");
        iF.addAction("com.android.mediacenter.metachanged");
        iF.addAction("com.android.mediacenter.playstatechanged");
        registerReceiver(musicReceiver, iF);

        return START_STICKY;
    }

    /** A client is binding to the service with bindService() */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Called when The service is no longer used and is being destroyed */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(musicReceiver);
        Toast.makeText(this, "Song Announcer Service Destroyed", Toast.LENGTH_LONG).show();
    }
}