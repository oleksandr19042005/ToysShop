package com.example.toysshop;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToyAdapter extends RecyclerView.Adapter<ToyAdapter.ToyViewHolder> {
    private List<Toy> toyList;
    private OnItemDeleteListener onItemDeleteListener;

    public ToyAdapter(List<Toy> toyList, OnItemDeleteListener onItemDeleteListener) {
        this.toyList = toyList;
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @NonNull
    @Override
    public ToyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toy_item, parent, false);
        return new ToyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToyViewHolder holder, int position) {
        Toy toy = toyList.get(position);
        holder.bind(toy);
    }

    @Override
    public int getItemCount() {
        return toyList.size();
    }

    public class ToyViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView material;
        private TextView ageForUse;
        private TextView size;
        private TextView price;
        private Button btnDelete;


        public ToyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            material = itemView.findViewById(R.id.material);
            ageForUse = itemView.findViewById(R.id.ageForUse);
            size = itemView.findViewById(R.id.size);
            price = itemView.findViewById(R.id.price);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onItemDeleteListener != null) {
                        onItemDeleteListener.onItemDelete(toyList.get(position));
                    }
                }
            });
        }

        public void bind(Toy toy) {
            id.setText(toy.getToy_id());
            name.setText(toy.getName());
            material.setText(toy.getMaterial());
            ageForUse.setText(toy.getAgeForUse());
            size.setText(toy.getSize());
            price.setText(toy.getPrice());
        }
    }

    public interface OnItemDeleteListener {
        void onItemDelete(Toy toy);
    }
}