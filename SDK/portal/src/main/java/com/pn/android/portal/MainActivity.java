package com.pn.android.portal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.pn.android.portal.ui.CircleRectFooter;


public class MainActivity extends AppCompatActivity {

    private Button btnstart;
    private CircleRectFooter circle3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle3 = (CircleRectFooter)findViewById(R.id.circle3);
        this.btnstart = (Button) findViewById(R.id.btn_start);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circle3.startAnimation();
            }
        });
    }
}
