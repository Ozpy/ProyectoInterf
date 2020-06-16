package com.example.proyectointerf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.ViewHolderContactos> {
    ArrayList<ContactoVo> listaContactos;

    public AdapterContactos(ArrayList<ContactoVo> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ViewHolderContactos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contactos,null,false);
        return new ViewHolderContactos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContactos holder, int position) {
        holder.etiNombre.setText(listaContactos.get(position).getNombre());
        holder.etiEmail.setText(listaContactos.get(position).getEmail());
        holder.foto.setImageResource(listaContactos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public class ViewHolderContactos extends RecyclerView.ViewHolder {
        TextView etiNombre,etiEmail;
            ImageView foto;
        public ViewHolderContactos(@NonNull View itemView) {
            super(itemView);
            etiNombre=(TextView)itemView.findViewById(R.id.tvNombre);
            etiEmail=(TextView)itemView.findViewById(R.id.tvEmail);
            foto=(ImageView)itemView.findViewById(R.id.foto);
        }
    }
}
