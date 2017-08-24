package com.example.phonemiss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class Sms extends BroadcastReceiver {

	String receiveNumber = "";
    String receiveText = "";
    
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();        
        SmsMessage[] msgs = null;
         
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];            
            for (int i=0; i<msgs.length; i++){
                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
                receiveNumber = msgs[i].getOriginatingAddress();                     
                receiveText = msgs[i].getMessageBody().toString();
            }   
            Intent intent2 = new Intent(context, MainService.class);
            //intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent2.putExtra("receiveNumber", receiveNumber);
            intent2.putExtra("receiveText", receiveText);
            context.startService(intent2);
        }
    }
    public String getNumber (){
    	return receiveNumber;
    }
}