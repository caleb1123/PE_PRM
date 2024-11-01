package com.example.pe_prm_hta;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;
    private ItemDAO itemDAO;

    public ItemAdapter(Context context, List<Item> itemList, ItemDAO itemDAO) {
        this.context = context;
        this.itemList = itemList;
        this.itemDAO = itemDAO;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewCreator.setText(item.getCreator());
        holder.textViewReleaseDate.setText(item.getRelease_date());
        holder.textViewType.setText(item.getType());
        holder.textViewIdentifier.setText(item.getIdentifier());
        holder.textViewIdType.setText(String.valueOf(item.getIdType()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateItemActivity.class);
            intent.putExtra("ITEM_ID", item.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            itemDAO.deleteItem(item.getId());
            itemList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, itemList.size());
            Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewCreator, textViewReleaseDate, textViewType, textViewIdentifier, textViewIdType;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCreator = itemView.findViewById(R.id.textViewDate);
            textViewReleaseDate = itemView.findViewById(R.id.textViewGender);
            textViewType = itemView.findViewById(R.id.textViewEmail);
            textViewIdentifier = itemView.findViewById(R.id.textViewAddress);
            textViewIdType = itemView.findViewById(R.id.textViewIdMajor);
        }
    }
}
