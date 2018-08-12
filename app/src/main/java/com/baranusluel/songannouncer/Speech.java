package com.baranusluel.songannouncer;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class Speech {

    private static TextToSpeech t2s;

    private static void initialize(Context context) {
        Log.v("SongAnnouncer", "Speech.initialize()");

        t2s = new TextToSpeech(context, (status) -> {
            //t2s.setLanguage(Locale.US);
            t2s.setPitch(0.85f);
            t2s.setSpeechRate(0.9f);
        });
    }

    static void speak(Context context, String text) {
        Log.v("SongAnnouncer", "Speech.speak()");

        if (t2s == null)
            initialize(context);

        text = filter(text);

        t2s.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private static String filter(String input) {
        return input
                .replaceAll("\\.", " ")
                .toLowerCase();
    }
}
