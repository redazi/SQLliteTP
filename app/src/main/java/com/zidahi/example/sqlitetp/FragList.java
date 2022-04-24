package com.zidahi.example.sqlitetp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.zidahi.example.sqlitetp.adapter.ListeMachinesSallesAdapter;
import com.zidahi.example.sqlitetp.service.MachineService;

import java.io.Serializable;
import java.util.HashMap;

public class FragList extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView list;
    private MachineService machineService;
    private View v ;
    private HashMap data ;
    private Intent i;
    private Serializable s;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragList() {
        // Required empty public constructor
    }


    public static FragList newInstance(String param1, String param2) {
        FragList fragment = new FragList();
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
        View v = inflater.inflate(R.layout.activity_liste_machines_salles ,container, false);
        list = (ListView) v.findViewById(R.id.listView);
        i = getActivity().getIntent();
        s = getActivity().getIntent().getSerializableExtra("data");
        data = (HashMap) s ;
        machineService = new MachineService(getContext());
        ListeMachinesSallesAdapter as = new ListeMachinesSallesAdapter(getContext(), machineService.findMachines(Integer.parseInt(data.get("id").toString())));
        list.setAdapter(as);
        return v;
    }
}