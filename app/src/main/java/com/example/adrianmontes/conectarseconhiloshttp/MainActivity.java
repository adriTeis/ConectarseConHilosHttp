package com.example.adrianmontes.conectarseconhiloshttp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

//implementamos de la clase onclick listener, es otro modo de realizar en OnClick de un boton;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1;
    Button btn2;
    Button btn3;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//cojo los botones a traves de su id
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);

//y le indico que esta es la actividad donde voy a utilizar el onClick
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:
                for (int i = 0; i < 10; i++) {
                    Hilos();

                }
                break;

            case R.id.button2:
                break;

            case R.id.button3:
                //ahora ejecuto el metodo asinktask para poner la barra de carga
                EjemploAsinkTask ejemploAsinkTask = new EjemploAsinkTask();
                ejemploAsinkTask.execute();

                break;

            default:
                break;


        }

    }

    public void unSegundo() {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    void Hilos() {
        //ahora lanzo el hilo
        Thread hilo = new Thread(new Runnable() {

            @Override
            public void run() {
                unSegundo();
            }
        });

        hilo.start();
    }


    // se ejecuta antes de la ejecucion del hilo
    //los paremetros que recoje pone void por que el hilo no recive ningun resultado.
    //Integer es el tipo de parametros que se intercambian el backgrond(hilo) y el onprogressUpdate que
    //se ejecuta despues del hilo
    //boolean es el tipo de valor que devuelve con el return el hilo

    private class EjemploAsinkTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            super.onPreExecute();
            progressBar.setMax(100);
            progressBar.setProgress(0);
        }


        //se ejecuta despues del doInBackground, se coje el return del DoInBackGround
        @Override
        protected void onPostExecute(Boolean Resultado) {
            super.onPostExecute(Resultado);
            if (Resultado == true) {
                Toast.makeText(getBaseContext(), "tarea larga finalizada", Toast.LENGTH_SHORT);
                progressBar.setProgress(10);


            }


        }

        //esto es la actividad principal, es lo que recojo los datos del hilo
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress((values[0].intValue()));
        }

        //al cerrar la actividad, y el hilo sigue corriendo aqui digo lo que quiero que haga el hilo
        @Override
        protected void onCancelled() {
            super.onCancelled();



        }

        //es lo que se ejecuta en segundo plano, es como si fuera el metodo run.
        @Override
        protected Boolean doInBackground(Void... params) {

            for (int i = 1; i <= 10; i++) {
                unSegundo();
                //con este metodo le pasamos datos al hilo principal, en este caso como le pasamos un entero
                //en el metodo onpostexecute recojemos un integer, ese valor se puede cambiar, dependiendo lo que le enviemos
                publishProgress(i * 10);
                if (isCancelled()) {
                    break;
                }
            }

            return true;

        }
    }
}


