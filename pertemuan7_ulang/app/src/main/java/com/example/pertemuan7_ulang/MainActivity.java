package com.example.pertemuan7_ulang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private SeekBar playerControl;
    private ImageButton btnPlay, btnStop;
    private TextView txtDisplayDuration;

    private MediaPlayer mediaPlayer;

    private double startTime = 0;
    private double finalTime = 0;

    private boolean isPLaying = false;

    private Handler syncHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd0bj = getAssets().openFd("VivalaVida.mp3");
            mediaPlayer.setDataSource(afd0bj.getFileDescriptor());
            Toast.makeText(this, "Tekan tombol play untuk memutar lagu", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        playerControl.setProgress(0);

        btnPlay.setOnClickListener(view -> {
            if (!isPLaying){
                isPLaying = true;

                btnPlay.setImageResource(R.drawable.ic_pause);
                startTime = mediaPlayer.getCurrentPosition();

                txtDisplayDuration.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                        TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes((long) startTime)
                        )
                ));

                playerControl.setMax((int) finalTime);
                playerControl.setProgress((int) startTime);

                mediaPlayer.start();
                syncHandler.postDelayed(updateDuration,100);
            } else {
                isPLaying = false;

                btnPlay.setImageResource(R.drawable.ic_play);
                mediaPlayer.pause();
            }
        });

        btnStop.setOnClickListener(view -> {
            mediaPlayer.seekTo(0);
            mediaPlayer.pause();
            playerControl.setProgress(0);
            btnPlay.setImageResource(R.drawable.ic_play);
            isPLaying = false;
        });

        playerControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Runnable updateDuration = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            txtDisplayDuration.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes((long) startTime)
                            )
            ));

            playerControl.setProgress((int) startTime);
            syncHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}