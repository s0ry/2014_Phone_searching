package com.example.phonemiss;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** GPS 샘플 */
public class MainActivity extends Activity
{

	private Button btnCheck;
	private TextView TextView1;
	private EditText Editpassword;
	 
	private Button btnButton1;
	private TextView TextView2;
	private EditText EditBattery;
	
 
	private String pass_word;
	private String pass_battery;

	String number;
	String text;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnCheck = (Button) findViewById(R.id.B_check);
		TextView1 = (TextView) findViewById(R.id.TextView1);
		Editpassword = (EditText) findViewById(R.id.Password);

		btnButton1 = (Button) findViewById(R.id.Button01);
		TextView2 = (TextView) findViewById(R.id.TextView2);
		EditBattery = (EditText) findViewById(R.id.EditBattery);
		
		SharedPreferences pref = getSharedPreferences("Address", MODE_PRIVATE);
		SharedPreferences pref2 = getSharedPreferences("Battery", MODE_PRIVATE);
		
		pass_word = pref.getString("password", "");
		pass_battery = pref2.getString("passbattery", "");
		Editpassword.setText(pass_word);
		EditBattery.setText(pass_battery);
		
		if(pass_word.equals(""))
		{
			pass_word = null;
		}
		btnCheck.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Editpassword.getText().toString().length() == 0 ) {
					TextView1.setText("다시입력하세요.");
				} else {
					pass_word = Editpassword.getText().toString();
					SharedPreferences pref = getSharedPreferences("Address", MODE_PRIVATE);
					Editor editor = pref.edit();
					editor.putString("password", pass_word);
					editor.commit();
					TextView1.setText("설정되었습니다.");
				} 
			}
		});	//주소 패스워드설정
		
		if(pass_battery.equals(""))
		{
			pass_battery = null;
		}
		btnButton1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (EditBattery.getText().toString().length() == 0 ) {
					TextView2.setText("다시입력하세요.");
				} else {
					pass_battery = EditBattery.getText().toString();
					SharedPreferences pref2 = getSharedPreferences("Battery", MODE_PRIVATE);
					Editor editor2 = pref2.edit();
					editor2.putString("passbattery", pass_battery);
					editor2.commit();
					TextView2.setText("설정되었습니다.");
				} 
			}
		});	//배터리 패스워드설정
	}	//Create
}