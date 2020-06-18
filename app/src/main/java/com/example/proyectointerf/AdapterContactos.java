package com.example.proyectointerf;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterContactos extends RecyclerView.Adapter<AdapterContactos.ViewHolderContactos>
        implements  View.OnClickListener{

    Button btnEstado;
    View vista;
    ArrayList<ContactoVo> listaContactos;
    private View.OnClickListener listener;

    public AdapterContactos(ArrayList<ContactoVo> listaContactos) {
        this.listaContactos = listaContactos;
    }

    @NonNull
    @Override
    public ViewHolderContactos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_contactos,null,false);
        view.setOnClickListener(this);
        btnEstado= view.findViewById(R.id.btEstado);
        return new ViewHolderContactos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContactos holder, int position) {
        holder.etiNombre.setText(listaContactos.get(position).getNombre());
        holder.etiEmail.setText(listaContactos.get(position).getEmail());
        holder.foto.setImageResource(listaContactos.get(position).getFoto());

        if(listaContactos.get(position).getEstado().equals("ok")){
            holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            Log.e("COLOR:","GREEN");
            holder.estado.setHighlightColor(Color.GREEN);
        } else if(listaContactos.get(position).getEstado().equals("pendiente")){
            holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            Log.e("COLOR:","RED");
        }else{
            holder.estado.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            holder.estado.setHighlightColor(Color.BLACK);
            Log.e("COLOR:","BLACK");
        }

    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }

    }

    public class ViewHolderContactos extends RecyclerView.ViewHolder {
        TextView etiNombre,etiEmail;
        ImageView foto;
        Button estado;
        public ViewHolderContactos(@NonNull View itemView) {
            super(itemView);
            etiNombre=(TextView)itemView.findViewById(R.id.tvNombre);
            etiEmail=(TextView)itemView.findViewById(R.id.tvEmail);
            foto=(ImageView)itemView.findViewById(R.id.foto);
            estado=(Button)itemView.findViewById(R.id.btEstado);
        }
    }
}
