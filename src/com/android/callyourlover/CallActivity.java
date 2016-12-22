package com.android.callyourlover;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import com.android.callyourlover.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * @version 2.0
 * @author EZSUN
 *
 */
public class CallActivity extends Activity implements OnClickListener
{
	private Button yesBtn,noBtn,setNumberBtn;
	private TextView phoneNumberTV;
	private String phoneNumberStr;
	private int callTimes;
	private int talkTime;
	private boolean isAutoExit;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setNumberBtn = (Button)findViewById(R.id.setNumber);
        setNumberBtn.setOnClickListener(this);
        
        phoneNumberTV = (TextView)findViewById(R.id.phoneNumberTV);
        
        getNumberByFile();
    }

    public void callPhone()
    {
    	Intent in = new Intent();
		
		in.setAction(Intent.ACTION_CALL);
		
		in.setData(Uri.parse("tel:"+phoneNumberStr));
		
		startActivity(in);
		
		callTimes++;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.call, menu);
        return true;
    }
    
    public void getNumberByFile()
    {
		try 
		{
			InputStream in = getApplicationContext().openFileInput("myNumber.txt");
			
			Scanner sc = new Scanner(in);
//			String str = null;
			
			String str = sc.nextLine();	
			
			phoneNumberStr = str;
			phoneNumberTV.setText("您指定的电话号码为："+str);
			
			if(str!=null && !str.equals(""))
			{
				
				callPhone();
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
    public void saveCallTimesInFile()
    {
    	
    }
    public void saveTalkTimeInFile()
    {
    	
    }
    public void isAutoExit()
    {
    	
    }
    public void saveNumberInFile(String number) 
    {
		String str = number;
		if (null != str && !"".equals(str)) {
			try {
				OutputStream os = getApplicationContext().openFileOutput("myNumber.txt", MODE_PRIVATE);

				os.write(str.getBytes());
				phoneNumberTV.setText("您指定的电话号码为："+str);
				os.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}

	}
    private void inputNumberViewDialog() 
    {
		final EditText phoneNumberEditText = new EditText(this);
		new Builder(this).setTitle(R.string.title).setView(phoneNumberEditText)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() 
				{
					@Override
					public void onClick(DialogInterface dialog, int which) {

						String str = phoneNumberEditText.getText().toString();
						
						saveNumberInFile(str);
						
						phoneNumberTV.setText("您指定的电话号码为："+str);
						
						Toast.makeText(getApplicationContext(), str,Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton(R.string.no,new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						
					}
				}).create().show();
	}



	@Override
	public void onClick(View v) 
	{
		int id = v.getId();
		switch(id)
		{
			case R.id.setNumber:
			{
				inputNumberViewDialog() ;
				
				break;
			}
			default:
				break;
		}
		
	}
    
}