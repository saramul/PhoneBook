package suriyon.cs.ubru.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import suriyon.cs.ubru.phonebook.dao.SQLiteHelper;
import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class AddActivity extends AppCompatActivity {
    private MaterialButton btnAdd;
    private TextInputEditText edtName, edtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        matchView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();

                if(name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(
                            AddActivity.this,
                            "Please full fill data before click add button",
                            Toast.LENGTH_SHORT
                    ).show();
                }else{
                    SQLiteHelper db = new SQLiteHelper(AddActivity.this);
                    PhoneBook phoneBook = new PhoneBook(name, phone);

                    boolean result = db.insert(phoneBook);
                    if(result) {
                        Toast.makeText(
                                AddActivity.this,
                                "Insert successfully!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else{
                        Toast.makeText(
                                AddActivity.this,
                                "Unable to insert!",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    clearText();
                }
            }
        });
    }

    private void clearText() {
        edtName.setText("");
        edtPhone.setText("");
        edtName.requestFocus();
    }

    private void matchView() {
        btnAdd = findViewById(R.id.btn_add);
        edtName = findViewById(R.id.tiedt_name);
        edtPhone = findViewById(R.id.tiedt_phone);
    }
}