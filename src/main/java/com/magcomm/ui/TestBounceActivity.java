package com.magcomm.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.graphics.Color;
import com.magcomm.demos.R;
import com.magcomm.views.BounceView;

/**
 * Created by lenovo on 15-10-14.
 */
public class TestBounceActivity extends Activity {

    private BounceView mView;
    private static final int RED = Color.RED;
    private static final int BLUE = Color.BLUE;
    private static final int GREEN = Color.GREEN;

    private enum COLORS{
        RED, BLUE, GREEN
    }

    private COLORS mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bounce_layout);

        mView = (BounceView)findViewById(R.id.bounceView);
    }

    public void doChangeColor(View view){

    }
}
