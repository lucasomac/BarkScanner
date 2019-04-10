package br.com.lucolimac.barkscanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;

public class CachorroAdapter extends RecyclerView.Adapter<CachorroAdapter.LatidoViewHolder> {
    private List<Cachorro> cachorros;

    public CachorroAdapter(List<Cachorro> cachorros) {
        this.cachorros = cachorros;
    }

    @NonNull
    @Override
    public LatidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardview_cachorro, parent, false);
        LatidoViewHolder l = new LatidoViewHolder(v);
        return l;
    }

    @Override
    public void onBindViewHolder(@NonNull LatidoViewHolder holder, int position) {
        holder.cardCachorroNome.setText(cachorros.get(position).getNome());
        holder.cardCachorroRaca.setText(cachorros.get(position).getRaca());
        Format formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.cardCachorroNascimento.setText(formatter.format(cachorros.get(position).getNascimento()));
        holder.cardCachorroIcone.setImageResource(R.drawable.image_card_cachorro);
    }

    @Override
    public int getItemCount() {
        return cachorros.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class LatidoViewHolder extends RecyclerView.ViewHolder {
        private CardView cardCachorro;
        private TextView cardCachorroNome;
        private TextView cardCachorroRaca;
        private TextView cardCachorroNascimento;
        private ImageView cardCachorroIcone;


        public LatidoViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cardCachorro = itemView.findViewById(R.id.cardview_cachorro);
            this.cardCachorroNome = itemView.findViewById(R.id.card_cahorro_nome);
            this.cardCachorroRaca = itemView.findViewById(R.id.card_cachorro_raca);
            this.cardCachorroNascimento = itemView.findViewById(R.id.card_cachorro_nascimento);
            this.cardCachorroIcone = itemView.findViewById(R.id.card_imageview);

        }
    }
}
