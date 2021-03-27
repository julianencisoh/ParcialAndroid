package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btnrojo;
    private Button btnamarillo;
    private Button btnverde;
    private Button btnvista;
    private Button btnconfirmar;
    private EditText casillaposx;
    private EditText casillaposy;
    private EditText casillarecordatorio;
    private String color;
    private BufferedWriter bwriter;
    private String estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnrojo = findViewById(R.id.btnrojo);
        btnamarillo = findViewById(R.id.btnamarillo);
        btnverde = findViewById(R.id.btnverde);
        btnvista = findViewById(R.id.btnvista);
        btnconfirmar = findViewById(R.id.btnconfirmar);
        casillaposx = findViewById(R.id.casillaposx);
        casillaposy = findViewById(R.id.casillaposy);
        casillarecordatorio = findViewById(R.id.casillarecordatorio);


        btnrojo.setOnClickListener(
                v -> {
                    color = "rojo";
                }
        );

        btnamarillo.setOnClickListener(
                v -> {
                    color = "amarillo";
                }
        );

        btnverde.setOnClickListener(
                v -> {
                    color = "verde";
                }
        );

        btnvista.setOnClickListener(
                v -> {

                    estado = "viendo";
                    String posx = casillaposx.getText().toString();
                    String posy = casillaposy.getText().toString();
                    String textoRecordatorio = casillarecordatorio.getText().toString();

                    enviarAEclipse(color, posx, posy, estado, textoRecordatorio);
                }
        );

        btnconfirmar.setOnClickListener(
                v -> {

                    estado = "confirmado";
                    String posx = casillaposx.getText().toString();
                    String posy = casillaposy.getText().toString();
                    String textoRecordatorio = casillarecordatorio.getText().toString();

                    enviarAEclipse(color, posx, posy, estado, textoRecordatorio);

                }
        );

        new Thread(
                () -> {
                    try {
                        Socket socket = new Socket("10.0.2.2",5000);
                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        bwriter = new BufferedWriter(osw);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
        ).start();








    }

    private void enviarAEclipse(String color, String posx, String posy, String estado, String textoRecordatorio) {

        new Thread(
                () -> {

                    Gson gson = new Gson();
                    Recordatorios recordatorio  = new Recordatorios(color, posx,posy,estado,textoRecordatorio);
                    String datosrecordatorio = gson.toJson(recordatorio);

                    try {
                        bwriter.write(datosrecordatorio + "\n");
                        bwriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        ).start();

    }


}