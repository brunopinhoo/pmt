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
        dao = PatientsDAO.newInstance(this);

        Bundle b = getIntent().getExtras();
        if (b != null && b.getString("patientId") != null) {
            patient = dao.getPatientById(b.getString("patientId"));
            listView.setAdapter(new ResultsAdapter(this, patient));
        }
        else
            listView.setAdapter(new AllResultsAdapter(this));

        this.setTitle("Resultados dos Testes");
    }
}
