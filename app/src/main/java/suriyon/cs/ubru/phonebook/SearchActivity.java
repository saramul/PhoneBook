package suriyon.cs.ubru.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import suriyon.cs.ubru.phonebook.adapter.PhoneBookAdapter;
import suriyon.cs.ubru.phonebook.dao.SQLiteHelper;
import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView edtSearch;
    private MaterialButton btnSearch;
    private RecyclerView rcvSearch;
    private SQLiteHelper db;
    private PhoneBookAdapter adapter;
    private List<PhoneBook> phoneBooks;
    private String[] list;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db = new SQLiteHelper(SearchActivity.this);

        loadNameToArray();

        matchView();

        arrayAdapter = new ArrayAdapter<String>(SearchActivity.this,
                android.R.layout.simple_dropdown_item_1line, list);
        edtSearch.setAdapter(arrayAdapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtSearch.getText().toString();
                phoneBooks = db.select(name);

                if(phoneBooks.size()>0) {
                    adapter = new PhoneBookAdapter(SearchActivity.this, phoneBooks);
                    rcvSearch.setAdapter(adapter);
                    rcvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                }else{
                    Toast.makeText(SearchActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
                edtSearch.setText("");
                hideSoftKeyboard(SearchActivity.this);
            }
        });
    }

    private void loadNameToArray() {
        phoneBooks = db.select();
        int size = phoneBooks.size();
        list = new String[size];
        for(int i=0; i<size; i++){
            list[i] = phoneBooks.get(i).getName();
        }
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void matchView() {
        edtSearch = findViewById(R.id.edt_search);
        btnSearch = findViewById(R.id.btn_search);
        rcvSearch = findViewById(R.id.rcv_search);
    }
}