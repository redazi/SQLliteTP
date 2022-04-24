package com.zidahi.example.sqlitetp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zidahi.example.sqlitetp.beans.Machine;

import java.util.List;

import com.zidahi.example.sqlitetp.R;


public class ListeMachinesSallesAdapter extends BaseAdapter{

    List<Machine> machinesSalles ;
    private LayoutInflater layoutInflater ;

    public ListeMachinesSallesAdapter(Context context, List<Machine> machinesSalles) {
        this.machinesSalles = machinesSalles;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return machinesSalles.size();
    }

    @Override
    public Object getItem(int position) {
        return machinesSalles.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null)
            view =  layoutInflater.inflate(R.layout.machinesalle_item, null) ;

        TextView marque = (TextView) view.findViewById(R.id.marque);
        TextView reference = (TextView) view.findViewById(R.id.reference);

        marque.setText(machinesSalles.get(position).getMarque());
        reference.setText(machinesSalles.get(position).getRefernce());

        return view;
    }
}
