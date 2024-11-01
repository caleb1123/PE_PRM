package com.example.pe_prm_hta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTypeActivity extends AppCompatActivity {
    private EditText editTextTypeName;
    private Button btnSaveMajor;
    private TypeDAO typeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        editTextTypeName = findViewById(R.id.editTextMajorName);
        btnSaveMajor = findViewById(R.id.btnSaveMajor);
        typeDAO = new TypeDAO(this);

        btnSaveMajor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String majorName = editTextTypeName.getText().toString();

                    if (majorName.isEmpty()) {
                        throw new IllegalArgumentException("Please enter a type name");
                    }

                    Type type = new Type();
                    type.setName(majorName);

                    long result = typeDAO.addType(type);
                    if (result > 0) {
                        Toast.makeText(AddTypeActivity.this, "Type added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddTypeActivity.this, "Failed to add type", Toast.LENGTH_SHORT).show();
                    }
                } catch (IllegalArgumentException e) {
                    Toast.makeText(AddTypeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
