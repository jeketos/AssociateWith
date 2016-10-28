package com.jeketos.associatewith.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jeketos.associatewith.R;
import com.jeketos.associatewith.drawer.DrawActivity;

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

    private void startDrawerActivity() {
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.button_guesser) void onGuesserClick(){
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
}
