package br.com.lucolimac.barkscanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Latido;

public class LatidoAdapter extends RecyclerView.Adapter<LatidoAdapter.LatidoViewHolder> {
    private List<Latido> latidos;

    public LatidoAdapter(List<Latido> latidos) {
        this.latidos = latidos;
    }

    @NonNull
    @Override
    public LatidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_latido, parent, false);
        LatidoViewHolder l = new LatidoViewHolder(v);
        return l;
    }

    @Override
    public void onBindViewHolder(@NonNull LatidoViewHolder holder, int position) {
        holder.cardLatidoCachorro.setText(latidos.get(position).getCao().getNome());
        holder.cardLatidoSituacao.setText(latidos.get(position).getSituacao());
        holder.cardLatidoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return latidos.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class LatidoViewHolder extends RecyclerView.ViewHolder {
        private CardView cardLatido;
        private TextView cardLatidoCachorro;
        private TextView cardLatidoSituacao;
        private ImageButton cardLatidoPlay;

        public LatidoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardLatido = itemView.findViewById(R.id.cardview_latido);
            this.cardLatidoCachorro = itemView.findViewById(R.id.card_latido_cachorro);
            this.cardLatidoSituacao = itemView.findViewById(R.id.card_latido_situacao);
            this.cardLatidoPlay = itemView.findViewById(R.id.card_play_button);
        }
    }
}
