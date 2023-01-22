package com.example.wally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomePage extends AppCompatActivity {

    public TextView txtWelcome,editAddCategory,editAddBill;
    public User user;
    private AlertDialog.Builder dialogBuilder, dialogBuilder2, dialogBuilder3;
    private AlertDialog dialog,dialog2,dialog3;
    private EditText editCategory, editPrice, editName;
    public Button btnSaveCategory, btnAddCategory;
    public ListView listPriceView, listCategoryView, listBillsView, editAddCategorie;
    public ArrayList<String> listPrice, listCategory, listBills;
    public ArrayList<Category> categories;
    public ArrayList<Bill> arrayListBills;
    private PieChart pieChart;
    private ProgressBar progressBar;
    private ClipData.Item plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listBills = new ArrayList<>();
//        plus = (ClipData.Item) findViewById(R.id.add);

        categories = new ArrayList<>();
        listPrice = new ArrayList<>();
        listCategory = new ArrayList<>();

        arrayListBills = new ArrayList<>();

        Intent intent = getIntent();
        String [] userValues = intent.getStringArrayExtra("userValues");
        user = new User(userValues[0],userValues[1], userValues[2],userValues[3],userValues[4]);

        btnAddCategory = (Button) findViewById(R.id.btnAdd);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer spent = Integer.parseInt(snapshot.child("totalSpent").getValue().toString());
                Integer income = Integer.parseInt(snapshot.child("income").getValue().toString());
                Integer progress = spent*100/income;
                progressBar.setProgress(progress);
                Toast.makeText(HomePage.this, spent.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pieChart = findViewById(R.id.activity_pie_chart);
        setupPieChart();
        loadPieChartData();

        listBillsView = (ListView) findViewById(R.id.listBills);

        updateBillsListview();
//        listCategoryView = (ListView) findViewById(R.id.listCategory);
//        listPriceView = (ListView) findViewById(R.id.listPrice);

//        btnAddCategory.setOnClickListener(this);

//        updateListview();
//        txtWelcome = (TextView) findViewById(R.id.txtWelcome);
//        txtWelcome.setText("Welcome "+user.firstName);

//        listPriceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = listCategoryView.getItemAtPosition(position);
//                Object p = listPriceView.getItemAtPosition(position);
//                String category = (String) o;
//                String price = (String) p;
//                openDialog(category, price);
//
//            }
//        });
//        listCategoryView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object o = listCategoryView.getItemAtPosition(position);
//                Object p = listPriceView.getItemAtPosition(position);
//                String category = (String) o;
//                String price = (String) p;
//                openDialog(category, price);
//
//            }
//        });
    }

    private void populateListView() {
        for (int i = 0; i < categories.size(); i++) {
            listCategory.add(categories.get(i).name);
            listPrice.add(categories.get(i).price);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,listCategory);
        listCategoryView.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,listPrice);
        listPriceView.setAdapter(arrayAdapter2);
    }


        public void updateBillsListview(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("bills");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bill> temp = new ArrayList<Bill>();

                for (DataSnapshot billSnapshot : snapshot.getChildren()) {
                       Bill bills= new Bill(billSnapshot.child("name").getValue().toString(), billSnapshot.child("amount").getValue().toString());
                       arrayListBills.add(bills);
                       temp.add(bills);
                }

                ArrayList<Integer> indexes = new ArrayList<Integer>();


                ArrayList<Bill> temp2 = new ArrayList<Bill>();
                while (!temp.isEmpty()){
                    int minIndex =0;
                    for (int i = 0; i < temp.size(); i++) {
                        if (Integer.parseInt(temp.get(i).amount)<Integer.parseInt(temp.get(minIndex).amount)){
                            minIndex = i;
                        }
                    }
                    temp2.add(temp.get(minIndex));
                    temp.remove(minIndex);
                }

                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
//                Integer days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                for (int i = 0; i < temp2.size(); i++) {
                    if(Integer.parseInt(temp2.get(i).amount) >22) {
                        listBills.add(temp2.get(i).name + " due in "+ (Integer.parseInt(temp2.get(i).amount) -22)+" days");
                    }
                }

                if (listBills.isEmpty()){
                    listBills.add("No bills left to pay this month!");
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,listBills);
                listBillsView.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }



    public void addCategory(String category, String price){
        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("categories").child(category).setValue(new Category(category,price)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HomePage.this, "Item was successfully added", Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("totalSpent").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Float previousTotal = Float.parseFloat(snapshot.getValue().toString());
                        Float newTotal = previousTotal + Float.parseFloat(price);
                        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("totalSpent").setValue(newTotal.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                loadPieChartData();
            }
        });
    }


//
    public void openNewDialog(){

        dialogBuilder = new AlertDialog.Builder(this);
        final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_category_popup,null);

        editCategory = (EditText) categoryPopupView.findViewById(R.id.editName);
        editPrice = (EditText) categoryPopupView.findViewById(R.id.editPrice);

        dialogBuilder.setView(categoryPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editCategory.getText().toString().isEmpty()) {
                    addCategory(editCategory.getText().toString(), editPrice.getText().toString());
                    dialog.dismiss();
                }
            }
        });
    }

//    public void makeNewAnything(){
//        dialogBuilder = new AlertDialog.Builder(this);
//        final View addAnythingPopout = getLayoutInflater().inflate(R.layout.activity_add_something,null);
//
//        editAddBill = (TextView) addAnythingPopout.findViewById(R.id.newBill);
//        editAddCategory = (TextView) addAnythingPopout.findViewById(R.id.newMonthlyExpense);
//
//
//        dialogBuilder.setView(addAnythingPopout);
//        dialog = dialogBuilder.create();
//        dialog.show();
//
//        editAddCategorie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 dialogBuilder2 = new AlertDialog.Builder(HomePage.this);
//                final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_category_popup,null);
//
//                editPrice = (EditText) categoryPopupView.findViewById(R.id.editPrice);
//                editCategory = (EditText) categoryPopupView.findViewById(R.id.editName);
//
//                btnSaveCategory = (Button) categoryPopupView.findViewById(R.id.btnSaveCategory);
//                dialogBuilder2.setView(categoryPopupView);
//                dialog2 = dialogBuilder2.create();
//                dialog2.show();
//
//                btnSaveCategory.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!editCategory.getText().toString().isEmpty()) {
//                            addCategory(editCategory.getText().toString(), editPrice.getText().toString());
//                            dialog2.dismiss();
//                        }
//                    }
//                });
//
//            }
//        });
//
//        editAddBill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogBuilder3 = new AlertDialog.Builder(HomePage.this);
//                final View billPopupView = getLayoutInflater().inflate(R.layout.activity_add_bill,null);
//
//                editName = (EditText) billPopupView.findViewById(R.id.editName);
//                editPrice = (EditText) billPopupView.findViewById(R.id.editPrice);
//                editCategory = (EditText) billPopupView.findViewById(R.id.editCategory);
//
//                btnSaveCategory = (Button) billPopupView.findViewById(R.id.btnSaveCategory);
//
//                dialogBuilder3.setView(billPopupView);
//                dialog3 = dialogBuilder3.create();
//                dialog3.show();
//
//                btnSaveCategory.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (!editCategory.getText().toString().isEmpty()) {
//                            addBill(editCategory.getText().toString(), editPrice.getText().toString(), editName.getText().toString());
//                            dialog3.dismiss();
//                        }
//                    }
//                });
//
//            }
//
//        });
//    }
//
//    private void addBill(String category, String Price, String duration) {
//        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("bills").child(category).setValue(new Bill()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(HomePage.this, "Item was successfully added", Toast.LENGTH_SHORT).show();
//                FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("totalSpent").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Float previousTotal = Float.parseFloat(snapshot.getValue().toString());
//                        Float newTotal = previousTotal + Float.parseFloat(price);
//                        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("totalSpent").setValue(newTotal.toString());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                loadPieChartData();
//            }
//        });
//    }

    private void setupPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Spending Categories");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        Log.d("afaf","aefoamfa");
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot categorySnapshot : snapshot.child("categories").getChildren()) {
                    entries.add(new PieEntry(Float.parseFloat(categorySnapshot.child("price").getValue().toString()), categorySnapshot.child("name").getValue()));
//                     entries.add(new PieEntry(0.3f,"test"));
                }
                ArrayList<Integer> colors = new ArrayList<>();
                for (int color : ColorTemplate.MATERIAL_COLORS){
                    colors.add(color);
                }

                for (int color : ColorTemplate.VORDIPLOM_COLORS){
                    colors.add(color);
                }

                PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                data.setDrawValues(true);
                data.setValueFormatter(new PercentFormatter(pieChart));
                data.setValueTextSize(12f);
                data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                pieChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
