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
		goBtn.setOnClickListener(this);/*
										 * new OnClickListener() {
										 * 
										 * public void onClick(View v) { Intent
										 * intent = new
										 * Intent(MainActivity.this,
										 * Worklist.class);
										 * startActivity(intent); } });
										 */
		cThread = new chatThread();
		cThread.start();
		loginBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.d("CHOI", "Send!");
				sendMessage(MakingProtocol.Login_info(getCurrentMacAddress(),
						idtv.getText().toString(), pwtv.getText().toString()));
				// loginBtn.setText("Login~ing");
				// loginBtn.setEnabled(false);
				// goBtn.setEnabled(true);

				// goBtn.setEnabled(true);
				/*
				 * cThread = new chatThread(); cThread.start();
				 */
				// Intent intent = new Intent(MainActivity.this,Worklist.class);
			}
		});

	}

	public Handler mHandler = new Handler() { // �����忡�� �޼����� ���� �ڵ鷯.
		public void handleMessage(Message msg) {
			// Log.d("Receive Message","msg = "+msg.obj.toString());
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
			streamOut.println(MSG); // ������ �޼����� �����ݴϴ�.
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
		private boolean flag = false; // ������ ����(����)�� �÷���

		public void run() {
			try {
				cSocket = SocketManager.getSocket();// new Socket(server, port);
				// Log.d("Choi",server+"/"+port+"/"+user);
				streamOut = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(cSocket.getOutputStream())),
						true);// new PrintWriter(cSocket.getOutputStream(),
								// true); // ��¿� ��Ʈ��
				streamIn = new BufferedReader(new InputStreamReader(
						cSocket.getInputStream())); // �Է¿� ��Ʈ��

				// sendMessage(getCurrentMacAddress()+"/"+pwtv.getText().toString());

				while (!flag) { // �÷��װ� false�ϰ�쿡 ����
					String msgs;
					Message msg = new Message();
					msg.what = 0;
					msgs = streamIn.readLine(); // �������� �� �޼����� ��ٸ���.
					msg.obj = msgs;

					mHandler.sendMessage(msg); // �ڵ鷯�� �޼��� ����

					/*
					 * if (msgs.equals("# [" + nickName + "]���� �����̽��ϴ�.")) { //
					 * �������� �� �޼����� ���� �޼������ flag = true; // ������ ���Ḧ ���� �÷��׸�
					 * true�� �ٲ�. msg = new Message(); msg.what = 1; // ����޼���
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
		Log.d("Choi", "Activity��ȯ");
		// Intent intent = new Intent(this,Worklist.class);
		Intent intent = new Intent(this, SectorList.class);
		startActivity(intent);
	}

}
