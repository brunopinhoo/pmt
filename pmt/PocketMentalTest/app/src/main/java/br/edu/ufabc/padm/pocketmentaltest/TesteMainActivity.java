package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;

public class TesteMainActivity extends AppCompatActivity {

    private PatientsDAO dao;
    private Patients patient;

    private RadioGroup group;
    private RadioButton meem;
    private RadioButton cdr;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testes_main);

        this.context = this;
        dao = PatientsDAO.newInstance(this);

        // get patient from DAO with the given ID from intent
        //long id = getIntent().getExtras().getLong("patientId");
        Intent intent = getIntent();
        Bundle dados;

        String id;
        dados = intent.getExtras();

        id = dados.getString("patientId").toString();

        Toast toast = Toast.makeText(context, id+" teste do ID - remover antes de submeter", Toast.LENGTH_SHORT);
        toast.show();


        this.patient = dao.getPatientById(id);

        group = (RadioGroup )findViewById(R.id.radioGroup_testes);
        meem = (RadioButton )findViewById(R.id.radioButton_testes_MEEM);
        cdr = (RadioButton )findViewById(R.id.radioButton_testes_CDR);

        Button iniciar = (Button )findViewById(R.id.button_iniciar_teste);
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check which was the radiobutton clicked
                if (group.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(context, "Selecione pelo menos uma opcao", Toast.LENGTH_SHORT).show();
                } else {
                    if (meem.isChecked()) {

                        // TODO open the meem test actv
                        Toast.makeText(context, "Coming soon....", Toast.LENGTH_SHORT).show();

                    } else if (cdr.isChecked()) {

                        Intent intent = new Intent(context, CDRAddActivity.class);

                        intent.putExtra("patientId", patient.getId());
                        intent.putExtra("testNumber", 0);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Toast.makeText(context, "Opcao invalida", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
