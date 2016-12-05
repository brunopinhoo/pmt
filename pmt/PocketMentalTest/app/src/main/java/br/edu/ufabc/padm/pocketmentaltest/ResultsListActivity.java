package br.edu.ufabc.padm.pocketmentaltest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;

/**
 * Created by victor on 12/4/16.
 */

public class ResultsListActivity  extends AppCompatActivity {
    private ListView listView;
    PatientsDAO dao;
    Patients patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);

        listView = (ListView)findViewById(R.id.listview_testsresults);
        listView.setAdapter(new ResultsAdapter(this, patient));

        dao = PatientsDAO.newInstance(this);

        // OBS: important to pass as extra the patientID before starting this activity
        String id = getIntent().getExtras().getString("patientId");
        patient = dao.getPatientById(id);

        this.setTitle("Resultados dos Testes");
    }
}
