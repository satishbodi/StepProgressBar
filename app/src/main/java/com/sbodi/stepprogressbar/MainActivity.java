package com.sbodi.stepprogressbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.sbodi.stepprogressbar.library.view.StepProgressBar;


public class MainActivity extends AppCompatActivity {

    private StepProgressBar mStepProgressBar;
    private Handler mUIHandler;
    private int mCurrentStep = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStepProgressBar = (StepProgressBar) findViewById(R.id.step_progress_view);
        final int totalSteps = getResources().getInteger(R.integer.default_no_steps);
        mUIHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (mCurrentStep >= totalSteps) {
                    mCurrentStep = 0;
                }
                mStepProgressBar.updateStepState(mCurrentStep++);
                mUIHandler.sendEmptyMessageDelayed(0, 2000);
            }
        };
        mUIHandler.sendEmptyMessage(0);
    }

}
