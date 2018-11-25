package com.willwong.moviepages;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG ="MainActivity";
    ViewPager viewPager;
    private boolean mLogShown;
    EditText inputView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.willwong.moviepages.R.layout.activity_main);

        button = (Button)findViewById(R.id.button_id);

        inputView = (EditText)findViewById(R.id.input_text);

        viewPager = (ViewPager) findViewById(com.willwong.moviepages.R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);



        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new mTimerTask(), 4000, 4000);

        if (savedInstanceState == null) {
            //allows fragment to be dynamic by using FragmentManager and
            // FragmentTransaction class to add, remove, and replace fragments
            //in the layout of activity.
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String input = inputView.getText().toString();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    RecyclerViewFragment fragment = RecyclerViewFragment.newInstance(input);
                    transaction.replace(R.id.fragment_container, fragment).
                            commit();
                }
            });
        }

    }

    public class mTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    }else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    }else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }



}