package suriyon.cs.ubru.phonebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import suriyon.cs.ubru.phonebook.dao.SQLiteHelper;
import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class UpdateDeleteActivity extends AppCompatActivity {
    private TextInputEditText edtName, edtPhone;
    private MaterialButton btnUpdate, btnDelete;
    private SQLiteHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        db = new SQLiteHelper(UpdateDeleteActivity.this);

        matchView();
        loadDataToView();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("id", 0);
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();

                PhoneBook phoneBook = new PhoneBook(id, name, phone);
                boolean result = db.update(phoneBook);

                if(result){
                    Toast.makeText(UpdateDeleteActivity.this,
                            "Update Phone Book successfully!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateDeleteActivity.this,
                            "Unable to update Phone Book!",
                            Toast.LENGTH_SHORT).show();
                }
                goToParent();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("id", 0);
                AlertDialog.Builder alert = new AlertDialog.Builder(UpdateDeleteActivity.this);
                alert.setTitle("Confirmed Message");
                alert.setMessage("Do you want to delete your friend?");

                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean result = db.delete(id);
                        if(result){
                            Toast.makeText(UpdateDeleteActivity.this,
                                    "Delete your friend successfully!",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(UpdateDeleteActivity.this,
                                    "Unable to delete your friend!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        goToParent();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alert.create().show();
            }
        });
    }

    private void goToParent() {
        Intent intent = new Intent(UpdateDeleteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void loadDataToView() {
        Intent intent = getIntent();
        edtName.setText(intent.getStringExtra("name"));
        edtPhone.setText(intent.getStringExtra("phone"));
    }

    private void matchView() {
        edtName = findViewById(R.id.edt_name);
        edtPhone = findViewById(R.id.edt_phone);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);
    }
}