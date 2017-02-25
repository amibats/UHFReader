package com.keepsake.cordova.plugin;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import rfid.ivrjacku1.*;

public class UHFReader extends CordovaPlugin implements IvrJackAdapter {

	// CordovaInterface mCordova; 

	// @Override 
	// public void initialize(CordovaInterface cordova, CordovaWebView webView) { 
	// 	super.initialize(cordova, webView); 
	// 	mCordova = cordova; 
	// }

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("read")) {
			this.readTags(args, callbackContext);
			return true;
		} else if (action.equals("write")) {
			this.writeTags(args, callbackContext);
			return true;
		}
		return false;
	}

	private void readTags(JSONArray args, CallbackContext callbackContext) {
		try{
			Context context=this.cordova.getActivity().getApplicationContext(); 
			IvrJackService ivrjacku1 = new IvrJackService();
			ivrjacku1.open(context, this);
			int readRes = ivrjacku1.readEPC(true);
			System.out.println("APPMSG - Read result: "+readRes);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private void writeTags(JSONArray args, CallbackContext callbackContext) {

	}

	@Override
	public void onConnect(String var1){
		System.out.println("APPMSG - onConnect");
	}

	@Override
    public void onDisconnect(){
    	System.out.println("APPMSG - onDisConnect");
    }

    @Override
    public void onStatusChange(IvrJackStatus var1){
    	switch (var1) {
			case ijsDetecting: 
				System.out.println("APPMSG - Is ijsDetecting");
				break;
				
			case ijsRecognized:
				System.out.println("APPMSG - Is ijsRecognized");
				break;
				
			case ijsUnRecognized:
				System.out.println("APPMSG - ijsUnRecognized");
				break;
		}
    }

    @Override
    public void onInventory(String var1){
    	System.out.println("APPMSG - onInventory");
    }
}