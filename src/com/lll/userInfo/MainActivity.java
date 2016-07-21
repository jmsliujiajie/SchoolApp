package com.lll.userInfo;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.lll.utisl.WebService;
import com.lll.utisl.WsPubSecur;

public class MainActivity extends Activity implements OnClickListener {
    //20160721 
	Button bDoLogin = null;
	TextView tUserName = null;
	TextView tPasswd = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initParam();
		
		bDoLogin.setOnClickListener(this);
	}
	
	//初始化页面参数
	private void initParam(){
		bDoLogin = (Button) findViewById(R.id.button1);
		tUserName = (TextView) findViewById(R.id.textUserName);
		tPasswd = (TextView) findViewById(R.id.textPasswd);
	}

	@Override
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.button1:
			if(doLogin()){
				Intent intent = new Intent(MainActivity.this, LoginSucc.class);
				intent.putExtra("isUser", "true");
				startActivity(intent);
			}else{
				Intent intent = new Intent(MainActivity.this, LoginFalse.class);
				intent.putExtra("isUser", "false");
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}
	
	private boolean doLogin(){
		boolean result = true;
		String userName = tUserName.getText().toString();
		String password = tPasswd.getText().toString();
		
		password = WsPubSecur.encrypt(password);
		
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("LOGIN_ID", userName);
			jsonObj.put("PASSWD", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(jsonObj.toString());
		String isLogin = WebService.callWebService(WebService.NS_doLogin,WebService.SN_doLogin,"doLogin", jsonObj.toString());
		
		System.out.println(isLogin);
		if("success".equals(isLogin)){
			result = true;
		}else{
			result = false;
		}
		return result;
	}

}
