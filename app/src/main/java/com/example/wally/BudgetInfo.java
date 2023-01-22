package com.example.wally;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class BudgetInfo extends AppCompatActivity {

    public EditText editIncome, editElectricityBill, editElectricityBillDue, editPhoneBill, editPhoneBillDue, editInternetBill, editInternetBillDue, editRent, editRentDue;
    private Button btnFinish;
    public String electricityBillDue, electricityBillString, phoneBillString, phoneBillDue, internetBillString, internetBillDue, rentString, rentDue, editIncomeString;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_info);

        editIncome = (EditText) findViewById(R.id.editIncome);

        editElectricityBill = (EditText) findViewById(R.id.editElectricityBill);
        editElectricityBillDue = (EditText) findViewById(R.id.editElectricityBillDue);
        editPhoneBill = (EditText) findViewById(R.id.editPhoneBill);
        editPhoneBillDue = (EditText) findViewById(R.id.editPhoneBillDue);
        editInternetBill = (EditText) findViewById(R.id.editInternetBill);
        editInternetBillDue = (EditText) findViewById(R.id.editInternetBillDue);
        editRent = (EditText) findViewById(R.id.editRent);
        editRentDue = (EditText) findViewById(R.id.editRentDue);

        btnFinish = (Button) findViewById(R.id.btnFinish);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editIncomeString = editIncome.getText().toString();
                electricityBillDue = editElectricityBillDue.getText().toString();
                electricityBillString = editElectricityBill.getText().toString();
                phoneBillString = editPhoneBill.getText().toString();
                phoneBillDue = editPhoneBillDue.getText().toString();
                internetBillString = editInternetBill.getText().toString();
                internetBillDue = editInternetBillDue.getText().toString();
                rentString = editRent.getText().toString();
                rentDue = editRentDue.getText().toString();

                Category electricityCategory = new Category("Electricity",electricityBillString);
                Category phoneCategory = new Category("Phone Bill", phoneBillString);
                Category internetCategory = new Category("Internet Bill", internetBillString);
                Category rentCategory = new Category("Rent",rentString);

                Bill electricityBill = new Bill("Electricity", electricityBillDue);
                Bill phoneBill = new Bill("Phone Bill", phoneBillDue);
                Bill internetBill = new Bill("Internet Bill", internetBillDue);
                Bill rentBill = new Bill("Rent", rentDue);

                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");

                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("categories").child("Electricity").setValue(electricityCategory);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("categories").child("Phone Bill").setValue(phoneCategory);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("categories").child("Internet Bill").setValue(internetCategory);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("categories").child("Rent").setValue(rentCategory);

                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("income").setValue(editIncomeString);

                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("bills").child("Electricity").setValue(electricityBill);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("bills").child("Phone").setValue(phoneBill);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("bills").child("Internet").setValue(internetBill);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("bills").child("Rent").setValue(rentBill);

                Float total = Float.parseFloat(electricityBillString) + Float.parseFloat(internetBillString)+ Float.parseFloat(phoneBillString)+ Float.parseFloat(rentString);
                FirebaseDatabase.getInstance().getReference("Users").child(userID).child("totalSpent").setValue(total);

                startActivity(new Intent(BudgetInfo.this,MainActivity.class));

                
            }
        });

    }

}