package com.example.laboratoriocooperativas;

import java.io.BufferedReader; 
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
	String vcuentas[] = new String[10000];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void buscar(View vista) {
		int sum=0, cts = 0;

		EditText pCampo = (EditText) findViewById(R.id.editText1);
		String qCampo = pCampo.getText().toString();
		String estado = Environment.getExternalStorageState();
		if (!estado.equals(Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "No hay acceso a MEMORIA SD", 1).show();
			finish();
		}
		File dir = Environment.getExternalStorageDirectory();

		File fcli = new File(dir.getAbsolutePath() + File.separator
				+ "clientes.csv");
		File fcta = new File(dir.getAbsolutePath() + File.separator
				+ "cuentas.csv");
		File fmov = new File(dir.getAbsolutePath() + File.separator
				+ "movimientos.csv");
		
		try {
			Scanner lector1 = new Scanner(new FileReader(fcli));
			Scanner lector2 = new Scanner(new FileReader(fcta));
			Scanner lector3 = new Scanner(new FileReader(fmov));
			// String res = ""; En caso de archivos normales
			// En caso de archivos extensos
			StringBuilder res = new StringBuilder();
			String linea;
			String campos[] = new String[6];
			Boolean encontrado = false;
			while (lector1.hasNext()) {
				linea = lector1.nextLine();
				campos = linea.split(";");
				if (campos[0].equals(qCampo)) {
					res.append(linea);
					encontrado = true;
					break;
				}
				// res.append(linea);
			}
			lector1.close();
			if (!encontrado) {
				Toast.makeText(this, "No existe CI", 1).show();

				EditText name = (EditText) findViewById(R.id.editText2);
				EditText ct = (EditText) findViewById(R.id.editText3);
				EditText sd = (EditText) findViewById(R.id.editText4);

				name.setText("");
				ct.setText("");
				sd.setText("");
			} else {
				// Toast.makeText(this, res, 1).show();
				EditText pDes = (EditText) findViewById(R.id.editText2);

				pDes.setText(campos[1]);

				StringBuilder res1 = new StringBuilder();
				String linea1;
				String campos1[] = new String[3];
				
				
				while (lector2.hasNext()) {
					linea1 = lector2.nextLine();
					campos1 = linea1.split(";");

					if (campos1[1].equals(campos[0])) {
						vcuentas[cts] = campos1[0];
						cts += 1;
					}
				}
				lector2.close();
				
			}
			String ss="";
			for (int i = 0; i < cts; i++) {
				ss+=vcuentas[i]+"\n";
				BufferedReader lector = new BufferedReader(new FileReader(fmov));
	        	String linea3;
	        	String campos2[] = new String[4];
	        	while((linea3=lector.readLine())!=null){
	        		campos2 = linea3.split(";");
	        		if (campos2[0].equals(vcuentas[i])) {
						//dd.append(linea3);
	        			
						sum += Integer.parseInt(campos2[1]);
					}
	        	}
	        	lector3.close();
			}
			if(cts>0){
				EditText cuenta = (EditText) findViewById(R.id.editText3);
				EditText saldo = (EditText) findViewById(R.id.editText4);
				cuenta.setText(cts + " cuenta/s encontrada/s\n"+ss);
				saldo.setText(sum + "");	
			}else{
				Toast.makeText(this, "No tiene cuentas", 2).show();
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void limpiar(View vista) {
		EditText ci = (EditText) findViewById(R.id.editText1);
		EditText name = (EditText) findViewById(R.id.editText2);
		EditText ct = (EditText) findViewById(R.id.editText3);
		EditText sd = (EditText) findViewById(R.id.editText4);

		ci.setText("");
		name.setText("");
		ct.setText("");
		sd.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
