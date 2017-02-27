package com.keepsake.cordova.plugin;

import org.apache.cordova.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import android.*;
// import android.content.Intent;
import rfid.ivrjacku1.*;
import java.util.*;

public class UHFReader extends CordovaPlugin implements IvrJackAdapter {

	private Context andContext;
	private IvrJackService ivrjacku1;
	private CallbackContext callbackContext;
	// private static final String READ_INTENT = "keepsake.intent.action.READ";
	// public static final int READ_CODE = 0;

	String [] permissions = { Manifest.permission.RECORD_AUDIO };

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		if (hasPermisssion()) {
			PluginResult r = new PluginResult(PluginResult.Status.OK);
			callbackContext.sendPluginResult(r);
		} else {
			PermissionHelper.requestPermissions(this, 0, permissions);
		}
	}

	public boolean hasPermisssion() {
		for (String p : permissions) {
			if (!PermissionHelper.hasPermission(this, p)) {
				return false;
			}
		}
		return true;
	}

	private IvrJackService getIvrJackService() {
		if (ivrjacku1 == null) {
			ivrjacku1 = new IvrJackService();
		}
		return ivrjacku1;
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.andContext = this.cordova.getActivity().getApplicationContext(); 
		getIvrJackService().open(andContext, this);

		System.out.println("APPMSG - Execute");

		if (action.equals("read")) {
			System.out.println("APPMSG - Read in Execute");
			this.readTags();
			return true;
		} 

		return false;
	}

	public void readTags() {
		System.out.println("APPMSG - Read Status Change");
    }

	@Override
	public void onStatusChange(IvrJackStatus var1) {
		switch (var1) {
			case ijsDetecting: 
				System.out.println("APPMSG - Is ijsDetecting");				
				break;
				
			case ijsRecognized:
					System.out.println("APPMSG - IsRecognized");
					int res = getIvrJackService().readEPC(false);
					System.out.println("APPMSG - Read: " + res);
					break;
				
			case ijsUnRecognized:
				System.out.println("APPMSG - ijsUnRecognized");
				break;
		}
	}

	@Override
	public void onConnect(String var1) {
		System.out.println("APPMSG - onConnect");
	}

	@Override
	public void onDisconnect(){
		System.out.println("APPMSG - onDisConnect");
	}

	public void onInventory(String var1){
		System.out.println("APPMSG - onInventory");
	}
}