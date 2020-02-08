package com.keatssalazar.groupchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.chrono.MinguoChronology;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView user;
    private EditText pas;
    private FirebaseAuth mauth;
    private Button btnsign;
    private Button btnreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=findViewById(R.id.login_email);
        pas=findViewById(R.id.login_password);
        btnsign=findViewById(R.id.login_sign_in_button);
        mauth=FirebaseAuth.getInstance();



        btnreg=findViewById(R.id.login_register_button);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        FirebaseUser currentUser=mauth.getCurrentUser();
        if(currentUser!=null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }



        btnsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = user.getText().toString();
                String password = pas.getText().toString();
                if( email.equals("") || password.equals("")){
                    return;
                }else{
                    Toast.makeText(getApplicationContext(),"Login in progress...",Toast.LENGTH_SHORT).show();
                }
                // TODO: Use FirebaseAuth to sign in with email & password
                mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                        MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                              //  Log.d("FlashChat","signInWithEmail() onComplete: "+ task.isSuccessful());
                                if(!task.isSuccessful()){
                                    //  Log.d("FlashChat","Problem signing in: "+ task.getException());
                                    showErrorDialog("Problem signing in");
                                }else{
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                                    startActivity(intent);
                                    finish();
                                }
                            }

                        }
                );
            }
        });


    }
    public void showErrorDialog(String s){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(s)
                .setPositiveButton(android.R.string.ok,null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }





}
