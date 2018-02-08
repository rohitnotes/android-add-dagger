package com.ivarprudnikov.daggerexample.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivarprudnikov.daggerexample.R;
import com.ivarprudnikov.daggerexample.settings.Theme;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends AppCompatActivity {

    @BindView(R.id.customer_content)
    TextView customerContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String themeLabel = sharedPref.getString(getString(R.string.pref_customer_theme_key), Theme.DEFAULT.getLabel());
        Theme theme = Theme.fromLabel(themeLabel);
        customerContent.setText(getString(R.string.activity_theme_text, theme.getLabel()));
        customerContent.setBackgroundColor(theme.getBackgroundHex());
        customerContent.setTextColor(theme.getForegroundHex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}