package com.example.laboratorioexistencias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int sum=0;
	int aux;
	String lp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void procesar(View vista){
    	File dir = Environment.getExternalStorageDirectory();
        File pt = new File(dir.getAbsolutePath()+File.separator+"bdproductos.txt");
        //Toast.makeText(this, dir.getAbsolutePath(), 1).show();
        EditText ed=(EditText) findViewById(R.id.editText1); 
        try{
        	BufferedReader lector = new BufferedReader(new FileReader(pt));
        	String linea;
        	while((linea=lector.readLine())!=null){
        		String arr[] = linea.split(";", 6);
        		String lproduc = arr[4];
        		String existecia = arr[5];
        		//Toast.makeText(this,existecia, 1).show();
        		if(lproduc.equals(ed.getText().toString()))
        		{
        			aux = Integer.parseInt(existecia);
        			sum=sum+aux;
        			lp=lproduc;
        		}
        	}
        	
        	lector.close();
        	TextView tv = (TextView) findViewById(R.id.textView1);
        	TextView tv2 = (TextView) findViewById(R.id.textView2);
        	String gg=Integer.toString(sum);
        	tv.setText(gg);
        	tv2.setText(lp);
        }
        catch (IOException e){
        	Toast.makeText(this, e.getMessage(), 1).show();
        }
    }
    public void limpiar(View vista){
    	sum=0;
    	aux=0;
    	lp="";
    	TextView tv = (TextView) findViewById(R.id.textView1);
    	TextView tv2 = (TextView) findViewById(R.id.textView2);
    	tv.setText("");
    	tv2.setText("");
    	EditText ed=(EditText) findViewById(R.id.editText1);
    	ed.setText("");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
