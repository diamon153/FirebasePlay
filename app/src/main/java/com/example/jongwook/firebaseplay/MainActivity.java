package com.example.jongwook.firebaseplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.text);
        final Firebase ref = new Firebase(Constants.FIREBASE_URL);
        ref.authWithPassword("jenny2@example.com", "correctpassword", new Firebase.AuthResultHandler() {
            public void onAuthenticated(AuthData authData) {
                textView.setText("success");
                Map<String, String> map = new HashMap<String, String>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")) {
                    map.put("displayName", authData.getProviderData().get("displayName").toString());

                }
                textView.setText(authData.getProviderData().toString());
                ref.child("users").child(authData.getUid()).setValue(map);
            }

            public void onAuthenticationError(FirebaseError error){
                // something went wrong
                textView.setText("failure3");
            }
        });
        // ref.child("hello").setValue("This is it!");
    }
}
