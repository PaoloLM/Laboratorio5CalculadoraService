package com.desarrollo.laboratorio5calculadoraservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean opcSum = false;
    private boolean opcRes = false;
    private boolean opcMul = false;
    private boolean opcDiv = false;
    private boolean opcCal = false;

    private String indicador = "";

    private int num1 = 0;
    private int num2 = 0;
    private double resultado = 0;

    Button btn_calcular;
    Button btn_suma;
    Button btn_resta;
    Button btn_division;
    Button btn_multiplicacion;

    EditText etnum1;
    EditText etnum2;

    TextView tvresultado;
    TextView tvprevia;

    Button btncomando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(getApplicationContext(),SoundService.class));


        //BOTON CALCULAR
        btn_calcular = (Button)findViewById(R.id.btn_calcular);
        btn_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvresultado = (TextView) findViewById(R.id.ver_resultado);
                tvprevia = (TextView) findViewById(R.id.ver_previa);

                if (VerificarTextos() == false) {
                    Toast.makeText(getApplicationContext(), "No hay numeros", Toast.LENGTH_SHORT).show();
                } else {
                    tvresultado.setText(String.valueOf(resultado));
                    tvprevia.setText(num1 + " " + indicador + " " + num2);
                    HabilitarControles();
                }
            }
        });

        //BOTON SUMA
        btn_suma = (Button)findViewById(R.id.btn_suma);
        btn_suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarTextos() == true) {
                    Resolucion(1);
                } else {
                    Toast.makeText(getApplicationContext(), "No hay numeros", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON RESTA
        btn_resta = (Button)findViewById(R.id.btn_resta);
        btn_resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarTextos() == true) {
                    Resolucion(2);
                } else {
                    Toast.makeText(getApplicationContext(), "No hay numeros", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON DIVISION
        btn_division = (Button)findViewById(R.id.btn_division);
        btn_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarTextos() == true) {
                    Resolucion(3);
                } else {
                    Toast.makeText(getApplicationContext(), "No hay numeros", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BOTON MULTIPLICACION
        btn_multiplicacion = (Button)findViewById(R.id.btn_multiplicacion);
        btn_multiplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VerificarTextos() == true) {
                    Resolucion(4);
                } else {
                    Toast.makeText(getApplicationContext(), "No hay numeros", Toast.LENGTH_SHORT).show();
                }
            }
        });

        VerificarEstado();
    }

    private boolean VerificarTextos() {
        etnum1 = findViewById(R.id.num_1);
        etnum2 = findViewById(R.id.num_2);

        String e1 = etnum1.getText().toString();
        String e2 = etnum2.getText().toString();

        if (e1.equals("") && e2.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void Resolucion(int opc) {
        VerificarEstado();
        etnum1 = (EditText) findViewById(R.id.num_1);
        etnum2 = (EditText) findViewById(R.id.num_2);
        num1 = Integer.parseInt(etnum1.getText().toString());
        num2 = Integer.parseInt(etnum2.getText().toString());

        switch (opc) {
            case 1: //CASO SUMA
                btncomando = (Button) findViewById(R.id.btn_suma);
                indicador = btncomando.getText().toString();
                resultado = num1 + num2;
                Toast.makeText(getApplicationContext(), "SUMA", Toast.LENGTH_SHORT).show();
                break;

            case 2: //CASO RESTA
                btncomando = (Button) findViewById(R.id.btn_resta);
                indicador = btncomando.getText().toString();
                resultado = num1 - num2;
                Toast.makeText(getApplicationContext(), "RESTA", Toast.LENGTH_SHORT).show();
                break;

            case 3: //CASO DIVISION
                btncomando = (Button) findViewById(R.id.btn_division);
                indicador = btncomando.getText().toString();

                double dnum1 = Double.parseDouble(etnum1.getText().toString());
                double dnum2 = Double.parseDouble(etnum2.getText().toString());
                
                resultado = dnum1 / dnum2;
                Toast.makeText(getApplicationContext(), "DIVISION", Toast.LENGTH_SHORT).show();
                break;

            case 4: //CASO MULTIPLICACION
                btncomando = (Button) findViewById(R.id.btn_multiplicacion);
                indicador = btncomando.getText().toString();
                resultado = num1 * num2;
                Toast.makeText(getApplicationContext(), "MULTIPLICACION", Toast.LENGTH_SHORT).show();
                break;

            default:
                Toast.makeText(getApplicationContext(), "Error en la seleccion", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void HabilitarControles() {
        opcSum = true; opcRes = true; opcMul = true; opcDiv = true;
    }

    private void VerificarEstado() {
        if (opcSum == true && opcRes == true && opcMul == true && opcDiv == true) {
            opcCal = true;
        } else {
            opcCal = false;
            opcSum = true;
            opcRes = true;
            opcMul = true;
            opcDiv = true;
        }
        InhabilitarBotones();
    }

    private void InhabilitarBotones() {
        btn_suma.setEnabled(opcSum);
        btn_resta.setEnabled(opcRes);
        btn_multiplicacion.setEnabled(opcMul);
        btn_division.setEnabled(opcDiv);
        btn_calcular.setEnabled(opcCal);
    }

    @Override
    protected void onPause() {
        super.onPause();
        startService(new Intent(getApplicationContext(),SoundService.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(getApplicationContext(),SoundService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getApplicationContext(),SoundService.class));
    }
}
