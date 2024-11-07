package com.ulpsoft.inmobiliaria_final.ui.pago;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ulpsoft.inmobiliaria_final.R;
import com.ulpsoft.inmobiliaria_final.model.Pago;

import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.PagoViewHolder> {
    private List<Pago> pagoList;

    public PagoAdapter(List<Pago> pagoList) {
        this.pagoList = pagoList;
    }

    @NonNull
    @Override
    public PagoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pago, parent, false);
        return new PagoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagoViewHolder holder, int position) {
        Pago pago = pagoList.get(position);
        holder.tvCodigo.setText(Html.fromHtml("<strong>Código:</strong> " + pago.getId()));
        holder.tvDetalle.setText( Html.fromHtml("<strong>Detalle:</strong> " + pago.getDetalle()));
        holder.tvMonto.setText(Html.fromHtml("<strong>Monto:</strong> " + pago.getMonto()));
        holder.tvConcepto.setText(Html.fromHtml("<strong>Concepto:</strong> " + pago.getConcepto().getNombre()));
        holder.tvFecha.setText(Html.fromHtml("<strong>Fecha: </strong>" + pago.getFecha().toString().substring(0, 10)));
        holder.tvPago.setText(Html.fromHtml("<strong>Pago: </strong>" + pago.getNro()));
        Log.d("pago" , pago.toString());
    }

    @Override
    public int getItemCount() {
        return pagoList.size();
    }

    public void setPagos(List<Pago> pagos) {
        this.pagoList = pagos;
        notifyDataSetChanged();
    }

    public static class PagoViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvDetalle, tvMonto, tvConcepto, tvFecha, tvPago;

        public PagoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigoPago);
            tvDetalle = itemView.findViewById(R.id.tvDetallePago);
            tvMonto = itemView.findViewById(R.id.tvMontoPago);
            tvConcepto = itemView.findViewById(R.id.tvConceptoPago);
            tvFecha = itemView.findViewById(R.id.tvFechaPago);
            tvPago = itemView.findViewById(R.id.tvNumeroPago);
        }
    }
}
