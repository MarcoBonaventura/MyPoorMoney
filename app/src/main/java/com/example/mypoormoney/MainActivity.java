package com.example.mypoormoney;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageButton;
import android.database.Cursor;
import android.content.Context;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private DbManager db = null;
    private CursorAdapter adapter;
    private ListView listview = null;
    private OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            int position = listview.getPositionForView(v);
            long id = adapter.getItemId(position);
            if (db.delete(id))
                adapter.changeCursor(db.query());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
