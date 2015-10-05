package com.practica3.practica3;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText vistaPrecio;
    private EditText vistaAhorro;
    private EditText vistaPlazo;
    private EditText vistaEuribor;
    private EditText vistaDiferencial;
    private TextView vistaMes;
    private TextView vistaTotal;

    TextWatcher myTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calcula();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vistaPrecio = (EditText) findViewById(R.id.et1);
        vistaAhorro = (EditText) findViewById(R.id.et2);
        vistaPlazo = (EditText) findViewById(R.id.et3);
        vistaEuribor = (EditText) findViewById(R.id.et4);
        vistaDiferencial = (EditText) findViewById(R.id.et5);
        vistaMes = (TextView) findViewById(R.id.tv1);
        vistaTotal = (TextView) findViewById(R.id.tv2);

        vistaPrecio.addTextChangedListener(myTextWatcher);
        vistaAhorro.addTextChangedListener(myTextWatcher);
        vistaPlazo.addTextChangedListener(myTextWatcher);
        vistaEuribor.addTextChangedListener(myTextWatcher);
        vistaDiferencial.addTextChangedListener(myTextWatcher);

        calcula();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClick(View view)
    {
        calcula();
    }

    private void calcula() {
        Calculo calculo = new Calculo().invoke();
        double cuota = calculo.getCuota();
        double total = calculo.getTotal();

        mostrar(cuota, total);
    }

    private void mostrar(double cuota, double total) {
        vistaMes.setText("Mes: " + String.valueOf(cuota) + "€");
        vistaTotal.setText("Total: " + String.valueOf(total) + "€");
    }

    private class Calculo {
        private double cuota;
        private double total;

        public double getCuota() {
            return cuota;
        }

        public double getTotal() {
            return total;
        }

        public Calculo invoke() {
            double precio = Double.parseDouble(vistaPrecio.getText().toString());
            double ahorro = Double.parseDouble(vistaAhorro.getText().toString());
            double plazo = Double.parseDouble(vistaPlazo.getText().toString());
            double euribor = Double.parseDouble(vistaEuribor.getText().toString());
            double diferencial = Double.parseDouble(vistaDiferencial.getText().toString());

            double intereses = euribor+diferencial;
            cuota = ((precio - ahorro) * intereses) * (100 * (1 - (Math.pow((1 + ((intereses) / 100)), -plazo))));
            total = cuota * 12 * plazo;
            return this;
        }
    }
}
