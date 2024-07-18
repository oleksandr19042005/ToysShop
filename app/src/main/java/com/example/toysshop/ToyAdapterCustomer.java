package com.example.toysshop;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ToyAdapterCustomer extends RecyclerView.Adapter<ToyAdapterCustomer.ToyViewHolder> {
    private List<Toy> toyList;
    private buyToysAddToBasket buyToysAddToBasket;

    public ToyAdapterCustomer(List<Toy> toyList, buyToysAddToBasket buyToysAddToBasket) {
        this.toyList = toyList;
        this.buyToysAddToBasket = buyToysAddToBasket;
    }

    @NonNull
    @Override
    public ToyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.toy_item_for_buy, parent, false);
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
        private Button btnBuy;
        private EditText inputQuantity;

        public ToyViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            material = itemView.findViewById(R.id.material);
            ageForUse = itemView.findViewById(R.id.ageForUse);
            size = itemView.findViewById(R.id.size);
            price = itemView.findViewById(R.id.price);
            btnBuy = itemView.findViewById(R.id.addToBasket);
            inputQuantity = itemView.findViewById(R.id.inputQuantity);

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && buyToysAddToBasket != null) {
                        String quantityText = inputQuantity.getText().toString();
                        if (!quantityText.isEmpty()) {
                            try {
                                int quantity = Integer.parseInt(quantityText);
                                buyToysAddToBasket.buyToys(toyList.get(position), quantity);
                            } catch (NumberFormatException e) {
                                Toast.makeText(view.getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(view.getContext(), "Enter quantity", Toast.LENGTH_SHORT).show();
                        }
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

    public interface buyToysAddToBasket {
        void buyToys(Toy toy, int quantity);
    }
}


