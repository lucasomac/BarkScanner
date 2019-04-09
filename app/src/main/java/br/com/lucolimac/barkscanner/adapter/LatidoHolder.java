package br.com.lucolimac.barkscanner.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import br.com.lucolimac.barkscanner.R;

public class LatidoHolder extends RecyclerView.ViewHolder {

    public TextView latido_cachorro;
    public TextView latido_situacao;
    public ImageButton play;

    public LatidoHolder(View itemView) {
        super(itemView);
        latido_cachorro = itemView.findViewById(R.id.item_latido_cachorro);
        latido_situacao = itemView.findViewById(R.id.item_latido_situacao);
        play = itemView.findViewById(R.id.image_play);
    }
}