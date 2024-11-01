package com.example.pe_prm_hta;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateTypeActivity extends AppCompatActivity {

    private EditText editTextTypeName;
    private Button btnSave, btnDelete;
    private TypeDAO typeDAO;
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_type);

        editTextTypeName = findViewById(R.id.editTextMajorName);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        typeDAO = new TypeDAO(this);

        typeId = getIntent().getIntExtra("TYPE_ID", -1);
        Type type = typeDAO.getTypeById(typeId);

        if (type != null) {
            editTextTypeName.setText(type.getName());
        }

        btnSave.setOnClickListener(v -> {
            try {
                Type updatedType = new Type(typeId, editTextTypeName.getText().toString());
                typeDAO.updateType(updatedType);
                Toast.makeText(UpdateTypeActivity.this, "Type updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(UpdateTypeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            try {
                typeDAO.deleteType(typeId);
                Toast.makeText(UpdateTypeActivity.this, "Type deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (IllegalArgumentException e) {
                Toast.makeText(UpdateTypeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
