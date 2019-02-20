package com.kor.foodmanager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends MvpAppCompatFragment implements IHome{
    @InjectPresenter HomePresenter presenter;
    @BindView(R.id.hello_text) TextView helloTxt;
    @BindView(R.id.logout_btn) Button logoutBtn;
    private Unbinder unbinder;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.logout_btn)
    public void logout(){
        presenter.logout();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startWork();
    }

    @Override
    public void showHello(String name) {
        String str = helloTxt.getText().toString();
        helloTxt.setText("Hello, "+name+"!");
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
