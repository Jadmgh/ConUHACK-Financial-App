package com.example.wally;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private Button btnPie;
    private FirebaseAuth mAuth;

    private EditText editEmail, editPassword;
    private TextView txtRegister;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth.getInstance().signOut();


        btnLogin = (Button) findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(this);

        btnPie = (Button) findViewById(R.id.btnTest);
        btnPie.setOnClickListener(this);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(this);

        btnLogin.setOnClickListener(this);
        btnPie.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtRegister:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btnSignIn:
                userLogin();
//                Intent i = new Intent(MainActivity.this, Pie_chart.class);
//                startActivity(i);
                break;

            case R.id.btnTest:
                Intent i = new Intent(MainActivity.this, Pie_chart.class);
                startActivity(i);
                break;
        }
    }

    private void userLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI();
                }
                else{
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    editPassword.getText().clear();
                }
            }

        });
    }

    private void updateUI() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Intent i = new Intent(MainActivity.this, HomePage.class);
                String[] userValues = {snapshot.child("email").getValue().toString(), snapshot.child("password").getValue().toString(), snapshot.child("userID").getValue().toString(), snapshot.child("firstName").getValue().toString(), snapshot.child("lastName").getValue().toString()};
                i.putExtra("userValues", userValues);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}