package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;

/**
 * Created by bpinh on 12/4/16.
 */

public class PatientsList extends AppCompatActivity {
    ListView lista;
    PatientsDAO dao;
    ArrayList<Patients> listPatients;
    Patients patient;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        init();
    }

    private void init() {
        this.setTitle(R.string.title_patients);

        lista = (ListView) findViewById(R.id.patientslist_listview);

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Patients patientChose = (Patients) adapter.getItemAtPosition(position);

                Intent i = new Intent(PatientsList.this, PatientsForm.class);
                i.putExtra("patient-selecionado", patientChose);
                startActivity(i);

            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                patient = (Patients) adapter.getItemAtPosition(position);
                return false;
            }
        });

        //loadListView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Paciente");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                dao = new PatientsDAO(PatientsList.this);
                dao.deletePatient(patient);
                dao.close();

                loadPatient();

                return true;
            }
        });


        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPatient();

    }

    public void loadPatient(){
        dao = new PatientsDAO(PatientsList.this);

        listPatients = dao.getList();
        dao.close();

        if (listPatients != null){
            adapter = new ArrayAdapter<Patients>(PatientsList.this,
                    android.R.layout.simple_list_item_1,
                    listPatients);
            lista.setAdapter(adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(PatientsList.this, PatientsForm.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


}