package br.edu.ufabc.padm.pocketmentaltest.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import br.edu.ufabc.padm.pocketmentaltest.R;

public class PatientsDAO extends SQLiteOpenHelper {

    private static PatientsDAO dao;
    private Context context;
    private SQLiteDatabase db;

    private static final String DATABASE = "patients";
    private static final int VERSION = 2;

    private static final String LOGTAG = PatientsDAO.class.getName();


    public PatientsDAO(Context c){
        super(c, DATABASE, null, VERSION);
        this.context = c;
        this.db = getWritableDatabase();
    }

    public static PatientsDAO newInstance(Context c) {
        if(dao == null) {
            dao = new PatientsDAO(c);
        } else
            dao.context = c;

        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String patient = "CREATE TABLE patients " +
                       " ( " +
                         " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                         " name TEXT NOT NULL," +
                         " address TEXT NOT NULL," +
                         " birth TEXT NOT NULL," +
                         " schooling TEXT NOT NULL,"+
                         " gender TEXT NOT NULL," +
                         " phone TEXT NOT NULL,"+
                         " susid TEXT NOT NULL"+
                         " );";
        db.execSQL(patient);*/

        /*String patient = "CREATE TABLE patients " +
                         " ( " +
                         " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                         " name TEXT NOT NULL," +
                         " address TEXT NOT NULL," +
                         " birth TEXT NOT NULL," +
                         " schooling TEXT NOT NULL,"+
                         " gender TEXT NOT NULL," +
                         " phone TEXT NOT NULL,"+
                         " susid TEXT NOT NULL"+
                         " ); "+
                         "CREATE TABLE tests_results_list "+
                         " ( "+
                         " _patientid INTEGER,"+
                         " FOREIGN KEY (_patient_id) REFERENCES patients(_id),"+
                         " date TEXT NOT NULL,"+
                         " score INTEGER NOT NULL,"+
                         " teste TEXT NOT NULL"+
                         " );";
        db.execSQL(patient);*/
        String patient = "CREATE TABLE patients " +
                " ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name TEXT NOT NULL," +
                " address TEXT NOT NULL," +
                " birth TEXT NOT NULL," +
                " schooling TEXT NOT NULL,"+
                " gender TEXT NOT NULL," +
                " phone TEXT NOT NULL,"+
                " susid TEXT NOT NULL,"+
                " date TEXT NULL,"+
                " score TEXT NULL,"+
                " teste TEXT NULL"+
                " );";
        db.execSQL(patient);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String patient = "DROP TABLE IF EXISTS patients";
        db.execSQL(patient);
    }

    public void savePatient(Patients patient){
        ContentValues values = new ContentValues();

        values.put("name", patient.getName());
        values.put("address", patient.getAddress());
        values.put("birth", patient.getBirth());
        values.put("schooling", patient.getSchooling());
        values.put("gender", patient.getGender());
        values.put("phone", patient.getPhone());
        values.put("susid", patient.getSusid());

        getWritableDatabase().insert("patients", null, values);

    }

    public void modifyPatient(Patients patient){
        ContentValues values = new ContentValues();

        values.put("name", patient.getName());
        values.put("address", patient.getAddress());
        values.put("birth", patient.getBirth());
        values.put("schooling", patient.getSchooling());
        values.put("gender", patient.getGender());
        values.put("phone", patient.getPhone());
        values.put("susid", patient.getSusid());


        String[] args = {patient.getId().toString()};
        getWritableDatabase().update("patients", values, "id=?", args);
    }

    public void deletePatient(Patients patient){

        String[] args = {patient.getId().toString()};
        getWritableDatabase().delete("patients", "id=?", args);
    }

    public ArrayList<Patients> getList(){

        String[] columns = {"_id", "name", "address", "birth", "schooling", "gender", "phone", "susid"};
        Cursor cursor = getWritableDatabase().query("patients", columns, null, null, null, null, null, null);

        ArrayList<Patients> patients = new ArrayList<Patients>();

        while (cursor.moveToNext()){

            Patients patient = new Patients();

            patient.setId(cursor.getLong(0));
            patient.setName(cursor.getString(1));
            patient.setAddress(cursor.getString(2));
            patient.setBirth(cursor.getString(3));
            patient.setSchooling(cursor.getString(4));
            patient.setGender(cursor.getString(5));
            patient.setPhone(cursor.getString(6));
            patient.setSusid(cursor.getString(7));

            patients.add(patient);
        }

        return patients;
    }

    public Patients getPatientById(String id) {
        String query = "SELECT * FROM patients WHERE _id =  '" + id + "';";
        try {
            Cursor cursor = db.rawQuery(query, new String[]{});
            cursor.moveToFirst();

            if (cursor != null && cursor.moveToFirst()) {
                Patients p = new Patients();
                p.setId(cursor.getLong(0));
                p.setName(cursor.getString(1));
                p.setAddress(cursor.getString(2));
                p.setBirth(cursor.getString(3));
                p.setSchooling(cursor.getString(4));
                p.setGender(cursor.getString(5));
                p.setPhone(cursor.getString(6));
                p.setSusid(cursor.getString(7));

                cursor.close();
                return p;
            }

        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to get from the database", e);
        }
        return null;
    }

    // -- RESULTS
    public ArrayList<Result> listResultsFromPatient(Patients p) {
        ArrayList<Result> results = new ArrayList<>();
        //String queryStr = "SELECT * FROM tests_results_list WHERE _patientid =" + p.getId() + ";";
        String queryStr = "SELECT * FROM patients WHERE _id = '"+ p.getId() + "';";
        try {
            Cursor cursor = db.rawQuery(queryStr, new String[]{});
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Result r = new Result();
                //r.setId(cursor.getLong(0));
                r.setData(cursor.getString(8));//1
                r.setScore(cursor.getDouble(9));//2
                r.setTeste(cursor.getString(10));//3
                results.add(r);

                cursor.moveToNext();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to list from the database", e);
        }

        return results;
    }

    public boolean addResult(Patients patient, Result result) {

        //String queryStr = context.getString(R.string.insert_patientresult_query);
        boolean status = true;

        try {
            /*SQLiteStatement statement = db.compileStatement(queryStr);
            statement.bindLong(0, patient.getId());
            statement.bindString(8, result.getData());//1
            statement.bindDouble(9, result.getScore());//2
            statement.bindString(10, result.getTeste());//3

            statement.execute();*/

            ContentValues values = new ContentValues();

            values.put("date", result.getData());
            values.put("score", result.getScore());
            values.put("teste", result.getTeste());

            String[] args = {patient.getId().toString()};
            getWritableDatabase().update("patients", values, "_id=?", args);

        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to add in the database", e);
            status = false;
        }

        return status;
    }

    public boolean removeAllResultsFromPatient(Patients p) {
        try {
            return db.delete("tests_results_list", "_patientid = " + p.getId(), null) > 0;
        } catch (SQLiteException e) {
            Log.e(LOGTAG, "Failed to delete from the database", e);
        }
        return false;
    }





}
