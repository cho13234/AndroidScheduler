package com.example.androidscheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	Button loginBtn;
	Button goBtn;
	RelativeLayout layout;
	public PrintWriter streamOut = null;
	public BufferedReader streamIn = null;
	public Socket cSocket = null;
	public chatThread cThread = null;
	public EditText idtv;
	public EditText pwtv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = (RelativeLayout) findViewById(R.id.layout);
		layout.setBackgroundResource(R.drawable.background);

		loginBtn = (Button) findViewById(R.id.loginbtn);
		goBtn = (Button) findViewById(R.id.gobtn);
		idtv = (EditText) findViewById(R.id.Idet);
		pwtv = (EditText) findViewById(R.id.Pwet);
		goBtn.setOnClickListener(this);
		cThread = new chatThread();
		cThread.start();
		loginBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.d("CHOI", "Send!");
				sendMessage(MakingProtocol.Login_info(getCurrentMacAddress(),
						idtv.getText().toString(), pwtv.getText().toString()));
			}
		});

	}

	public Handler mHandler = new Handler() { // 스레드에서 메세지를 받을 핸들러.
		public void handleMessage(Message msg) {
			String response = msg.obj.toString();
			String response2[] = response.split("/");
			if (response2[0].equals("login")) {
				if (response2[2].equals("ok")) {
					Log.d("Yoon", response2[2]);
					loginBtn.setText("Completed");
					loginBtn.setEnabled(false);
					goBtn.setEnabled(true);
				}
			}
		}
	};

	private void sendMessage(String MSG) {
		try {
			streamOut.println(MSG); // 서버에 메세지를 보내줍니다.
		} catch (Exception ex) {
			Log.d("SendError", ex.toString());
		}
	}

	public String getCurrentMacAddress() {
		String macAddress = "";
		boolean bIsWifiOff = false;

		WifiManager wfManager = (WifiManager) getSystemService(WIFI_SERVICE);
		if (!wfManager.isWifiEnabled()) {
			wfManager.setWifiEnabled(true);
			bIsWifiOff = true;
		}

		WifiInfo wfInfo = wfManager.getConnectionInfo();
		macAddress = wfInfo.getMacAddress();

		if (bIsWifiOff) {
			wfManager.setWifiEnabled(false);
			bIsWifiOff = false;
		}

		return macAddress;

	}

	class chatThread extends Thread {
		private boolean flag = false; // 스레드 유지(종료)용 플래그

		public void run() {
			try {
				cSocket = SocketManager.getSocket();// new Socket(server, port);
				// Log.d("Choi",server+"/"+port+"/"+user);
				streamOut = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(cSocket.getOutputStream())),
						true);// new PrintWriter(cSocket.getOutputStream(),
								// true); // 출력용 스트림
				streamIn = new BufferedReader(new InputStreamReader(
						cSocket.getInputStream())); // 입력용 스트림

				// sendMessage(getCurrentMacAddress()+"/"+pwtv.getText().toString());

				while (!flag) { // 플래그가 false일경우에 루프
					String msgs;
					Message msg = new Message();
					msg.what = 0;
					msgs = streamIn.readLine(); // 서버에서 올 메세지를 기다린다.
					msg.obj = msgs;

					mHandler.sendMessage(msg); // 핸들러로 메세지 전송

					/*
					 * if (msgs.equals("# [" + nickName + "]님이 나가셨습니다.")) { //
					 * 서버에서 온 메세지가 종료 메세지라면 flag = true; // 스레드 종료를 위해 플래그를
					 * true로 바꿈. msg = new Message(); msg.what = 1; // 종료메세지
					 * mHandler.sendMessage(msg); }
					 */
				}

			} catch (Exception e) {
				// logger(e.getMessage());
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("Choi", "Activity전환");
		// Intent intent = new Intent(this,Worklist.class);
		Intent intent = new Intent(this, SectorList.class);
		startActivity(intent);
	}

}
