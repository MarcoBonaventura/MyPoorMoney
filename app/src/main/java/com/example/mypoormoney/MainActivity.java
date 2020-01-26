package com.example.mypoormoney;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageButton;
import android.database.Cursor;
import android.content.Context;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Button;
import android.icu.util.Calendar;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private DbManager db = null;
    private CursorAdapter adapter;
    private ListView listview = null;

    private int mYear;
    private int mMonth;
    private int mDay;

    Button btnDatePicker;
    EditText txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnDatePicker = (Button)findViewById(R.id.btn_date);
        txtDate = (EditText)findViewById(R.id.etxt_data);
        btnDatePicker.setOnClickListener(this);

        db = new DbManager(this);
        listview = (ListView) findViewById(R.id.listview);
        Cursor crs = db.query();
        adapter = new CursorAdapter(this, crs, 0) {
            @Override
            public View newView(Context ctx, Cursor arg1, ViewGroup arg2) {
                View v = getLayoutInflater().inflate(R.layout.row, null);
                return v;
            }

            @Override
            public void bindView(View v, Context arg1, Cursor crs) {
                String data = crs.getString(crs.getColumnIndex(DatabaseStrings.FIELD_DATE));
                String in = crs.getString(crs.getColumnIndex(DatabaseStrings.FIELD_IN));
                String out = crs.getString(crs.getColumnIndex(DatabaseStrings.FIELD_OUT));
                String operazione = crs.getString(crs.getColumnIndex(DatabaseStrings.FIELD_OPER));

                TextView txt = v.findViewById(R.id.etxt_data);
                txt.setText(data);

                txt = v.findViewById(R.id.etxt_in);
                txt.setText(in);

                txt = v.findViewById(R.id.etxt_out);
                txt.setText(out);

                txt = v.findViewById(R.id.etxt_oper);
                txt.setText(operazione);

                ImageButton imgbtn = v.findViewById(R.id.btn_delete);
                imgbtn.setOnClickListener(clickListener);
            }

            @Override
            public long getItemId(int position) {
                Cursor crs = adapter.getCursor();
                crs.moveToPosition(position);
                return crs.getLong(crs.getColumnIndex(DatabaseStrings.FIELD_ID));
            }
        };

        listview.setAdapter(adapter);
    }

    private OnClickListener clickListener = new View.OnClickListener() {



        @Override
        public void onClick(View v) {

            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (db.delete(id))
                adapter.changeCursor(db.query());



        }
    };

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    public void salva(View v) {
        EditText data = (EditText) findViewById(R.id.etxt_data);
        EditText in = (EditText) findViewById(R.id.etxt_in);
        EditText out = (EditText) findViewById(R.id.etxt_out);
        EditText oper = (EditText) findViewById(R.id.etxt_oper);
        if (data.length() >0 && oper.length() >0)
        {
            db.save(data.getEditableText().toString(), in.getEditableText().toString(), out.getEditableText().toString(), oper.getEditableText().toString());
            adapter.changeCursor(db.query());
        }
    }




    /*
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    */





}


