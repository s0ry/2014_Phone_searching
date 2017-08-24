package com.example.phonemiss;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.WindowManager;

public class SaveBatteryActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_battery);
		Bright_control();
		
	}
	
	public void Bright_control()
	{
		//ȭ�� ��� ����
		Settings.System.putInt(getContentResolver(), "screen_brightness", 10);
		
		//������Ŵ��� ��� ����
		WindowManager.LayoutParams myLayoutParameter = getWindow().getAttributes();
		myLayoutParameter.screenBrightness = (float) 0.1;
		getWindow().setAttributes(myLayoutParameter);
		finish();
	}
}
