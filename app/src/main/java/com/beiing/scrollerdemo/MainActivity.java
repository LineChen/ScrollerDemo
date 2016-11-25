package com.beiing.scrollerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout llScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llScroll = (LinearLayout) findViewById(R.id.ll_scroll);
    }


    public void btnScroll(View view) {
        int x = System.currentTimeMillis() % 2 == 0 ? 30 : -30;
        view.scrollBy(x, 0);
    }

    public void layoutScrollLeft(View view) {
        llScroll.scrollBy(30, 0);
    }

    public void layoutScrollRight(View view) {
        llScroll.scrollBy(-30, 0);
    }


}
