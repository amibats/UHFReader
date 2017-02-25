package com.keepsake.cordova.plugin;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ivrjacku1.rfid.ivrjacku1.IvrJackStatus;

public class UHFReader extends CordovaPlugin {

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
		if (args) {
			callbackContext.success(args);
		} else {
			callbackContext.error("Read tags error.");
		}
	}

	private void writeTags(JSONArray args, CallbackContext callbackContext) {
		if (args) {
			callbackContext.success(message);
		} else {
			callbackContext.error("Write tags error.");
		}
	}
}