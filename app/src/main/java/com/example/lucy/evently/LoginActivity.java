package com.example.lucy.evently;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import com.facebook.FacebookSdk;

import com.firebase.client.Firebase;

/**
 * Created by Cindy on 9/19/2015.
 */
public class LoginActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
    }


}
