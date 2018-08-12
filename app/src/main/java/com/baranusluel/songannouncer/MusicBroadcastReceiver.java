package com.baranusluel.songannouncer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MusicBroadcastReceiver extends BroadcastReceiver {

    private static String prevArtist = "";
    private static String prevTrack = "";

    private NotificationManager notifManager;
    private NotificationCompat.Builder notifBuilder;
    private int NOTIFICATION_ID;

    public MusicBroadcastReceiver(Context context, int notifID, NotificationCompat.Builder notifBuilder) {
        this.notifBuilder = notifBuilder;
        this.NOTIFICATION_ID = notifID;
        notifManager = context.getSystemService(NotificationManager.class);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("SongAnnouncer", "onReceive()");

        String action = intent.getAction();
        if (action == null)
            return;
        Log.v("SongAnnouncer", action);

        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String track = intent.getStringExtra("track");
        Log.v("SongAnnouncer", artist + ":" + album + ":" + track);

        notifBuilder.setContentText(String.format(
                context.getResources().getString(R.string.notification_text),
                track + " by " + artist));

        notifManager.notify(NOTIFICATION_ID, notifBuilder.build());

        if (action.contains("meta") && (!artist.equals(prevArtist) || !track.equals(prevTrack)))
            Speech.speak(context, track + ", by " + artist);

        prevArtist = artist;
        prevTrack = track;
    }
}