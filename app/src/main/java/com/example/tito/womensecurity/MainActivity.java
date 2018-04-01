package com.example.tito.womensecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tito.womensecurity.Services.MyService;
import com.github.piasy.rxandroidaudio.AudioRecorder;
import com.github.piasy.rxandroidaudio.PlayConfig;
import com.github.piasy.rxandroidaudio.RxAudioPlayer;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.tito.womensecurity.Services.MyService.RECORD_AUDIO;
import static com.example.tito.womensecurity.Services.MyService.WRITE_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static AudioRecorder mAudioRecorder;
    private static RxAudioPlayer mRxAudioPlayer;
    private static File mAudioFile;
  static long  a;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    RECORD_AUDIO);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_STORAGE);

        }
        Intent intent = new Intent(this, MyService.class);
        //Start Service
        startService(intent);
    }
    public  void startRecording()
    {


        Log.d("yash","reached here");
        a=System.currentTimeMillis();
        mAudioRecorder = AudioRecorder.getInstance();
        mAudioFile = new File(
                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        File.separator +a+".file.m4a");
        Log.d("path",Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator +a+".file.m4a");
        mAudioRecorder.prepareRecord(MediaRecorder.AudioSource.MIC,
                MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.AudioEncoder.AAC,
                mAudioFile);
        mAudioRecorder.startRecord();
for(int i=0;i<90000;i++)
{}
        mAudioRecorder.stopRecord();
//playRecording();
}

    public static void playRecording()
    {
        mRxAudioPlayer.play(PlayConfig.file(mAudioFile).looping(true).build())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(final Disposable disposable) {

                    }

                    @Override
                    public void onNext(final Boolean aBoolean) {
                        // prepared
                    }

                    @Override
                    public void onError(final Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        // play finished
                        // NOTE: if looping, the Observable will never finish, you need stop playing
                        // onDestroy, otherwise, memory leak will happen!
                        mRxAudioPlayer.stopPlay();
                    }
                });
    }

}
