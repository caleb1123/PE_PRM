package com.example.pe_prm_hta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewItems;
    private RecyclerView recyclerViewTypes;
    private Button btnAddItem;
    private Button btnAddType;
    private ItemDAO itemDAO;
    private TypeDAO typeDAO;
    private ItemAdapter itemAdapter;
    private TypeAdapter typeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewItems = findViewById(R.id.recyclerViewStudents);
        recyclerViewTypes = findViewById(R.id.recyclerViewMajors);
        btnAddItem = findViewById(R.id.btnAddStudent);
        btnAddType = findViewById(R.id.btnAddMajor);
        itemDAO = new ItemDAO(this);
        typeDAO = new TypeDAO(this);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        btnAddType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTypeActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTypes.setLayoutManager(new LinearLayoutManager(this));

        loadItems();
        loadTypes();
    }

    private void loadItems() {
        ArrayList<Item> items = itemDAO.getAllItems();
        itemAdapter = new ItemAdapter(this, items, itemDAO);
        recyclerViewItems.setAdapter(itemAdapter);
    }

    private void loadTypes() {
        ArrayList<Type> types = typeDAO.getAllTypes();
        typeAdapter = new TypeAdapter(this, types, typeDAO);
        recyclerViewTypes.setAdapter(typeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems();
        loadTypes();
    }
}
