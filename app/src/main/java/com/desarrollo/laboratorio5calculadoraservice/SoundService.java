package com.desarrollo.laboratorio5calculadoraservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SoundService extends Service {
    MediaPlayer reproductor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        reproductor = MediaPlayer.create(getApplicationContext(),R.raw.back_theme);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        reproductor.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        reproductor.stop();
    }
}
