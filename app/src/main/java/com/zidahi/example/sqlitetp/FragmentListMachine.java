package com.zidahi.example.sqlitetp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zidahi.example.sqlitetp.adapter.ListeMachinesSallesAdapter;
import com.zidahi.example.sqlitetp.adapter.MachineAdapter;
import com.zidahi.example.sqlitetp.beans.Machine;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.MachineService;
import com.zidahi.example.sqlitetp.service.SalleService;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListMachine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListMachine extends android.app.Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView list;
    private MachineService machineService;
    private String mParam1;
    private String mParam2;
    private View v1 ;
    private HashMap data ;
    private Intent i;
    private Serializable s;
    public FragmentListMachine() {

    }


    public static FragmentListMachine newInstance(String param1, String param2) {
        FragmentListMachine fragment = new FragmentListMachine();
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
        View v = inflater.inflate(R.layout.frag_list_machines ,container, false);
        list = (ListView) v.findViewById(R.id.listView);
        i = getActivity().getIntent();
        s = getActivity().getIntent().getSerializableExtra("data");
        data = (HashMap) s ;
        machineService = new MachineService(getActivity());
        ListeMachinesSallesAdapter as = new ListeMachinesSallesAdapter(getContext(), machineService.findMachines(Integer.parseInt(data.get("id").toString())));
        list.setAdapter(as);

        list.setAdapter(as);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // You can launch activity here in your case.

                v1 = view;
                AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(getActivity());
                final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialogBuilder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                int id = Integer.parseInt(((TextView) v1.findViewById(R.id.ids)).getText().toString());
                                machineService.delete(machineService.findById(id));
                                Intent intent = new Intent(getActivity().getApplicationContext(), MainMachine.class);
                                startActivity(intent);
                            }
                        });
                        alertDialogBuilder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder1.create();
                        alertDialog.show();
                    }
                });

                alertDialogBuilder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getActivity().getApplicationContext(), ModifierMachine.class);
                        int id = Integer.parseInt(((TextView) v1.findViewById(R.id.ids)).getText().toString());
                        Machine machine = machineService.findById(id);
                        HashMap<String,String> data = new HashMap<>();
                        data.put("id",machine.getId()+"");
                        data.put("idSalle",machine.getSalle().getCode()+"");
                        data.put("marque",machine.getMarque());
                        data.put("reference",machine.getRefernce());
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }
                });

                AlertDialog alertDialog1 = alertDialogBuilder.create();
                alertDialog1.show();
            }
        });


        //MainMachine m = new MainMachine();
        return v;
    }

    }




