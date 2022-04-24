package com.zidahi.example.sqlitetp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.SalleService;

public class FragmentSalle extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText code;
    private EditText libelle;
    private Button add;
    private Button menu;
    private Button list;

    SalleService db = null;

    private String mParam1;
    private String mParam2;

    public FragmentSalle() {

    }


    public static FragmentMachine newInstance(String param1, String param2) {
        FragmentMachine fragment = new FragmentMachine();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_salle, container, false);
        code = (EditText) v.findViewById(R.id.code);
        libelle = (EditText) v.findViewById(R.id.libelle);
        add = (Button) v.findViewById(R.id.add);
        menu = (Button) v.findViewById(R.id.menu);
         add.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 db =  new SalleService(getActivity().getApplicationContext());
                 db.add(new Salle(code.getText().toString(), libelle.getText().toString()));
                 Toast.makeText(getActivity().getApplicationContext(), " Salle crée avec succès :)" , Toast.LENGTH_LONG).show();
             }
         });

        list = (Button) v.findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getActivity().getApplicationContext(), ListSalleActivity.class);
                startActivity(intent);

            }
        });
        //MainMachine m = new MainMachine();
        return v;
    }
}
