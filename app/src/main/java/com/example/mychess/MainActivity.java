package com.example.mychess;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public MyCustomView myChessView;
    TextView myTextView;
    Dialog myDialog;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        myChessView = (MyCustomView) findViewById(R.id.chessView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        ;
    }

    //This part captures the response of the spinner and uses it to render a board of a different size between 6 and 16
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();


        MyCustomView.setSize(Integer.parseInt(item));
        myChessView.invalidate();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public class myView extends View {
        public myView(Context context) {
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
        }

    }


}
