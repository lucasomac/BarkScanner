package br.com.lucolimac.barkscanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;
import br.com.lucolimac.barkscanner.model.Cachorro;

public class AdapterCao extends RecyclerView.Adapter<AdapterCao.CaoViewHolder> {

    private List<Cachorro> mDataset;
    private Context context;

    public AdapterCao(List<Cachorro> mDataset, Context context) {
        this.context = context;
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public CaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycler_cao, parent, false);
        CaoViewHolder holder = new CaoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CaoViewHolder viewholder, int position) {
        Cachorro cao = mDataset.get(position);

        viewholder.nome.setText(cao.getNome());
        viewholder.raca.setText(cao.getRaca());
        viewholder.nascimento.setText(cao.getNascimento().toString());
        viewholder.porte.setText(cao.getPorte());
        viewholder.veterinario.setText(cao.getVeterinario());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class CaoViewHolder extends RecyclerView.ViewHolder {

        final TextView nome;
        final TextView raca;
        final TextView nascimento;
        final TextView porte;
        final TextView veterinario;

        public CaoViewHolder(View view) {
            super(view);

            nome = view.findViewById(R.id.item_cachorro_nome);
            raca = view.findViewById(R.id.item_cachorro_raca);
            nascimento = view.findViewById(R.id.item_cachorro_nascimento);
            porte = view.findViewById(R.id.item_cachorro_porte);
            veterinario = view.findViewById(R.id.item_cachorro_veterinario);
        }
    }
}
