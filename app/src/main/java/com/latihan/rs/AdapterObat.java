package com.latihan.rs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterObat extends RecyclerView.Adapter<AdapterObat.HolderObat> {

    Context context;
    List<ModelObat> modelObatList;

    public AdapterObat(Context context, List<ModelObat> modelObatList) {
        this.context = context;
        this.modelObatList = modelObatList;
    }

    @NonNull
    @Override
    public AdapterObat.HolderObat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_obat, parent, false);
        return new HolderObat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterObat.HolderObat holder, int position) {
        holder.tvNamaObat.setText((CharSequence) modelObatList.get(position).getName());
        holder.tvPriceObat.setText((CharSequence) modelObatList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return modelObatList.size();
    }

    public class HolderObat extends RecyclerView.ViewHolder {
        TextView tvNamaObat, tvPriceObat;
        public HolderObat(@NonNull View itemView) {
            super(itemView);

            tvNamaObat = itemView.findViewById(R.id.tv_nama_obat);
            tvPriceObat = itemView.findViewById(R.id.tv_price_obat);
        }
    }
}
