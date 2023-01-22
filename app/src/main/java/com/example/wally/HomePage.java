package com.example.wally;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    public TextView txtWelcome;
    public User user;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText editCategory, editPrice;
    public Button btnSaveCategory, btnAddCategory;
    public ListView listPriceView, listCategoryView;
    public ArrayList<String> listPrice, listCategory;
    public ArrayList<Category> categories;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        categories = new ArrayList<>();
        listPrice = new ArrayList<>();
        listCategory = new ArrayList<>();

        Intent intent = getIntent();
        String [] userValues = intent.getStringArrayExtra("userValues");
        user = new User(userValues[0],userValues[1], userValues[2],userValues[3],userValues[4]);

        btnAddCategory = (Button) findViewById(R.id.btnAdd);
//        startActivity(new Intent(HomePage.this,Pie_chart.class));


        pieChart = findViewById(R.id.activity_pie_chart);
        setupPieChart();
        loadPieChartData();
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

//    private void openDialog(String category,String price) {
//        dialogBuilder = new AlertDialog.Builder(this);
//        final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_category_popup,null);
//
//        editCategory = (EditText) categoryPopupView.findViewById(R.id.editCategory);
//        editPrice = (EditText) categoryPopupView.findViewById(R.id.editPrice);
//
//        btnSaveCategory = (Button) categoryPopupView.findViewById(R.id.btnSaveCategory);
//
//        dialogBuilder.setView(categoryPopupView);
//        dialog = dialogBuilder.create();
//        dialog.show();
//
//        editCategory.setText(category);
//        editPrice.setText(price);
//        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!editCategory.getText().toString().isEmpty()) {
//                    addCategory(editCategory.getText().toString(), editPrice.getText().toString());
//                    dialog.dismiss();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btnAdd:
//                openNewDialog();
//        }
//    }
////
//    public void addCategory(String category, String price){
//        FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("categories").child(category).setValue(new Category(category,price)).addOnCompleteListener(new OnCompleteListener<Void>() {
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
//                updateListview();
//            }
//        });
//    }
//
//    public void updateListview(){
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.userID).child("categories");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot categorySnapshot : snapshot.getChildren()) {
//                    Category category = new Category(categorySnapshot.child("name").getValue().toString(), categorySnapshot.child("price").getValue().toString());
//                    categories.add(category);
//                }
//                populateListView();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }

//
//    public void openNewDialog(){
//
//        dialogBuilder = new AlertDialog.Builder(this);
//        final View categoryPopupView = getLayoutInflater().inflate(R.layout.activity_category_popup,null);
//
//        editCategory = (EditText) categoryPopupView.findViewById(R.id.editCategory);
//        editPrice = (EditText) categoryPopupView.findViewById(R.id.editPrice);
//
//        btnSaveCategory = (Button) categoryPopupView.findViewById(R.id.btnSaveCategory);
//
//        dialogBuilder.setView(categoryPopupView);
//        dialog = dialogBuilder.create();
//        dialog.show();
//
//        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!editCategory.getText().toString().isEmpty()) {
//                    addCategory(editCategory.getText().toString(), editPrice.getText().toString());
//                    dialog.dismiss();
//                }
//            }
//        });
//    }
////
//    private void populateListView() {
//        for (int i = 0; i < categories.size(); i++) {
//            listCategory.add(categories.get(i).name);
//            listPrice.add(categories.get(i).price);
//        }
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,listCategory);
//        listCategoryView.setAdapter(arrayAdapter);
//        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(HomePage.this, android.R.layout.simple_list_item_1,listPrice);
//        listPriceView.setAdapter(arrayAdapter2);
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
                    Toast.makeText(HomePage.this, "accessed", Toast.LENGTH_SHORT).show();
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
