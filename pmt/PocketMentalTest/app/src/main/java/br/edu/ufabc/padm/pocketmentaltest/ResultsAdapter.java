package br.edu.ufabc.padm.pocketmentaltest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ufabc.padm.pocketmentaltest.model.Patients;
import br.edu.ufabc.padm.pocketmentaltest.model.PatientsDAO;
import br.edu.ufabc.padm.pocketmentaltest.model.Result;

/**
 * Created by victor on 12/4/16.
 */

public class ResultsAdapter extends BaseAdapter {

    private Context context;
    private PatientsDAO dao;
    private Patients patient;
    private ArrayList<Result> list;

    public ResultsAdapter(Context c, Patients p) {
        this.context = c;
        this.dao = PatientsDAO.newInstance(context);
        this.patient = p;
        this.list = dao.listResultsFromPatient(patient);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView testType, date, score;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.results_plist_item, null);

        testType = (TextView) convertView.findViewById(R.id.result_type);
        date = (TextView) convertView.findViewById(R.id.result_date);
        score = (TextView) convertView.findViewById(R.id.result_score);

        Result r = list.get(position);
        testType.setText(r.getTeste());
        date.setText(r.getData());
        score.setText(String.valueOf(r.getScore()));

        return convertView;
    }

}
