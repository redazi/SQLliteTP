package com.zidahi.example.sqlitetp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zidahi.example.sqlitetp.adapter.SalleAdapter;
import com.zidahi.example.sqlitetp.beans.Salle;
import com.zidahi.example.sqlitetp.service.SalleService;

import java.util.HashMap;




public class ListSalleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView list;
    private SalleService ad;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_salle);
        list = (ListView) findViewById(R.id.listView);
        ad = new SalleService(this);
        SalleAdapter as = new SalleAdapter(this, ad.findAll());
        list.setAdapter(as);
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, ((TextView) view.findViewById(R.id.ids)).getText().toString() + " " + ((TextView) view.findViewById(R.id.code)).getText() + " " + ((TextView) view.findViewById(R.id.libelle)).getText(), Toast.LENGTH_LONG).show();
        v = view;
        AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(this);
        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);

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
                        finish();
                        startActivity(getIntent());
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
                Intent intent = new Intent(getApplicationContext(), ModifierActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), ListeMachinesSalles.class);
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
}
