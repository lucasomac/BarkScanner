package br.com.lucolimac.barkscanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Latido;

public class AdapterLatido extends RecyclerView.Adapter<LatidoHolder> {
    private List<Latido> latidos;

    public AdapterLatido(ArrayList latidos) {
        this.latidos = latidos;
    }

    @NonNull
    @Override
    public LatidoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LatidoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latido, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LatidoHolder holder, int position) {
        holder.latido_cachorro.setText(latidos.get(position).getCao().getNome());
        holder.latido_situacao.setText(latidos.get(position).getSituacao());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return latidos != null ? latidos.size() : 0;
    }
}
