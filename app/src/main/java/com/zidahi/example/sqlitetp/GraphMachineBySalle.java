package com.zidahi.example.sqlitetp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.zidahi.example.sqlitetp.adapter.ListeMachinesSallesAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GraphMachineBySalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GraphMachineBySalle extends android.app.Fragment {

    private BarChart chart;
    private MachineService machineService;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public GraphMachineBySalle() {
        // Required empty public constructor
    }



    public static GraphMachineBySalle newInstance(String param1, String param2) {
        GraphMachineBySalle fragment = new GraphMachineBySalle();
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

        View v = inflater.inflate(R.layout.fragment_graph_machine_by_salle, container, false);
        chart = v.findViewById(R.id.chart1);
        initializeBarChart();
        loadAll();
        return v;

    }
    public void loadAll() {
        List<Machine> machineList = new ArrayList<>();
        machineService=new MachineService(getActivity().getApplicationContext());
        System.out.println("ammmmmmmmmmmmm heeeeeeeereeeeeeeeeeeee");
        machineList= machineService.findAll();
          for (Machine m : machineList)
              System.out.println(m);

        Map<String, Integer> map = new HashMap<>();
        for(Machine m : machineList) {
            if(map.containsKey(m.getSalle().getCode())) {
                map.put(m.getSalle().getCode(), map.get(m.getSalle().getCode()) + 1);
            }else {
                map.put(m.getSalle().getCode(), 1);
            }
        }
        createBarChart(map);

    }

    private void initializeBarChart() {
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(4);
        chart.getXAxis().setDrawGridLines(false);
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);
        chart.animateY(1500);
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(true);
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.invalidate();
    }

    private void createBarChart(Map<String, Integer> map) {
        List<BarEntry> values = new ArrayList<>();
        int i = 0;
        for(String key : map.keySet()) {
            values.add(new BarEntry(i, map.get(key)));
            ++i;
        }

        BarDataSet set1;
        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setDrawValues(true);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setVisibleXRange(1,4);
            chart.setFitBars(true);
            XAxis xAxis = chart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(map.keySet()));
            for (IDataSet set : chart.getData().getDataSets())
                set.setDrawValues(!set.isDrawValuesEnabled());
            chart.invalidate();
        }
    }}
