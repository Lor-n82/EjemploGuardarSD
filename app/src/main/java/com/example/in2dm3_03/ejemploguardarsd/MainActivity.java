package com.example.in2dm3_03.ejemploguardarsd;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

 public class MainActivity extends Activity {
        EditText editText;
        //cte para bloque de lectura
        static final int READ_BLOCK_SIZE = 100;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            editText = (EditText) findViewById(R.id.editText);
        }

     public void onClickGuardar(View view) {
         String str = editText.getText().toString();
         try
         {
             //---Almacenamiento en SD Card ---
             File sdCard = Environment.getExternalStorageDirectory();
             File directory = new File (sdCard.getAbsolutePath() +  "/MyFiles");
             directory.mkdirs();
             File file = new File(directory, "textfile.txt");
             FileOutputStream fOut = new FileOutputStream(file);
             OutputStreamWriter osw = new
                     OutputStreamWriter(fOut);
             //---escribir la cadena en el archivo---
             osw.write(str);
             osw.flush();
             osw.close();
             //---mostrar mensaje de archivo guardado---
             Toast.makeText(getBaseContext(),
                     "Fichero guardado correctamente!",
                     Toast.LENGTH_SHORT).show();
             //---borrar el editext---
             editText.setText("");
         }
         catch (IOException ioe)
         {
             ioe.printStackTrace();
         }
     }


     public void onClickCargar(View view) {
         try
         {

             //--- leer de tarjeta SD ---
             File sdCard = Environment.getExternalStorageDirectory();
             File directory = new File (sdCard.getAbsolutePath() +
                     "/MyFiles");
             File file = new File(directory, "textfile.txt");
             FileInputStream fIn = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fIn);
             char[] inputBuffer = new char[READ_BLOCK_SIZE];
             String s = "";
             int charRead;  //número de caracteres leidos
             while ((charRead = isr.read(inputBuffer))>0)
             {
                 //---convertir en cadena---
                 String readString =
                         String.copyValueOf(inputBuffer, 0,
                                 charRead);
                 s += readString;
                 inputBuffer = new char[READ_BLOCK_SIZE];
             }
             //---establecer el EditText con el texto leido
             // read---
             editText.setText(s);
             Toast.makeText(getBaseContext(),
                     "Fichero cargado correctamente!",
                     Toast.LENGTH_SHORT).show();
         }
         catch (IOException ioe) {
             ioe.printStackTrace();
         }
     }


 }
