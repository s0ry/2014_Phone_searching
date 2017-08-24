package com.example.phonemiss;

import android.app.Activity;
import android.provider.Settings;
import android.view.WindowManager;

public class SaveBattery extends Activity {
	
	public SaveBattery(MainService mainService) {
		// TODO Auto-generated constructor stub
		
	}

	public void Bright_control()
	{
		//화면 밝기 조절
		Settings.System.putInt(getContentResolver(), "screen_brightness", 30);
		
		//윈도우매니저 밝기 조절
		WindowManager.LayoutParams myLayoutParameter = getWindow().getAttributes();
		myLayoutParameter.screenBrightness = (float) 0.1;
		getWindow().setAttributes(myLayoutParameter);
	}
}
