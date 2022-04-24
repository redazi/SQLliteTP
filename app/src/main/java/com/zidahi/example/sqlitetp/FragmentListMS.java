package com.zidahi.example.sqlitetp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.zidahi.example.sqlitetp.adapter.ListeMachinesSallesAdapter;
import com.zidahi.example.sqlitetp.beans.Machine;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.MachineService;
import com.zidahi.example.sqlitetp.service.SalleService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentListMS  extends Fragment {
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
    private ListView list;
    private MachineService machineService;
    private View v ;

    private ListView liste;

    private String mParam1;
    private String mParam2;

    public FragmentListMS() {

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
        View v = inflater.inflate(R.layout.fragment_list_machine_par_salle, container, false);
        list = (ListView) v.findViewById(R.id.listView);
        salleService = new SalleService(getActivity().getApplicationContext());
        spinner = (Spinner) v.findViewById(R.id.spinner);
        liste = (ListView) v.findViewById(R.id.listView);
        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();
        for (Salle salle : salleService.findAll()){
            list.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, list);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // You can launch activity here in your case.




                machineService = new MachineService(getActivity().getApplicationContext());
                int id = salleService.findByCode(spinner.getSelectedItem().toString()).getId();
                ListeMachinesSallesAdapter as = new ListeMachinesSallesAdapter(getActivity().getApplicationContext(), machineService.findMachines(id));
                liste.setAdapter(as);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }}