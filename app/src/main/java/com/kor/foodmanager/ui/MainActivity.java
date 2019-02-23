package com.kor.foodmanager.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.addEvent.AddEventFragment;
import com.kor.foodmanager.ui.eventList.EventListFragment;
import com.kor.foodmanager.ui.home.HomeFragment;
import com.kor.foodmanager.ui.login.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMain{
    public static final String LOGIN_SCREEN = "LOGIN_SCREEN";
    public static final String HOME_SCREEN = "HOME_SCREEN";
    public static final String ADD_EVENT_SCREEN = "ADD_EVENT_SCREEN";
    public static final String EVENT_LIST_SCREEN = "EVENT_LIST_SCREEN";
    public static final String SHOW_PROGRESS = "SHOW_PROGRESS";
    public static final String HIDE_PROGRESS = "HIDE_PROGRESS";
    public static final String TAG = "MY_TAG";
    @InjectPresenter MainActivityPresenter presenter;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        progressFrame.setOnClickListener(null);
        presenter.startWork();
    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        new AlertDialog.Builder(this)
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
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
                case EVENT_LIST_SCREEN:
                    return new EventListFragment();
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            switch (message){
                case SHOW_PROGRESS:
                    showProgressFrame();
                    break;
                case HIDE_PROGRESS:
                    hideProgressFrame();
                    break;
                    default:
                        showError(message);
            }
        }

        @Override
        protected void exit() {
            getSupportFragmentManager().popBackStack();
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