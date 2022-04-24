package com.zidahi.example.sqlitetp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zidahi.example.sqlitetp.beans.Machine;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.MachineService;
import com.zidahi.example.sqlitetp.service.SalleService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMachine extends Fragment {
    private static final String TAG = "MachineFragment";
    private RecyclerView recyclerView;
    private EditText marque ;
    private EditText reference ;
    private Button create ;
    private Button listeMachines ;
    private Button menu ;
    private Spinner spinner ;
    private SalleService salleService;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnCreate ;


    private String mParam1;
    private String mParam2;

    public FragmentMachine() {

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
        View v = inflater.inflate(R.layout.frag_machine, container, false);
        salleService = new SalleService(getActivity().getApplicationContext());
        marque = (EditText) v.findViewById(R.id.marque);
        reference = (EditText) v.findViewById(R.id.reference);
        create = (Button) v.findViewById(R.id.btnCreate);
        menu = (Button) v.findViewById(R.id.menu);
        listeMachines = (Button) v.findViewById(R.id.btnListeMachines);
        spinner = (Spinner) v.findViewById(R.id.Spinner);
        ArrayAdapter<String> adapter;
        List<String> liste= new ArrayList<String>();
        for(Salle salle : salleService.findAll()) {
            liste.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, liste);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineService machineService = new MachineService(getActivity().getApplicationContext());
                salleService = new SalleService(getActivity().getApplicationContext());
                Salle salle = salleService.findByCode(spinner.getSelectedItem().toString());
                machineService.add(new Machine(marque.getText().toString(), reference.getText().toString(), salle ));
                Toast.makeText(getActivity().getApplicationContext() ," Machine crée avec succès :) ", Toast.LENGTH_LONG).show();
            }
        });
        listeMachines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ListeMachines.class);
                startActivity(intent);
               /* FragmentListMachine machineFragment = new FragmentListMachine();
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.fragment_container, machineFragment);
                ft1.commit();*/
               /* FragmentMachine machineFragment = new FragmentMachine();
                FragmentManager fm1 = getFragmentManager();
                FragmentTransaction ft1 = fm1.beginTransaction();
                ft1.replace(R.id.fragment_container, machineFragment);
                ft1.commit();*/
            }
        });
        //MainMachine m = new MainMachine();
        return v;
    }




    }