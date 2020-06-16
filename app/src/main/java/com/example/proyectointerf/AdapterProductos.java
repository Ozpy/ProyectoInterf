package com.example.proyectointerf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolderProductos>{
    ArrayList<Producto> listaProductos;

    public AdapterProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ViewHolderProductos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productos,null,false);
        return new AdapterProductos.ViewHolderProductos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProductos.ViewHolderProductos holder, int position) {
        holder.etiNombre.setText(listaProductos.get(position).getNombre());
        holder.etiDescripcion.setText(listaProductos.get(position).getDescripcion());
        holder.foto.setImageResource(listaProductos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderProductos extends RecyclerView.ViewHolder {
        TextView etiNombre,etiDescripcion;
        ImageView foto;
        public ViewHolderProductos(@NonNull View itemView) {
            super(itemView);
            etiNombre=(TextView)itemView.findViewById(R.id.tvNombre2);
            etiDescripcion=(TextView)itemView.findViewById(R.id.tvDescripcion);
            foto=(ImageView)itemView.findViewById(R.id.foto2);
        }
    }
}
