//package com.example.wally;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.data.PieEntry;
//import com.github.mikephil.charting.formatter.PercentFormatter;
//import com.github.mikephil.charting.utils.ColorTemplate;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//
//public class Pie_chart extends AppCompatActivity {
//
////    private PieChart pieChart;
////
////    @Override
////    protected void onCreate(Bundle savedInstanceState){
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_home);
////
////        pieChart = findViewById(R.id.activity_pie_chart);
////        setupPieChart();
////        loadPieChartData();
////
////    }
////
////    private void setupPieChart(){
////        pieChart.setUsePercentValues(true);
////        pieChart.setEntryLabelTextSize(12);
////        pieChart.setEntryLabelColor(Color.BLACK);
////        pieChart.setCenterText("Spending Categories");
////        pieChart.setCenterTextSize(24);
////        pieChart.getDescription().setEnabled(false);
////
////        Legend l = pieChart.getLegend();
////        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
////        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
////        l.setOrientation(Legend.LegendOrientation.VERTICAL);
////        l.setDrawInside(false);
////        l.setEnabled(true);
////    }
////
////    private void loadPieChartData() {
////        ArrayList<PieEntry> entries = new ArrayList<>();
////
////        Log.d("afaf","aefoamfa");
////        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                for (DataSnapshot categorySnapshot : snapshot.child("categories").getChildren()) {
////                    Toast.makeText(Pie_chart.this, "accessed", Toast.LENGTH_SHORT).show();
////                    entries.add(new PieEntry(Float.parseFloat(categorySnapshot.child("amount").getValue().toString()), categorySnapshot.child("name").getValue()));
//////                     entries.add(new PieEntry(0.3f,"test"));
////                }
////                ArrayList<Integer> colors = new ArrayList<>();
////                for (int color : ColorTemplate.MATERIAL_COLORS){
////                    colors.add(color);
////                }
////
////                for (int color : ColorTemplate.VORDIPLOM_COLORS){
////                    colors.add(color);
////                }
////
////                PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
////                dataSet.setColors(colors);
////
////                PieData data = new PieData(dataSet);
////                data.setDrawValues(true);
////                data.setValueFormatter(new PercentFormatter(pieChart));
////                data.setValueTextSize(12f);
////                data.setValueTextColor(Color.BLACK);
////
////                pieChart.setData(data);
////                pieChart.invalidate();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
//
//
////        entries.add(new PieEntry(0.3f,"test"));
////        entries.add(new PieEntry(0.3f,"test"));
////        entries.add(new PieEntry(0.3f,"test"));
////
////        ArrayList<Integer> colors = new ArrayList<>();
////        for (int color : ColorTemplate.MATERIAL_COLORS){
////            colors.add(color);
////        }
////
////        for (int color : ColorTemplate.VORDIPLOM_COLORS){
////            colors.add(color);
////        }
////
////        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
////        dataSet.setColors(colors);
////
////        PieData data = new PieData(dataSet);
////        data.setDrawValues(true);
////        data.setValueFormatter(new PercentFormatter(pieChart));
////        data.setValueTextSize(12f);
////        data.setValueTextColor(Color.BLACK);
////
////        pieChart.setData(data);
////        pieChart.invalidate();
//
//    }
//
//}
