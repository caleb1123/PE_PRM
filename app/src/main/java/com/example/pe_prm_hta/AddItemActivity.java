package com.example.pe_prm_hta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {
    private EditText editTextName,
            editTextCreator,
            editTextReleaseDate,
            editTextType,
            editTextIdentifier,
            editTextIdType;
    private Button btnSave;
    private ItemDAO itemDAO;
    private TypeDAO typeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editTextName = findViewById(R.id.editTextName);
        editTextCreator = findViewById(R.id.editTextDate);
        editTextReleaseDate = findViewById(R.id.editTextGender);
        editTextType = findViewById(R.id.editTextEmail);
        editTextIdentifier = findViewById(R.id.editTextAddress);
        editTextIdType = findViewById(R.id.editTextIdMajor);
        btnSave = findViewById(R.id.btnSave);
        itemDAO = new ItemDAO(this);
        typeDAO = new TypeDAO(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = editTextName.getText().toString();
                    String creator = editTextCreator.getText().toString();
                    String release_date = editTextReleaseDate.getText().toString();
                    String type = editTextType.getText().toString();
                    String identifier = editTextIdentifier.getText().toString();
                    String idTypeText = editTextIdType.getText().toString();

                    if (idTypeText.isEmpty()) {
                        throw new IllegalArgumentException("Type ID cannot be empty");
                    }

                    int idMajor = Integer.parseInt(idTypeText);

                    if (!typeDAO.isTypeExists(idMajor)) {
                        throw new IllegalArgumentException("Type does not exist");
                    }

                    Item item = new Item();
                    item.setName(name);
                    item.setCreator(creator);
                    item.setRelease_date(release_date);
                    item.setType(type);
                    item.setIdentifier(identifier);
                    item.setIdType(idMajor);

                    long result = itemDAO.addItem(item);
                    if (result > 0) {
                        Toast.makeText(AddItemActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddItemActivity.this, "Add item failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(AddItemActivity.this, "Type ID must be a number", Toast.LENGTH_SHORT).show();
                } catch (IllegalArgumentException e) {
                    Toast.makeText(AddItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
