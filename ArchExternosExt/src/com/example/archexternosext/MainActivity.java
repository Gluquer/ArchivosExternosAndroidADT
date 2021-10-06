package com.example.archexternosext;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void buscar (View vista){
    	EditText pCampo = (EditText) findViewById(R.id.editText1);
    	String qCampo = pCampo.getText().toString();
    	String estado = Environment.getExternalStorageState();
    	if(!estado.equals(Environment.MEDIA_MOUNTED)){
    		Toast.makeText(this, "No hay acceso a MEMORIA SD", 1).show();
    		finish();
    	}
    	File dir = Environment.getExternalStorageDirectory();
    	File p = new File(dir.getAbsolutePath()+File.separator+"bdproductos.txt");
    	try{
    		Scanner lector = new Scanner(new FileReader(p));
    		//String res = ""; En caso de archivos normales
    		//En caso de archivos extensos
    		StringBuilder res = new StringBuilder();
    		String linea;
    		String campos[] = new String[6];
    		Boolean encontrado=false;
    		while(lector.hasNext()){
    			linea=lector.nextLine();
    			campos = linea.split(";");
    			if(campos[0].equals(qCampo)){
    				res.append(linea);
    				encontrado=true;
    				break;
    			}
    			//res.append(linea);
    		}
    		lector.close();
    		if(!encontrado){
    			Toast.makeText(this, "No existe Codigo", 1).show();
    		}else{
    			//Toast.makeText(this, res, 1).show();
    			EditText pDes = (EditText) findViewById(R.id.editText2);
    			EditText pUni = (EditText) findViewById(R.id.editText3);
    			EditText pLin = (EditText) findViewById(R.id.editText4);
    			EditText pExs = (EditText) findViewById(R.id.editText5);
    			pDes.setText(campos[1]);
    			pUni.setText(campos[2]);
    			pLin.setText(campos[4]);
    			pExs.setText(campos[5]);
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    public void limpiar(View vista){
    	EditText pCod = (EditText) findViewById(R.id.editText1);
    	EditText pDes = (EditText) findViewById(R.id.editText2);
		EditText pUni = (EditText) findViewById(R.id.editText3);
		EditText pLin = (EditText) findViewById(R.id.editText4);
		EditText pExs = (EditText) findViewById(R.id.editText5);
		pCod.setText("");
		pDes.setText("");
		pUni.setText("");
		pLin.setText("");
		pExs.setText("");

    }
    public void resumen (View vista){
    	EditText pLinea = (EditText) findViewById(R.id.editText1);
    	String qLinea = pLinea.getText().toString();
    	String estado = Environment.getExternalStorageState();
    	int TotalExistencia =0;
    	if(!estado.equals(Environment.MEDIA_MOUNTED)){
    		Toast.makeText(this, "No hay acceso a MEMORIA SD", 1).show();
    		finish();
    	}
    	File dir = Environment.getExternalStorageDirectory();
    	File p = new File(dir.getAbsolutePath()+File.separator+"bdproductos.txt");
    	try{
    		Scanner lector = new Scanner(new FileReader(p));
    		String linea;
    		String campos[] = new String[6];
    		Boolean encontrado=false;
    		while(lector.hasNext()){
    			linea=lector.nextLine();
    			campos = linea.split(";");
    			if(campos[4].equals(qLinea)){
    				encontrado=true;
    				int existencia = Integer.parseInt(campos[5]);
    				TotalExistencia = TotalExistencia + existencia;
    			}
    		}
    		lector.close();
    		if(!encontrado){
    			Toast.makeText(this, "No existe Codigo de Linea", 1).show();
    		}else{
    			//Toast.makeText(this, res, 1).show();
    			EditText pDes = (EditText) findViewById(R.id.editText2);
    			EditText pUni = (EditText) findViewById(R.id.editText3);
    			EditText pLin = (EditText) findViewById(R.id.editText4);
    			EditText pExs = (EditText) findViewById(R.id.editText5);
    			pDes.setText("");
    			pUni.setText("");
    			pLin.setText(qLinea);
    			pExs.setText(TotalExistencia+"");
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
