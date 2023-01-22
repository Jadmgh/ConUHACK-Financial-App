//package com.example.wally;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class CutDownPage extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cut_down_page);
//    }
//
//    //
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
//
//        private void openDialog(String category,String price) {
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
//
//
//}
//}