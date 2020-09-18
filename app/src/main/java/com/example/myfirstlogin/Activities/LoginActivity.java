package com.example.myfirstlogin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.myfirstlogin.R;
import com.example.myfirstlogin.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private EditText EditTextEmail;
    private EditText EditTextPass;
    private Switch switchRemember;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        
        binUI();
        setCredentialsIsExist();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = EditTextEmail.getText().toString();
                String password = EditTextPass.getText().toString();

                if(login(email, password)) {
                    goToMain();
                    savedPreferences(email, password);
                }
            }
        });
    }

    private void binUI(){
        EditTextEmail = findViewById(R.id.editTextEmail);
        EditTextPass = findViewById(R.id.editTextPassword);
        switchRemember = findViewById(R.id.switchRemeber);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    private void setCredentialsIsExist() {
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            EditTextEmail.setText(email);
            EditTextPass.setText(password);
        }
    }

    private boolean login(String email, String password) {
        if(!isValidEmail(email)){
            Toast.makeText(this, "This email is not valid, try again", Toast.LENGTH_LONG).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(this, "This passwsword is not valid, try again", Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    private void savedPreferences(String email, String password) {
        if(switchRemember.isChecked()){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            /*
            Guarda de forma sincrona
            editor.commit();
             */
            //Guarda las cosas de forma asincrona
            editor.apply();
        }
    }

    private boolean isValidEmail(String email){
        //Utilidades para saber si el texto está vacio y el siguiente el email hace match
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password){
        return password.length()>=4;
    }

    private void goToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}