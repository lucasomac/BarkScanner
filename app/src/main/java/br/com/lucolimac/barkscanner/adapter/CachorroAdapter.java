package br.com.lucolimac.barkscanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;


public class CachorroAdapter extends RecyclerView.Adapter<CachorroAdapter.CachorroViewHolder> {

    private List<Cachorro> cachorros;

    public CachorroAdapter(List<Cachorro> cachorros) {
        this.cachorros = cachorros;
    }

    @NonNull
    @Override
    public CachorroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_cachorro, parent, false);
        return new CachorroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CachorroViewHolder holder, int position) {
        holder.nomeView.setText(cachorros.get(position).getNome());
        holder.racaView.setText(cachorros.get(position).getRaca());
        holder.idadeView.setText(cachorros.get(position).getIdade());
    }

    @Override
    public int getItemCount() {
        return cachorros.size();
    }

    public static class CachorroViewHolder extends RecyclerView.ViewHolder {
        TextView nomeView;
        TextView idadeView;
        TextView racaView;

        CachorroViewHolder(View itemView) {
            super(itemView);
            nomeView = itemView.findViewById(R.id.dog_name_card);
            racaView = itemView.findViewById(R.id.dog_raca_card);
            idadeView = itemView.findViewById(R.id.dog_age_card);
        }
    }
}
