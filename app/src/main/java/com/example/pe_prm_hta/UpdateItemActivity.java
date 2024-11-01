package com.example.pe_prm_hta;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateItemActivity extends AppCompatActivity {

    private EditText editTextName,
            editTextCreator,
            editTextReleaseDate,
            editTextType,
            editTextIdentifier,
            editTextIdType;
    private Button btnSave, btnDelete;
    private ItemDAO itemDAO;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        editTextName = findViewById(R.id.editTextName);
        editTextCreator = findViewById(R.id.editTextDate);
        editTextReleaseDate = findViewById(R.id.editTextGender);
        editTextType = findViewById(R.id.editTextEmail);
        editTextIdentifier = findViewById(R.id.editTextAddress);
        editTextIdType = findViewById(R.id.editTextIdMajor);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        itemDAO = new ItemDAO(this);

        itemId = getIntent().getIntExtra("ITEM_ID", -1);
        Item item = itemDAO.getItemById(itemId);

        if (item != null) {
            editTextName.setText(item.getName());
            editTextCreator.setText(item.getCreator());
            editTextReleaseDate.setText(item.getRelease_date());
            editTextType.setText(item.getType());
            editTextIdentifier.setText(item.getIdentifier());
            editTextIdType.setText(String.valueOf(item.getIdType()));
        }

        btnSave.setOnClickListener(v -> {
            try {
                String idMajorText = editTextIdType.getText().toString();
                if (idMajorText.isEmpty()) {
                    throw new IllegalArgumentException("Type ID cannot be empty");
                }

                int idMajor = Integer.parseInt(idMajorText);

                Item updatedItem = new Item(itemId,
                        editTextName.getText().toString(),
                        editTextCreator.getText().toString(),
                        editTextReleaseDate.getText().toString(),
                        editTextType.getText().toString(),
                        editTextIdentifier.getText().toString(),
                        idMajor);

                itemDAO.updateItem(updatedItem);
                Toast.makeText(UpdateItemActivity.this, "Item updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (NumberFormatException e) {
                Toast.makeText(UpdateItemActivity.this, "Type ID must be a number", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(UpdateItemActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            itemDAO.deleteItem(itemId);
            Toast.makeText(UpdateItemActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
