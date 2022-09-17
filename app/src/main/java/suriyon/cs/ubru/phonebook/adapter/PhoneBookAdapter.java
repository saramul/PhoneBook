package suriyon.cs.ubru.phonebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import suriyon.cs.ubru.phonebook.R;
import suriyon.cs.ubru.phonebook.UpdateDeleteActivity;
import suriyon.cs.ubru.phonebook.model.PhoneBook;

public class PhoneBookAdapter extends RecyclerView.Adapter<PhoneBookAdapter.PhoneBookViewHolder> {
    private Context context;
    private List<PhoneBook> phoneBooks;

    public PhoneBookAdapter(Context context, List<PhoneBook> phoneBooks) {
        this.context = context;
        this.phoneBooks = phoneBooks;
    }

    @NonNull
    @Override
    public PhoneBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_phone_book, parent, false);
        return new PhoneBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneBookViewHolder holder, int position) {
        holder.tvName.setText(phoneBooks.get(position).getName());
        holder.tvPhone.setText(phoneBooks.get(position).getPhone());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateDeleteActivity.class);
                intent.putExtra("id", phoneBooks.get(position).getId());
                intent.putExtra("name", phoneBooks.get(position).getName());
                intent.putExtra("phone", phoneBooks.get(position).getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneBooks.size();
    }

    public class PhoneBookViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPhone;
        ConstraintLayout layout;
        public PhoneBookViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            layout = itemView.findViewById(R.id.layout_phone_book);
        }
    }
}
