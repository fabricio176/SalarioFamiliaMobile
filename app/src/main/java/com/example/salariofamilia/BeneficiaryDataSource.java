package com.example.salariofamilia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryDataSource {
    private SQLiteDatabase database;
    private BeneficiaryDatabaseHelper dbHelper;

    public BeneficiaryDataSource(Context context) {
        dbHelper = new BeneficiaryDatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertBeneficiary(Beneficiary beneficiary) {
        ContentValues values = new ContentValues();
        values.put(BeneficiaryDatabaseHelper.COLUMN_NOME, beneficiary.getNome());
        values.put(BeneficiaryDatabaseHelper.COLUMN_CPF, beneficiary.getCpf());
        values.put(BeneficiaryDatabaseHelper.COLUMN_DATA_NASCIMENTO, beneficiary.getDataNascimento());

        return database.insert(BeneficiaryDatabaseHelper.TABLE_NAME, null, values);
    }

    public void deleteBeneficiary(int beneficiaryId) {
        database.delete(BeneficiaryDatabaseHelper.TABLE_NAME,
                BeneficiaryDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(beneficiaryId)});
    }

    public List<Beneficiary> getAllBeneficiaries() {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        Cursor cursor = database.query(
                BeneficiaryDatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        int idColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_ID);
        int nomeColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_NOME);
        int cpfColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_CPF);
        int dataNascimentoColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_DATA_NASCIMENTO);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(idColumnIndex);
            String nome = cursor.getString(nomeColumnIndex);
            String cpf = cursor.getString(cpfColumnIndex);
            String dataNascimento = cursor.getString(dataNascimentoColumnIndex);

            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setBeneficiarioID(id);
            beneficiary.setNome(nome);
            beneficiary.setCpf(cpf);
            beneficiary.setDataNascimento(dataNascimento);

            beneficiaries.add(beneficiary);
        }

        cursor.close();

        return beneficiaries;
    }

    public void updateBeneficiary(Beneficiary beneficiary) {
        ContentValues values = new ContentValues();
        values.put(BeneficiaryDatabaseHelper.COLUMN_NOME, beneficiary.getNome());
        values.put(BeneficiaryDatabaseHelper.COLUMN_CPF, beneficiary.getCpf());
        values.put(BeneficiaryDatabaseHelper.COLUMN_DATA_NASCIMENTO, beneficiary.getDataNascimento());

        database.update(BeneficiaryDatabaseHelper.TABLE_NAME, values,
                BeneficiaryDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(beneficiary.getBeneficiarioID())});
    }

    public Beneficiary getBeneficiaryById(int beneficiaryId) {
        Beneficiary beneficiary = null;
        Cursor cursor = database.query(
                BeneficiaryDatabaseHelper.TABLE_NAME,
                null,
                BeneficiaryDatabaseHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(beneficiaryId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_ID);
            int nomeColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_NOME);
            int cpfColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_CPF);
            int dataNascimentoColumnIndex = cursor.getColumnIndex(BeneficiaryDatabaseHelper.COLUMN_DATA_NASCIMENTO);

            int id = cursor.getInt(idColumnIndex);
            String nome = cursor.getString(nomeColumnIndex);
            String cpf = cursor.getString(cpfColumnIndex);
            String dataNascimento = cursor.getString(dataNascimentoColumnIndex);

            beneficiary = new Beneficiary();
            beneficiary.setBeneficiarioID(id);
            beneficiary.setNome(nome);
            beneficiary.setCpf(cpf);
            beneficiary.setDataNascimento(dataNascimento);
        }

        cursor.close();
        return beneficiary;
    }

}
