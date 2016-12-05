package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;


public class PatientsForm extends AppCompatActivity {

    EditText name, address, birth, schooling, gender, phone, susid;
    Button salvar, exame, examesSalvos;
    Patients patientToEdit, patient;
    PatientsDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_patients);

        Intent intent = getIntent();
        patientToEdit = (Patients) intent.getSerializableExtra("patient-selecionado");

        patient = new Patients();
        dao = new PatientsDAO(PatientsForm.this);

        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        birth = (EditText) findViewById(R.id.birth);
        schooling = (EditText) findViewById(R.id.schooling);
        gender = (EditText) findViewById(R.id.gender);
        phone = (EditText) findViewById(R.id.phone);
        susid = (EditText) findViewById(R.id.susid);

        salvar = (Button) findViewById(R.id.botaoSalvar);
        exame = (Button) findViewById(R.id.botaoExame);
        examesSalvos = (Button) findViewById(R.id.botaoResults);

        if (patientToEdit != null){
            salvar.setText("Alterar");

            name.setText(patientToEdit.getName());
            address.setText(patientToEdit.getAddress());
            birth.setText(patientToEdit.getBirth());
            schooling.setText(patientToEdit.getSchooling());
            gender.setText(patientToEdit.getGender());
            phone.setText(patientToEdit.getPhone());
            susid.setText(patientToEdit.getSusid());

            patient.setId(patientToEdit.getId());

        } else {
            salvar.setText("Salvar");
        }

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient.setName(name.getText().toString());
                patient.setAddress(address.getText().toString());
                patient.setBirth(birth.getText().toString());
                patient.setSchooling(schooling.getText().toString());
                patient.setGender(gender.getText().toString());
                patient.setPhone(phone.getText().toString());
                patient.setSusid(susid.getText().toString());

                if (salvar.getText().toString().equals("Salvar")){
                    dao.savePatient(patient);
                    dao.close();

                    Toast.makeText(getApplicationContext(), "Cadastro criado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    dao.modifyPatient(patient);
                    dao.close();
                }

                finish();
            }
        });

        exame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent(PatientsForm.this, TesteMainActivity.class);
                Bundle bundle = new Bundle();

                if (patientToEdit != null) {
                    Long getid = patientToEdit.getId();
                    String patientId = String.valueOf(getid);
                    patientId.toString();

                    bundle.putString("patientId", patientId);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else
                   Toast.makeText(getApplicationContext(), "Primeiro salve o cadastro!", Toast.LENGTH_SHORT).show();
            }
        });

        examesSalvos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Intent intent = new Intent(PatientsForm.this, ResultsListActivity.class);
                Bundle bundle = new Bundle();

                if (patientToEdit != null) {
                    Long getid = patientToEdit.getId();
                    String patientId = String.valueOf(getid);
                    patientId.toString();

                    bundle.putString("patientId", patientId);
                    intent.putExtras(bundle);

                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "Primeiro salve o cadastro!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
