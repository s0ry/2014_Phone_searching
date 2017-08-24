package com.example.phonemiss;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;

public class MainService extends Service 
{
	// GPSTracker class
		//private SaveBattery SBattery;
		private GpsInfo gps;
		private int percent;
		private String password;
		private String passbattery;
		
		private boolean getBatteryAction = false;

		String number;
		String text;
	@Override
	public IBinder onBind(Intent intent)	//  사용안함. 
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		SharedPreferences pref = getSharedPreferences("Address", MODE_PRIVATE);
		SharedPreferences pref2 = getSharedPreferences("Battery", MODE_PRIVATE);
		password = pref.getString("password", "");
		passbattery = pref2.getString("passbattery", "");
		if(password.equals(""))
		{
			password = null;
		}
		if (passbattery.equals(""))
		{
			passbattery = null;
		}
		
		if(intent != null)
		{
			number = intent.getStringExtra("receiveNumber");
			if(number == null)
				number = "";
			text = intent.getStringExtra("receiveText");
			if(text == null)
				text = "";
			
			this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			if(text.equals(password) == true)
			{
				//SBattery = new SaveBattery(MainService.this);
				gps = new GpsInfo(MainService.this);
				
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run() 
					{
						while( gps.isLocationEnabled() == true && (gps.isGetLocation() == false) )
						{
							try
							{
								Thread.sleep(100);
							}
							catch(InterruptedException e)
							{
								
							}
						}
						/*
						while( getBatteryAction == false )
						{
							try
							{
								Thread.sleep(100);
							}
							catch(InterruptedException e)
							{
								
							}
						}*/
						String mAddressStr = "";
						if(gps.isLocationEnabled() == true)
							mAddressStr = gps.getAddress();
						else
							mAddressStr = "gps 꺼져있음";
						
						//mAddressStr += "\n" + "Now Battery" + percent + "%";
						
						//주소 메시지 보내기
						sendSMS(number, mAddressStr);
						
						Intent intent  = new Intent(MainService.this, SaveBatteryActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						//SBattery.Bright_control();
						
						MainService.this.stopSelf();
					}
				});
				thread.start();
			}	//주소보내기
			
			
			if(text.equals(passbattery) == true)
			{
				Thread thread = new Thread(new Runnable()
				{
					@Override
					public void run() 
					{
						while( getBatteryAction == false )
						{
							try
							{
								Thread.sleep(100);
							}
							catch(InterruptedException e)
							{
								
							}
						}
						String mAddressStr = "";
						mAddressStr += "Now Battery" + percent + "%";
						sendSMS(number, mAddressStr);
						MainService.this.stopSelf();
					}
				});
				thread.start();
			}	//배터리보내기
			
		}
		
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() 
	{
		this.unregisterReceiver(mBatInfoReceiver);
		super.onDestroy();
	}
	

	//배터리잔량
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent intent) 
		{
			// TODO Auto-generated method stub
			String action = intent.getAction();
			
			if( action.equals(Intent.ACTION_BATTERY_CHANGED) == true )
			{
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
				percent = (level * 100) / scale;
				getBatteryAction = true;
			}
		}
	};

	//문자보내기
	private void sendSMS(String sendNumber, String sendText)
	{        
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(sendNumber, null, sendText, null, null);        
	}
	
}
