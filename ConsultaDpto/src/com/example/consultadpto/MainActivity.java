package com.example.consultadpto;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ProgressDialog pg;
	ProgressBar pb;
	int ps = 0;
	Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb =(ProgressBar)findViewById(R.id.progressBar1);
    	pb.setVisibility(View.VISIBLE);
		TextView tv = (TextView) findViewById(R.id.textView3);
		
    }
    public void procesar(View vista){
    	procesando();
    	Button bProcesar = (Button) findViewById(R.id.button1);
		bProcesar.setEnabled(false);
		String res = "";
		String ctas = "";
		String carnets[] = new String[300];
		EditText pDep=(EditText) findViewById(R.id.editText1);
		EditText psal=(EditText) findViewById(R.id.editText2);
		TextView tv = (TextView) findViewById(R.id.textView3);
		tv.setText("Procensado...");
		String qDep = pDep.getText().toString();
		File dir=Environment.getExternalStorageDirectory();
		File pArch = new File(dir.getAbsolutePath()+File.separator+"clientes.csv");
		Toast.makeText(this, "Filtrando carnets...", 0).show();
		try{
			Scanner lector = new Scanner(new FileReader(pArch));
			res = "";
			String linea;
			int c=0;
			while(lector.hasNext()){
				linea = lector.nextLine();
				//El campo 3 es DEPTO
				String campos[] = linea.split(";");
				if(campos[3].equals(qDep)){
					c++;
					res=res+campos[0]+";";
				}
			}
			lector.close();
			//tv.setText(res);
			//carnets = res.split(";");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		dir=Environment.getExternalStorageDirectory();
		pArch = new File(dir.getAbsolutePath()+File.separator+"cuentas.csv");
		Toast.makeText(this, "Filtrando cuentas...", 0).show();
		try{
			Scanner lector = new Scanner(new FileReader(pArch));
			String linea;
			int c=0;
			while(lector.hasNext()){
				linea = lector.nextLine();
				//El campo 3 es DEPTO
				String campos[] = linea.split(";");
				if(res.contains(campos[1])){
					c++;
					ctas=ctas+campos[0]+";";
//					p1.setProgress(c);
				}
			}
			lector.close();
			//tv.setText(ctas);
			//carnets = res.split(";");
		}catch(IOException e){
			e.printStackTrace();
		}
		
		dir=Environment.getExternalStorageDirectory();
		pArch = new File(dir.getAbsolutePath()+File.separator+"movimientos.csv");
		Toast.makeText(this, "Acumulando Saldos...", 0).show();
		try{
			Scanner lector = new Scanner(new FileReader(pArch));
			String linea;
			int saldo=0;
			while(lector.hasNext()){
				linea = lector.nextLine();
				//El campo 0 es la Cuenta
				String campos[] = linea.split(";");
				if(ctas.contains(campos[0])){
					int monto = Integer.parseInt(campos[1]);
					saldo = saldo +monto;
//					p1.setProgress(saldo);
				}
			}
			lector.close();
			tv.setText("Finalizo");
			psal.setText(saldo+"");
			//carnets = res.split(";");
		}catch(IOException e){
			e.printStackTrace();
		}
		bProcesar.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void procesando(){
    	pb =(ProgressBar)findViewById(R.id.progressBar1);
    	pb.setVisibility(View.VISIBLE);
    	pb.setProgress(0);
    	pb.setMax(100);
    	ps=0;
    	
    	new Thread(new Runnable(){
    		@Override
    		public void run(){
    			while(ps<100)
    			{
    				ps+=(int)100/20;
    				try{
    					Thread.sleep(1000);
    				}catch(InterruptedException e){
    					e.printStackTrace();
    				}
    				handler.post(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							pb.setProgress(ps);
						}
					});
    			}
    		}
    	}).start();
    }
    public void limpiar(View vista){
    	pb =(ProgressBar)findViewById(R.id.progressBar1);
    	pb.setVisibility(View.VISIBLE);
    	pb.setProgress(0);
		EditText pDep=(EditText) findViewById(R.id.editText1);
		EditText psal=(EditText) findViewById(R.id.editText2);
		TextView tv = (TextView) findViewById(R.id.textView3);
		tv.setText("");
		pDep.setText("");
		psal.setText("");
		
    }
}
