package suriyon.cs.ubru.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import suriyon.cs.ubru.phonebook.adapter.PhoneBookAdapter;
import suriyon.cs.ubru.phonebook.dao.SQLiteHelper;
import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabAdd;
    private TextView tvNoData;
    private ImageView imgvNoData;
    private SQLiteHelper db;
    private List<PhoneBook> phoneBooks;
    private RecyclerView rcvPhoneBook;
    private PhoneBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteHelper(MainActivity.this);

        matchView();
        displayNoData();

        phoneBooks = db.select();
        adapter = new PhoneBookAdapter(MainActivity.this, phoneBooks);
        rcvPhoneBook.setAdapter(adapter);
        rcvPhoneBook.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.phone_book_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.menu_delete_all : confirmedDelete(); break;
            case R.id.menu_search : Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                                    startActivity(intent);
                                    break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmedDelete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Confirmed Message");
        alert.setMessage("Do you want to delete all friends?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.delete();
                Toast.makeText(MainActivity.this,
                        "Delete all friends!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.create().show();
    }

    private void displayNoData() {
        phoneBooks = db.select();
//        Log.d("Name", phoneBooks.get(0).getName());
        if(phoneBooks.size()>0) {
            imgvNoData.setVisibility(View.GONE);
            tvNoData.setVisibility(View.GONE);
        }else{
            imgvNoData.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void matchView() {
        fabAdd = findViewById(R.id.fab_add);
        imgvNoData = findViewById(R.id.imgv_no_data);
        tvNoData = findViewById(R.id.tv_no_data);
        rcvPhoneBook = findViewById(R.id.rcv_phonebook);
    }
}