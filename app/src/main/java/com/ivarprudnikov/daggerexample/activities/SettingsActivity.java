package com.ivarprudnikov.daggerexample.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ivarprudnikov.daggerexample.R;
import com.ivarprudnikov.daggerexample.settings.AppSettingsLoginFragment;
import com.ivarprudnikov.daggerexample.settings.MainPreferenceFragment;
import com.ivarprudnikov.daggerexample.settings.PasswordService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements AppSettingsLoginFragment.OnDetachListener, AppSettingsLoginFragment.OnAuthenticateListener {

    @BindView(R.id.content_settings)
    LinearLayout content;

    PasswordService mPasswordService;
    AppSettingsLoginFragment mAppSettingsLoginFragment;
    private boolean isAuthenticated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPasswordService = new PasswordService(this);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        content.removeAllViewsInLayout();
        showLoginDialog();
    }

    private void showLoginDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(AppSettingsLoginFragment.TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        if (mAppSettingsLoginFragment == null)
            mAppSettingsLoginFragment = new AppSettingsLoginFragment();

        mAppSettingsLoginFragment.show(ft, AppSettingsLoginFragment.TAG);
    }

    private void showPreferences() {
        getFragmentManager().beginTransaction()
                .add(R.id.content_settings, new MainPreferenceFragment())
                .commit();
    }

    @Override
    public boolean onLoginFragmentAuthenticate(String candidate) {
        isAuthenticated = mPasswordService.checkCandidate(candidate);
        return isAuthenticated;
    }

    @Override
    public void onLoginFragmentDetached() {
        if (!isAuthenticated)
            this.finish();
        else
            showPreferences();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

}

