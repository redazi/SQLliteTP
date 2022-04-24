package com.zidahi.example.sqlitetp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zidahi.example.sqlitetp.beans.Machine;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.MachineService;
import com.zidahi.example.sqlitetp.service.SalleService;

import java.util.ArrayList;
import java.util.List;



public class MainMachine extends AppCompatActivity {

    private EditText marque ;
    private EditText reference ;
    private Button create ;
    private Button listeMachines ;
    private Button menu ;
    private Spinner spinner ;
    private SalleService salleService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_machine);
        salleService = new SalleService(getApplicationContext());
        marque = (EditText) findViewById(R.id.marque);
        reference = (EditText) findViewById(R.id.reference);
        create = (Button) findViewById(R.id.btnCreate);
        menu = (Button) findViewById(R.id.menu);
        listeMachines = (Button) findViewById(R.id.btnListeMachines);
        spinner = (Spinner) findViewById(R.id.Spinner);

        ArrayAdapter<String> adapter;
        List<String> liste= new ArrayList<String>();
        for(Salle salle : salleService.findAll()) {
            liste.add(salle.getCode());
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, liste);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MachineService machineService = new MachineService(getApplicationContext());
                salleService = new SalleService(getApplicationContext());
                Salle salle = salleService.findByCode(spinner.getSelectedItem().toString());
                machineService.add(new Machine(marque.getText().toString(), reference.getText().toString(), salle ));
                Toast.makeText(getApplicationContext() ," Machine crée avec succès :) ", Toast.LENGTH_LONG).show();
            }
        });

        listeMachines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ListeMachines.class);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
