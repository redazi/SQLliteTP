package com.zidahi.example.sqlitetp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.SalleService;



public class Main extends AppCompatActivity {
    private EditText code;
    private EditText libelle;
    private Button add;
    private Button menu;
    private Button list;

    SalleService db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        code = (EditText) findViewById(R.id.code);
        libelle = (EditText) findViewById(R.id.libelle);
        add = (Button) findViewById(R.id.add);
        menu = (Button) findViewById(R.id.menu);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db =  new SalleService(getApplicationContext());
                db.add(new Salle(code.getText().toString(), libelle.getText().toString()));
                Toast.makeText(getApplicationContext(), " Salle crée avec succès :)" , Toast.LENGTH_LONG).show();
            }
        });

        list = (Button) findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMachine.class);
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

        // Add Salle
          //db.add(new Salle("A1", "Arabe"));
          //db.add(new Salle("A2", "Anglais"));
          //db.add(new Salle("A3", "Info"));

         //db.delete(db.findById(1));
        // Get all salle
          //List<Salle> list = db.findAll();

        // Delete one salle
       // db.delete(list.get(0));
        // Get all salle
       // db.findAll();
       // Salle s = db.findById(2);
       /// MachineService md = new MachineService(getApplicationContext());
       // md.add(new Machine("HP", "REF", db.findById(2)));
      //  List<Machine> machines = md.findAll();
       // Machine machine = md.findById(3);
       // md.delete(machine);
    }



}
