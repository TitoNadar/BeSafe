package com.example.tito.womensecurity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tito.womensecurity.Common.Helper;
import com.example.tito.womensecurity.Interface.SmsService;
import com.example.tito.womensecurity.Modal.Response;
import com.example.tito.womensecurity.Services.MyService;
import com.github.piasy.rxandroidaudio.AudioRecorder;
import com.github.piasy.rxandroidaudio.PlayConfig;
import com.github.piasy.rxandroidaudio.RxAudioPlayer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit.Callback;
import retrofit.Retrofit;

import static com.example.tito.womensecurity.Services.MyService.RECORD_AUDIO;
import static com.example.tito.womensecurity.Services.MyService.WRITE_STORAGE;

public class MainActivity extends AppCompatActivity {
    private static AudioRecorder mAudioRecorder;
    private static RxAudioPlayer mRxAudioPlayer;
    private static File mAudioFile;
  static long  a;
  private StorageReference tito;
SmsService smsService;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smsService= Helper.getSmsService();
        tito= FirebaseStorage.getInstance().getReference();
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
        //sendSMS();
//uploadToFirebase(Environment.getExternalStorageDirectory().getAbsolutePath() +
  //      File.separator +a+".file.m4a");

}

    private void sendSMS() {
        smsService.getResponse(Helper.getApiUrl()).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                if(Integer.parseInt(response.body().getMessageIDs())>0)
                {
                    Log.d("sms","success");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    private void uploadToFirebase(String filePath) {
        if (filePath != null) {
            //displaying a progress dialog while upload is going on

            StorageReference riversRef = tito.child("audios/recordings.m4a");
            riversRef.putFile(Uri.parse(filePath))
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                           Log.d("success","uploaded");

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog

Log.d("fail","not uploaded");
                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
Log.d("progress",progress+"");
                            //displaying percentage in progress dialog

                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
            Log.d("error","no such file");
        }
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
