package com.kor.foodmanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.addEvent.AddEventFragment;
import com.kor.foodmanager.ui.home.HomeFragment;
import com.kor.foodmanager.ui.login.LoginFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMain{
    public static final String LOGIN_SCREEN = "LOGIN_SCREEN";
    public static final String HOME_SCREEN = "HOME_SCREEN";
    public static final String ADD_EVENT_SCREEN = "ADD_EVENT_SCREEN";
    public static final String TAG = "MY_TAG";
    @InjectPresenter MainActivityPresenter presenter;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        presenter.startWork();
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),R.id.root) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey){
                case LOGIN_SCREEN:
                    return new LoginFragment();
                case HOME_SCREEN:
                    return new HomeFragment();
                case ADD_EVENT_SCREEN:
                    return new AddEventFragment();
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.removeNavigator();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}