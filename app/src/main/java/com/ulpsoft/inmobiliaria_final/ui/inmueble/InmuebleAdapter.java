package com.ulpsoft.inmobiliaria_final.ui.inmueble;

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
import com.ulpsoft.inmobiliaria_final.model.Inmueble;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.InmuebleViewHolder> {
    private List<Inmueble> inmuebles;
    private InmuebleViewModel viewModel;
    private OnInmuebleClickListener clickListener;

    public InmuebleAdapter(List<Inmueble> inmuebles, InmuebleViewModel viewModel, OnInmuebleClickListener clickListener) {
        this.inmuebles = inmuebles;
        this.viewModel = viewModel;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public InmuebleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inmueble_item2, parent, false);
        return new InmuebleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InmuebleViewHolder holder, int position) {
        Inmueble inmueble = inmuebles.get(position);
        holder.bind(inmueble);
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onInmuebleClick(inmueble);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inmuebles.size();
    }

    public void updateInmuebles(List<Inmueble> nuevosInmuebles) {
        inmuebles.clear();
        inmuebles.addAll(nuevosInmuebles);
        notifyDataSetChanged();
    }

    class InmuebleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDireccion;
        private TextView tvPrecio;
        private TextView tvUso;
        private ImageView ivInmueble;

        public InmuebleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvUso = itemView.findViewById(R.id.tvUso);
            ivInmueble = itemView.findViewById(R.id.ivInmueble);
        }

        public void bind(Inmueble inmueble) {
            tvDireccion.setText(inmueble.getDireccion());
            tvPrecio.setText("$" + inmueble.getPrecio().toString());
            tvUso.setText(inmueble.usoDescripcion());

            Glide.with(ivInmueble.getContext())
                    .load(inmueble.getImgUrl())
                    .placeholder(R.drawable.nova_gris)
                    .error(R.drawable.nova_gris)
                    .into(ivInmueble);
        }
    }

    public interface OnInmuebleClickListener {
        void onInmuebleClick(Inmueble inmueble);
    }
}
