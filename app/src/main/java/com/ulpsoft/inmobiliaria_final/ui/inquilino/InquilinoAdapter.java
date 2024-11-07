package com.ulpsoft.inmobiliaria_final.ui.inquilino;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;

import java.util.List;

public class InquilinoAdapter extends RecyclerView.Adapter<InquilinoAdapter.InquilinoViewHolder> {
    private List<Contrato> inquilinos;
    private InquilinoViewModel viewModel;
    private OnInquilinoClickListener clickListener;

    public InquilinoAdapter(List<Contrato> inquilinos, InquilinoViewModel viewModel, OnInquilinoClickListener clickListener) {
        this.inquilinos = inquilinos;
        this.viewModel = viewModel;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public InquilinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inquilino_item, parent, false);
        return new InquilinoViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return inquilinos.size();
    }

    public void updateInquilinos(List<Contrato> nuevosInquilinos) {
        inquilinos.clear();
        inquilinos.addAll(nuevosInquilinos);
        notifyDataSetChanged();
    }

    class InquilinoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre;
        private TextView tvEmail;
        private TextView tvDireccionInmueble;
        private ImageView ivInmueble;

        public InquilinoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvApellidoEnInquilinoItem);
            tvEmail = itemView.findViewById(R.id.tvEmailEnInquilinoItem);
            tvDireccionInmueble = itemView.findViewById(R.id.tvDireccionEnInquilinoItem);
           // ivInmueble = itemView.findViewById(R.id.ivInmuebleEnInquilinoItem);
        }

        public void bind(Contrato contrato) {
            tvNombre.setText(contrato.getInqui().getNombre() + " " + contrato.getInqui().getApellido());
            tvEmail.setText(contrato.getInqui().getEmail());
            tvDireccionInmueble.setText(contrato.getInmu().getDireccion());
            /*Glide.with(itemView.getContext())
                    .load(contrato.getInmu().getImgUrl())
                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(ivInmueble);*/
        }
    }
    @Override
    public void onBindViewHolder(@NonNull InquilinoViewHolder holder, int position) {
        Contrato contrato = inquilinos.get(position);
        Inquilino inquilino = contrato.getInqui();
        Inmueble inmueble = contrato.getInmu();


            holder.bind(contrato);
            holder.itemView.setOnClickListener(v -> {

                    clickListener.onInquilinoClick(contrato, inquilino, inmueble);

            });

    }

    public interface OnInquilinoClickListener {
        void onInquilinoClick(Contrato contrato, Inquilino inquilino, Inmueble inmueble);
    }
}
