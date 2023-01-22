package com.example.wally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnRegister;
    private EditText editFirstName, editLastName, editInputEmail, editMakePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeClient();
                return;
            }

        });

        mAuth = FirebaseAuth.getInstance();
        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editInputEmail = (EditText) findViewById(R.id.editEmail);
        editMakePassword = (EditText) findViewById(R.id.editMakePassword);

    }

    private void makeClient() {


        String email = editInputEmail.getText().toString().trim();
        String password = editMakePassword.getText().toString().trim();
        String firstName = editFirstName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();

        if (email.isEmpty()) {
            editInputEmail.setError("Email is required");
            editInputEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editMakePassword.setError("Password is required");
            editMakePassword.requestFocus();
            return;
        }

        if (firstName.isEmpty()) {
            editFirstName.setError("First Name is required");
            editFirstName.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            editLastName.setError("Last Name is required");
            editLastName.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editMakePassword.setError("Minimum password length should be 6 characters!");
            editMakePassword.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(email, password, FirebaseAuth.getInstance().getCurrentUser().getUid(),firstName, lastName);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "You have successfully been registered", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(Register.this, BudgetInfo.class);
                                        i.putExtra("userID",FirebaseAuth.getInstance().getCurrentUser().getUid() );
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Register.this, "Failed to register. Try again!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(Register.this, "Failed to register", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

}