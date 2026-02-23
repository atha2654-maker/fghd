package com.vandemunconnect.javaapp.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vandemunconnect.javaapp.databinding.ItemSimpleBinding;
import com.vandemunconnect.javaapp.model.SimpleItem;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ItemViewHolder> {

    private final List<SimpleItem> items = new ArrayList<>();

    public void submit(List<SimpleItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSimpleBinding binding = ItemSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SimpleItem item = items.get(position);
        holder.binding.title.setText(item.title);
        holder.binding.subtitle.setText(item.subtitle);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        final ItemSimpleBinding binding;

        ItemViewHolder(ItemSimpleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
