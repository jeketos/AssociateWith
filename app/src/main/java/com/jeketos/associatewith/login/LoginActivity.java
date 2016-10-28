package com.jeketos.associatewith.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jeketos.associatewith.R;
import com.jeketos.associatewith.drawer.DrawerActivity;
import com.jeketos.associatewith.guesser.GuesserActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class LoginActivity extends AppCompatActivity {

    @OnClick(R.id.button_drawer) void onDrawerClick(){
        startDrawerActivity();
    }

    @OnClick(R.id.button_guesser) void onGuesserClick(){
        startGuesserActivity();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void startDrawerActivity() {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }

    private void startGuesserActivity() {
        Intent intent = new Intent(this, GuesserActivity.class);
        startActivity(intent);
    }

}
