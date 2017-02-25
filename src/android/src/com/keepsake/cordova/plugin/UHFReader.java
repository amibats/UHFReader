package com.keepsake.cordova.plugin;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
//		IvrJackService.readEPC();
		try{
			IvrJackService ivrjacku1=new IvrJackService();
			int readRes=ivrjacku1.readEPC(true);
			System.out.println("Read result: "+readRes);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void writeTags(JSONArray args, CallbackContext callbackContext) {

	}

	public void onConnect(String var1){
		System.out.println("onConnect");
	}

    public void onDisconnect(){
    	System.out.println("onDisConnect");
    }

    public void onStatusChange(IvrJackStatus var1){
    	System.out.println("onStatusChange");
    }

    public void onInventory(String var1){
    	System.out.println("onInventory");
    }
}