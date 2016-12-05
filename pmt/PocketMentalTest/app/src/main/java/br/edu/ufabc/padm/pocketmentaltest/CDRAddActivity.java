package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class CDRAddActivity extends AppCompatActivity {

    // Colummns = test number
    // Lines = title, opt1, opt2, etc
    private static String[][] texts = null;
    private static double[] results = new double[6];

    private int testNumber;
    private long patientId;
    Context context;

    private RadioButton opt1;
    private RadioButton opt2;
    private RadioButton opt3;
    private RadioButton opt4;
    private RadioButton opt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdr_add);
        context = this;

        if (texts == null)
            loadTextsMatriz();

        this.patientId = getIntent().getExtras().getLong("patientId");
        this.testNumber = getIntent().getExtras().getInt("testNumber");
        setAlternatives(this.testNumber);

        // register button handlers
        registerHandlers();
    }

    // change text accordingly
    private void setAlternatives(int testNumber) {
        TextView title = (TextView) findViewById(R.id.cdr_add_title);
        title.setText(texts[testNumber][0]);
        opt1 = (RadioButton) findViewById(R.id.cdr_add_opt1);
        opt1.setText(texts[testNumber][1]);
        opt1.setChecked(true); // first is default one
        opt2 = (RadioButton) findViewById(R.id.cdr_add_opt2);
        opt2.setText(texts[testNumber][2]);
        opt3 = (RadioButton) findViewById(R.id.cdr_add_opt3);
        opt3.setText(texts[testNumber][3]);
        opt4 = (RadioButton) findViewById(R.id.cdr_add_opt4);
        opt4.setText(texts[testNumber][4]);
        opt5 = (RadioButton) findViewById(R.id.cdr_add_opt5);
        opt5.setText(texts[testNumber][5]);
    }

    private void registerHandlers() {

        // NEXT
        Button nextButton = (Button)findViewById(R.id.cdr_add_button_prox);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                results[testNumber] = getResult();

                switch(testNumber) {
                    case 5:
                        // open the intent to result window
                        Intent intent = new Intent(context, CDRResultActivity.class);
                        intent.putExtra("patientId", patientId); // repassar

                        intent.putExtra("memoria", results[0]);
                        intent.putExtra("orientacao", results[1]);
                        intent.putExtra("julgamento", results[2]);
                        intent.putExtra("comunidade", results[3]);
                        intent.putExtra("lazer", results[4]);
                        intent.putExtra("higiene", results[5]);

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        Log.i("DEBUGGING", "opening CDRresultActivity");

                        startActivity(intent);
                        break;
                    default:
                        // just advance the test
                        Intent i = new Intent(context, CDRAddActivity.class);

                        i.putExtra("testNumber", testNumber+1);
                        i.putExtra("patientId", patientId); // repassar

                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(i);

                        break;
                }
            }
        });

        // PREVIOUS
        Button prevButton = (Button)findViewById(R.id.cdr_add_button_ant);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // reset all scores from this test until the end
                for (int i = testNumber; i <= 5; i++)
                    results[i] = 0;

                Intent intent;
                switch(testNumber) {
                    case 0:
                        // return to the test selection
                        intent = new Intent(context, TesteMainActivity.class);
                        intent.putExtra("patientId", patientId);
                        startActivity(intent);

                        break;
                    default:
                        // just return one test
                        intent = new Intent(context, CDRAddActivity.class);
                        intent.putExtra("testNumber", testNumber-1);
                        intent.putExtra("patientId", patientId); // repassar
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;
                }
            }
        });

        // RECORD
        Button record = (Button)findViewById(R.id.button_record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO open intent to record and save in folder the interview
                // for now just save the audio file
                Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);

                try {
                    startActivity(Intent.createChooser(intent, "Gravar audio..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Nao ha aplicativos para gravação de audio instalados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private double getResult() {
        double value = 0;

        if (opt2.isChecked()) {
            value = 0.5;
        } else if (opt3.isChecked()) {
            value = 1.0;
        } else if (opt4.isChecked()) {
            value = 2.0;
        } else if (opt5.isChecked()) {
            value = 3.0;
        }

        return value;
    }

    private void loadTextsMatriz() {
        texts = new String[6][6];

        // memoria
        texts[0][0] = "Memória";
        texts[0][1] = "Sem perda de memória, ou apenas esquecimento discreto e incosciente";
        texts[0][2] = "Esquecimento leve e consistente; lembrança parcial dos eventos; esquecimento benigno";
        texts[0][3] = "Perda de memória moderada, mais acentuada para fatos recentes; déficit interfere em atividades do dia-a-dia";
        texts[0][4] = "Perda de memória grave; apenas material muito aprendido é retido; materiais novos são rapidamente perdidos";
        texts[0][5] = "Perda de memória grave; apenas fragmentos aparecem";

        // orientacao
        texts[1][0] = "Orientação";
        texts[1][1] = "Plenamente orientado";
        texts[1][2] = "Quase pleanamente orientado";
        texts[1][3] = "Dificuldade moderada com as relações de tempo; orientado no espaço no exame, mas pode ter desorientação geográfica em outros locais";
        texts[1][4] = "Geralmente desorientado";
        texts[1][5] = "Orientação pessoal apenas";

        // Julgamnto e solucao de problemas
        texts[2][0] = "Julgamento e Solução de Problemas";
        texts[2][1] = "Resolve bem problemas do dia-a-dia, juízo crítico é bom em relação ao desempenho passado";
        texts[2][2] = "Leve comprometimento na solução de problemas, semelhanças e diferenças";
        texts[2][3] = "Dificuldade moderada na solução de problemas, semelhanças e diferenças; julgamento social geralmente mantido";
        texts[2][4] = "Gravemente comprometido para solução de problemas, semelhanças e diferenças; juízo social geralmente comprometido";
        texts[2][5] = "Incapaz de resolver problemas ou de ter qualquer juízo crítico";

        // Assuntos na Comunidade
        texts[3][0] = "Assuntos na Comunidade";
        texts[3][1] = "Função independente na função habitual de trabalho, compras, negócios, finanças e grupos sociais";
        texts[3][2] = "Leve dificuldade nessas atividades";
        texts[3][3] = "Incapaz de funcionar independentemente nessas atividades, embora ainda possa desempenhar algumas delas; pode parecer normal à avaliação superficial";
        texts[3][4] = "Sem possibilidade de desempenho fora de casa. Parece suficientemente bem para ser levado a atividades fora de casa";
        texts[3][5] = "Sem possiblidade de desempenho fora de casa. Parece muito doente para ser levado a atividades fora de casa";

        // Lazer e Pariticipacao no lar
        texts[4][0] = "Lazer e Participação no Lar";
        texts[4][1] = "Vida em casa, passatempos e interesses intelectuais mantidos";
        texts[4][2] = "Vida em casa, passatempos e interesses intelectuais levemente afetados";
        texts[4][3] = "Comprometimento leve, mas evidentemente em casa; abandono das tarefas mais difíceis; passatempos e interesses mais complicados são abandonados";
        texts[4][4] = "Só realiza tarefas mais simples. Interesses muito limitados e pouco mantidos";
        texts[4][5] = "Sem qualquer atividade significativa fora de casa";

        // Cuidados pessoais
        texts[5][0] = "Cuidados Pessoais";
        texts[5][1] = "Plenamente capaz";
        texts[5][2] = "Quase plenamente capaz mas não necessita de assistência";
        texts[5][3] = "Necessita assistência ocasional";
        texts[5][4] = "Requer assistência no vestir e higiene";
        texts[5][5] = "Requer muito auxílio nos cuidados pessoais. Geralmente incontinente";
    }

}
