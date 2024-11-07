package com.ulpsoft.inmobiliaria_final.ui.contrato;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.model.Contrato;
import com.ulpsoft.inmobiliaria_final.model.Inmueble;
import com.ulpsoft.inmobiliaria_final.model.Inquilino;

import java.util.List;

public class ContratoAdapter extends RecyclerView.Adapter<ContratoAdapter.ContratoViewHolder> {
    private List<Contrato> contratos;
    private ContratoViewModel vm;
    private OnContratoClickListener clickListener;

    public ContratoAdapter(List<Contrato> contratos, ContratoViewModel vm, OnContratoClickListener clickListener) {
        this.contratos = contratos;
        this.vm = vm;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ContratoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contrato, parent, false);
        return new ContratoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContratoAdapter.ContratoViewHolder holder, int position) {
        Contrato contrato = contratos.get(position);
        Inquilino inquilino = contrato.getInqui();
        Inmueble inmueble = contrato.getInmu();
        holder.bind(contrato);
        holder.itemView.setOnClickListener(v -> {
            clickListener.onContratoClick(contrato);
        });


    }

    @Override
    public int getItemCount() {
        return contratos.size();
    }

    public void updateContratos(List<Contrato> nuevosContratos) {
        contratos.clear();
        contratos.addAll(nuevosContratos);
        notifyDataSetChanged();
    }

    class ContratoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDireccion;
        private TextView tvNombre;
        private TextView tvFechas;
        private ImageView ivInmueble;

        public ContratoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccionEnItemContrato);
            tvNombre = itemView.findViewById(R.id.tvApellidoEnItemContrato);
            tvFechas = itemView.findViewById(R.id.tvFechaInicio_fin);
            ivInmueble = itemView.findViewById(R.id.ivInmuebleEnItemContrato);
        }

        public void bind(Contrato contrato) {
            tvNombre.setText(contrato.getInqui().getNombre() + " " + contrato.getInqui().getApellido());
            tvFechas.setText(contrato.getFechaInicio().substring(0, 10) + " - " + contrato.getFechaFin().substring(0, 10));
            tvDireccion.setText(contrato.getInmu().getDireccion());
            Glide.with(ivInmueble.getContext())
                    .load(contrato.getInmu().getImgUrl())
                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(ivInmueble);
        }
    }

    public interface OnContratoClickListener {
        void onContratoClick(Contrato contrato);
    }
}