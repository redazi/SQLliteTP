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
import android.widget.ListView;
import android.widget.TextView;

import com.zidahi.example.sqlitetp.adapter.SalleAdapter;
import com.zidahi.example.sqlitetp.beans.Machine;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.SalleService;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_list_salles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_list_salles extends Fragment {
    private ListView list;
    private SalleService ad;
    private View v1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_list_salles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_list_salles.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_list_salles newInstance(String param1, String param2) {
        Fragment_list_salles fragment = new Fragment_list_salles();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.frag_list_machines ,container, false);
        inflater.inflate(R.layout.fragment_list_salles, container, false);
        list = (ListView) v.findViewById(R.id.listView);
        ad = new SalleService(getContext());
        SalleAdapter as = new SalleAdapter(getContext(), ad.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // You can launch activity here in your case.

                v1 = view;
                AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(getContext());
                final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(getContext());

                alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        alertDialogBuilder1.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                                ad.delete(ad.findById(id));
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                //startActivity(intent);
                                getActivity().finish();
                                startActivity(getActivity().getIntent());
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
                        Intent intent = new Intent(getActivity().getApplicationContext(), ModifierActivity.class);
                        int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                        Salle salle = ad.findById(id);
                        HashMap<String,String> data = new HashMap<>();
                        data.put("id",salle.getId()+"");
                        data.put("code",salle.getCode());
                        data.put("libelle",salle.getLibelle());
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }
                });

                alertDialogBuilder.setNegativeButton("Machines List", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), ListeMachinesSalles.class);
                        int id = Integer.parseInt(((TextView) v.findViewById(R.id.ids)).getText().toString());
                        Salle salle = ad.findById(id);
                        HashMap<String,String> data = new HashMap<>();
                        data.put("id",salle.getId()+"");
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
}}