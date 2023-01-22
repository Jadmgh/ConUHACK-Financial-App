package com.example.wally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CutDownPage extends AppCompatActivity {


    public ListView listBillsView, listCategoryView;
    public ArrayList<Category> listCategory;
    public ArrayList<Bill> listBills;
    public ArrayList<String> listBillsString, listCategoryString;
    public EditText editCategory,editPrice, editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cut_down_page);

        listCategoryView = (ListView) findViewById(R.id.listCategory);
        listBillsView = (ListView)  findViewById(R.id.listBills);

        listCategory = new ArrayList<Category>();
        listBills = new ArrayList<Bill>();
        listCategoryString = new ArrayList<String>();
        listBillsString = new ArrayList<String>();
        getInfoFromFireBase();

        listBillsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object p = listBillsView.getItemAtPosition(position);
                String category = (String) p;
                String split[]= category.split(" - ");
                String Category = split[0].trim();
                String[] nameList = split[1].split(" ");
                String name = nameList[0].trim();
                openBillDialog(Category,name);
            }
        });
        listCategoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = listCategoryView.getItemAtPosition(position);
                String allFile = (String) o;
                String[] split = ((String) o).split("-");
                String category = split[0].trim();
                String Price = split[1].trim();
                openComplaintDialog(category,Price);

            }
        });
    }

    private void getInfoFromFireBase() {
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categorySnapshot : snapshot.child("categories").getChildren()) {
                    listCategory.add(new Category(categorySnapshot.child("name").getValue().toString(),categorySnapshot.child("price").getValue().toString()));

                }
                for (DataSnapshot BillSnapshot : snapshot.child("bills").getChildren()) {
                    listBills.add(new Bill(BillSnapshot.child("name").getValue().toString(), BillSnapshot.child("amount").getValue().toString()));
                }
                populateListView();
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void populateListView() {
        for (int i = 0; i < listCategory.size(); i++) {
            listCategoryString.add(listCategory.get(i).name+ " - "+listCategory.get(i).price);
            listBillsString.add(listBills.get(i).name + " - "+listBills.get(i).amount +" of every month");
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CutDownPage.this, android.R.layout.simple_list_item_1,listCategoryString);
        listCategoryView.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(CutDownPage.this, android.R.layout.simple_list_item_1,listBillsString);
        listBillsView.setAdapter(arrayAdapter2);
    }

    public void openComplaintDialog(String nameOfCategory, String Price){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_category_popup,null);

        editCategory = (EditText) categoryPopupView.findViewById(R.id.editName);
        editPrice = (EditText) categoryPopupView.findViewById(R.id.editPrice);

        dialogBuilder.setView(categoryPopupView);
        Dialog dialog2 = dialogBuilder.create();
        dialog2.show();


        editCategory.setText(nameOfCategory);
        editPrice.setText(Price);
//        editPrice.setText();

        Button btnSaveCategory = (Button) categoryPopupView.findViewById(R.id.btnSaveCategory);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editCategory.getText().toString().isEmpty()) {
                    addCategory(editCategory.getText().toString(), editPrice.getText().toString());
                    dialog2.dismiss();
                }
            }
        });
    }

    public void openBillDialog(String nameOfCategory, String amount){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_add_bill,null);

        editCategory = (EditText) categoryPopupView.findViewById(R.id.editCategory);
        editName = (EditText) categoryPopupView.findViewById(R.id.editName);

        dialogBuilder.setView(categoryPopupView);
        Dialog dialog2 = dialogBuilder.create();
        dialog2.show();


        editCategory.setText(nameOfCategory);
        editName.setText(amount);

        Button btnSaveCategory = (Button) categoryPopupView.findViewById(R.id.btnSaveCategory);

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editCategory.getText().toString().isEmpty() && !editName.getText().toString().isEmpty()) {
                    addCategory(editCategory.getText().toString(), editName.getText().toString());
                    dialog2.dismiss();
                }
            }
        });
    }

    public void addCategory(String category, String price){
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("categories").child(category).setValue(new Category(category,price)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CutDownPage.this, "Item was successfully added", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("totalSpent").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Float previousTotal = Float.parseFloat(snapshot.getValue().toString());
                        Float newTotal = previousTotal + Float.parseFloat(price);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("totalSpent").setValue(newTotal.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }



}