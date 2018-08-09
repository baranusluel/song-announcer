package com.baranusluel.songannouncer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MusicBroadcastReceiver extends BroadcastReceiver {

    private static String prevArtist = "";
    private static String prevTrack = "";

    private TextToSpeech t2s;

    private NotificationCompat.Builder notifBuilder;
    private int NOTIFICATION_ID;

    public MusicBroadcastReceiver(int notifID, NotificationCompat.Builder notifBuilder) {
        this.notifBuilder = notifBuilder;
        this.NOTIFICATION_ID = notifID;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("SongAnnouncer", "onReceive");

        String action = intent.getAction();
        if (action == null)
            return;
        Log.v("SongAnnouncer", action);

        String artist = intent.getStringExtra("artist");
        String album = intent.getStringExtra("album");
        String track = intent.getStringExtra("track");
        Log.v("SongAnnouncer", artist + ":" + album + ":" + track);
        //Toast.makeText(context, track + " by " + artist, Toast.LENGTH_SHORT).show();

        notifBuilder.setContentText(String.format(
                context.getResources().getString(R.string.notification_text),
                track + " by " + artist));
        NotificationManager notifManager = context.getSystemService(NotificationManager.class);
        if (notifManager != null)
            notifManager.notify(NOTIFICATION_ID, notifBuilder.build());

        track = track.replaceAll("\\.", " ").toLowerCase();
        artist = artist.replaceAll("\\.", " ").toLowerCase();

        if (action.contains("meta")
                && !artist.equals(prevArtist)
                && !track.equals(prevTrack)) {
            Log.v("SongAnnouncer", "TextToSpeech");
            if (t2s == null) {
                t2s = new TextToSpeech(context, (status) -> {
                    //t2s.setLanguage(Locale.US);
                    t2s.setPitch(0.85f);
                    t2s.setSpeechRate(0.9f);
                });
            }
            t2s.speak(track + ", by " + artist, TextToSpeech.QUEUE_FLUSH, null, null);
        }

        prevArtist = artist;
        prevTrack = track;
    }
}