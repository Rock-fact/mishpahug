package com.kor.foodmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kor.foodmanager.ui.eventList.EventListFragment;

public class m2_forTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m2_for_test);
        getSupportFragmentManager().beginTransaction().replace(R.id.root, new EventListFragment()).commit();
    }
}
