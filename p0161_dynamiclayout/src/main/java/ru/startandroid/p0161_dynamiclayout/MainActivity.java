package ru.startandroid.p0161_dynamiclayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // create LinearLayout
        LinearLayout linLayout = new LinearLayout(this);
        // set vertical orientation
        linLayout.setOrientation(LinearLayout.VERTICAL);
        // create LayoutParams
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // linLayout as root element
        setContentView(linLayout, linLayoutParam);

        // LayoutParams WRAP_CONTENT
        ViewGroup.LayoutParams lpView = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // create TextView
        TextView tv = new TextView(this);
        // set text
        tv.setText("TextView");
        // set params
        tv.setLayoutParams(lpView);
        // add view to Layout
        linLayout.addView(tv);

        // create button
        Button btn = new Button(this);
        // set text
        btn.setText("Button");
        // add button to layout and set params
        linLayout.addView(btn, lpView);

        // LayoutParams wrap content params
        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // add param margin left for new LayoutParams
        leftMarginParams.leftMargin = 50;

        // Button with leftMarginParams
        Button btnLeftMargin = new Button(this);
        btnLeftMargin.setText("Button 1");
        // add button to Layout
        linLayout.addView(btnLeftMargin, leftMarginParams);

        LinearLayout.LayoutParams rightGravityParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        rightGravityParams.gravity = Gravity.RIGHT;

        Button btnRightGravity = new Button(this);
        btnRightGravity.setText("Button 2");
        linLayout.addView(btnRightGravity, rightGravityParams);
    }
}
