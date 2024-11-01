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

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.TypeViewHolder> {

    private List<Type> typeList;
    private Context context;
    private TypeDAO typeDAO;

    public TypeAdapter(Context context, List<Type> typeList, TypeDAO typeDAO) {
        this.context = context;
        this.typeList = typeList;
        this.typeDAO = typeDAO;
    }

    @NonNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_type, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeViewHolder holder, int position) {
        Type major = typeList.get(position);
        holder.textViewItemID.setText(String.valueOf(major.getId()));
        holder.textViewItemName.setText(major.getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateTypeActivity.class);
            intent.putExtra("TYPE_ID", major.getId());
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            typeDAO.deleteType(major.getId());
            typeList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, typeList.size());
            Toast.makeText(context, "Type deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    static class TypeViewHolder extends RecyclerView.ViewHolder {
        TextView textViewItemID, textViewItemName;

        TypeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItemID = itemView.findViewById(R.id.textViewMajorID);
            textViewItemName = itemView.findViewById(R.id.textViewMajorName);
        }
    }
}
