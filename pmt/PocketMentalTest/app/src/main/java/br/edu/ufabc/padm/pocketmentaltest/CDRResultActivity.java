package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;
import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.Result;

public class CDRResultActivity extends AppCompatActivity {

    private double[] results = new double[6];
    private PatientsDAO dao;
    private Patients patient;
    private String testDate;
    private String resultsTextFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdr_result);

        this.dao = PatientsDAO.newInstance(this);

        // get the patient data
        Long patientId = getIntent().getExtras().getLong("patientId");
        String IdSt = patientId.toString();
        this.patient = dao.getPatientById(IdSt);

        // get the scores
        results[0] = getIntent().getExtras().getDouble("memoria");
        results[1] = getIntent().getExtras().getDouble("orientacao");
        results[2] = getIntent().getExtras().getDouble("julgamento");
        results[3] = getIntent().getExtras().getDouble("comunidade");
        results[4] = getIntent().getExtras().getDouble("lazer");
        results[5] = getIntent().getExtras().getDouble("higiene");

        // calculate overall score
        double finalScore = calculateScore();

        // get patient id from intent
        savePatientScore(finalScore);

        // Set the title and the score values in the view
        TextView titleName = (TextView )findViewById(R.id.cdr_result_pacient_name);
        titleName.setText(this.patient.getName());

        TextView titleDate = (TextView )findViewById(R.id.cdr_result_data_teste);
        this.testDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        titleDate.setText(this.testDate);

        resultsTextFormat = setScoreViewValues(finalScore);
        handleCloseButton();
        handleDetailsButtons();
    }

    private void handleDetailsButtons() {
        // TODO open the details activity passing the score and information

    }

    private void handleCloseButton() {
        // return to the patients list?
        Intent intent = new Intent(this, PatientsList.class);
//        intent.putExtra("patientId", this.patient.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private String setScoreViewValues(double finalScore) {

        TextView finalScoreView = (TextView )findViewById(R.id.cdr_result_value);
        finalScoreView.setText(String.format("CDR %1$.1f", finalScore));

        TextView description = (TextView )findViewById(R.id.cdr_result_demencia_description);
        String descTxt = "";
        if (finalScore == 0.5) {
            descTxt = "Demência questionável";
        } else if (finalScore == 1) {
            descTxt = "Demência leve";
        } else if (finalScore == 2) {
            descTxt = "Demência moderada";
        } else if (finalScore == 3) {
            descTxt = "Demência grave";
        } else {
            descTxt = "Saudável";
        }
        description.setText(descTxt);

        TextView memoriaView = (TextView )findViewById(R.id.cdr_result_memory_value);
        memoriaView.setText(String.valueOf(results[0]));

        TextView orientacaoView = (TextView )findViewById(R.id.cdr_result_orientation_value);
        orientacaoView.setText(String.valueOf(results[1]));

        TextView julgamentoView = (TextView )findViewById(R.id.cdr_result_problemsolving_value);
        julgamentoView.setText(String.valueOf(results[2]));

        TextView comunidadeView = (TextView )findViewById(R.id.cdr_result_comunidade_value);
        comunidadeView.setText(String.valueOf(results[3]));

        TextView lazerView = (TextView )findViewById(R.id.cdr_result_lazer_value);
        lazerView.setText(String.valueOf(results[4]));

        TextView higieneView = (TextView )findViewById(R.id.cdr_result_cuidadospess_value);
        higieneView.setText(String.valueOf(results[5]));

        return String.format("Teste CDR\nResultado final: %s\n%s\n\n" +
                "Memória: %s\nOrientação: %s\nJulgamento e solução de problemas: %s\n" +
                "Assuntos na Comunidade: %s\nLazer e Atividades no Lar: %s\nCuidados Pessoais: %s",
                String.valueOf(finalScore), descTxt, String.valueOf(results[0]),
                String.valueOf(results[1]), String.valueOf(results[2]), String.valueOf(results[3]),
                String.valueOf(results[4]), String.valueOf(results[5]));
    }

    private void savePatientScore(double score) {
        // save with DAO the scores with DATE and CDR name
        Result result = new Result();
        result.setTeste("CDR");
        result.setScore(score);
        result.setId(this.patient.getId());
        result.setData(this.testDate);
        this.dao.addResult(this.patient, result);
    }

    private  float roundToHalf(float x) {
        return (float) (Math.ceil(x * 2) / 2);
    }

    private double calculateScore() {
        float sum = 0;
        for (int i = 0; i < results.length; i++)
            sum += results[i];

        return (roundToHalf(sum/5) == 0.5) ? 0.5 : Math.ceil(sum);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cdr_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sendemail) {

            Intent i = new Intent(Intent.ACTION_SENDTO);
            //i.setType("message/rfc822");
            i.setType("*/*");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"example_address@email.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "CDR Test Results: " + this.testDate);
            i.putExtra(Intent.EXTRA_TEXT, resultsTextFormat);

            try {
                startActivity(Intent.createChooser(i, "Enviar email..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Nao ha aplicativos de email instalados", Toast.LENGTH_SHORT).show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
