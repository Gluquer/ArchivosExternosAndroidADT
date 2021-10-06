package com.example.archivosexternos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String estado= Environment.getExternalStorageState();
        if(!estado.equals(Environment.MEDIA_MOUNTED)){
        	Toast.makeText(this, "No hay memoria", 1).show();
        }
        File dir = Environment.getExternalStorageDirectory();
        File pt = new File(dir.getAbsolutePath()+File.separator+"bolivia.txt");
        //Toast.makeText(this, dir.getAbsolutePath(), 1).show();
        try{
        	BufferedReader lector = new BufferedReader(new FileReader(pt));
        	String res="";
        	String linea;
        	while((linea=lector.readLine())!=null){
        		res +=linea+"\n";
        	}
        	lector.close();
        	TextView tv = (TextView) findViewById(R.id.textView1);
        	tv.setText(res);
        }
        catch (IOException e){
        	Toast.makeText(this, e.getMessage(), 1).show();
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}




