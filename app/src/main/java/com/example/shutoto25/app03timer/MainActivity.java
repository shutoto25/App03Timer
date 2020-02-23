package com.example.shutoto25.app03timer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Handlerを使ったTimerアプリ.
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTimerText;
    private Button mTimer;
    private Button mReset;
    private int mCount;
    private int mPeriod;
    /**
     * データ書式の作成.
     */
    private final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("mm.ss.S", Locale.JAPAN);
    /**
     * スタートボタンテキスト.
     */
    private final String BUTTON_START = "START";
    /**
     * ストップボタンテキスト.
     */
    private final String BUTTON_STOP = "STOP";
    /**
     * タイマー初期値.
     */
    private final int DEFAULT_VALUE = 0;
    /**
     * タイマー現在値.
     */
    private Object mObject;
    /**
     * タイマーハンドラ.
     */
    private Handler mHandler = new Handler();
    /**
     * ランナブル.
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mCount++;
            mObject = mCount * mPeriod;
            mTimerText.setText(DATE_FORMAT.format(mObject));
            mHandler.postDelayed(this, mPeriod);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimerText = findViewById(R.id.tvTimerText);
        mTimerText.setText(DATE_FORMAT.format(DEFAULT_VALUE));
        mTimer = findViewById(R.id.btTimer);
        mTimer.setText(BUTTON_START);
        mTimer.setOnClickListener(buttonClick);
        mReset = findViewById(R.id.btReset);
        mReset.setOnClickListener(buttonClick);

        mCount = 0;
        mPeriod = 100;
    }

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                // スタートまたはストップ.
                case R.id.btTimer:
                    if (mTimer.getText().equals(BUTTON_START)) {
                        // スタートボタン.
                        mHandler.post(mRunnable);
                        mTimer.setText(BUTTON_STOP);
                    } else {
                        // ストップボタン.
                        mTimer.setText(BUTTON_START);
                        Object currentTime = mObject;
                        // 一度handlerを削除し、現在値を再設定.
                        mHandler.removeCallbacks(mRunnable);
                        mTimerText.setText(DATE_FORMAT.format(currentTime));
                    }
                    break;

                // リセット.
                case R.id.btReset:
                    mHandler.removeCallbacks(mRunnable);
                    mTimerText.setText(DATE_FORMAT.format(DEFAULT_VALUE));
                    mTimer.setText(BUTTON_START);
                    mCount = 0;
                    break;
            }
        }
    };
}