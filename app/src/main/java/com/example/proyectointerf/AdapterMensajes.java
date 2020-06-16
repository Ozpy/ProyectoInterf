package com.example.proyectointerf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje>{

    List<Mensaje> listMnesaje = new ArrayList<>();
    Context c;

    public AdapterMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(Mensaje m){
        listMnesaje.add(m);
        notifyItemInserted(listMnesaje.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent, false);
        return new HolderMensaje(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMensaje holder, int position) {
        holder.getNombre().setText(listMnesaje.get(position).getNombre());
        holder.getMensaje().setText(listMnesaje.get(position).getMensaje());
        holder.getHora().setText(listMnesaje.get(position).getHora());

    }

    @Override
    public int getItemCount() {
        return listMnesaje.size();
    }
}
