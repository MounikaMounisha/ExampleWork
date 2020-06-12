package com.example.examplework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    OneTimeWorkRequest firstrequest,secondrequest;
    PeriodicWorkRequest thirdwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constraints c = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        firstrequest = new OneTimeWorkRequest.Builder(FirstWork.class).setConstraints(c).build();
        thirdwork = new PeriodicWorkRequest.Builder(ThirdWork.class,15, TimeUnit.MINUTES).build();
        Data data = new Data.Builder().putString("key","This is second request").build();
        secondrequest = new OneTimeWorkRequest.Builder(SecondRequest.class).setInitialDelay(5, TimeUnit.SECONDS).setInputData(data).build();
    }
    public void alert(View view) {
        WorkManager.getInstance(this).beginWith(firstrequest).then(secondrequest).enqueue();
        WorkManager.getInstance(this).enqueue(thirdwork);
        //WorkManager.getInstance(this).enqueue(secondrequest);
    }
}

