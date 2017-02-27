package com.keepsake.cordova.plugin;

import org.apache.cordova.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import android.*;
import rfid.ivrjacku1.*;
import java.util.*;

public class UHFReader extends CordovaPlugin implements IvrJackAdapter {

	private Context andContext;
	private IvrJackService ivrjacku1;
	private CallbackContext callbackContext;
	private static final String READ_INTENT = "keepsake.intent.action.UHF_READ";
	public static final int READ_CODE = 0;

	String [] permissions = { Manifest.permission.RECORD_AUDIO };

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		if (hasPermisssion()) {
			PluginResult r = new PluginResult(PluginResult.Status.OK);
			callbackContext.sendPluginResult(r);
			return true;
		} else {
			PermissionHelper.requestPermissions(this, 0, permissions);
		}
	}

	private IvrJackService getIvrJackService(){
		if(ivrjacku1 == null)
			ivrjacku1 = new IvrJackService();
		return ivrjacku1;
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		this.andContext = this.cordova.getActivity().getApplicationContext(); 

		System.out.println("APPMSG - Execute");
		
		getIvrJackService().open(andContext, this);

		if (action.equals("read")) {
			System.out.println("APPMSG - Read in Execute");
			this.readTags();
		}

		return false;
	}

	public void readTags() {
		System.out.println("APPMSG - Read Status Change");
        Intent intentRead = new Intent();
        intentRead.setAction(READ_INTENT);
        this.cordova.startActivityForResult(this, intentRead, READ_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	switch(requestCode) {
       		case READ_CODE :
       			System.out.println("APPMSG - onActivityResult : " + resultCode);

	            if (resultCode == this.andContext.RESULT_OK) {
	                JSONObject obj = new JSONObject();
	                try {
	                    ArrayList<String> result = intent.getStringArrayListExtra("RESULT");
	                    JSONArray jsArray = new JSONArray(result);
	                    // obj.put(CANCELLED, false);
	                    obj.put("tags", jsArray);
	                } catch (JSONException e) {
	                    // Log.d(LOG_TAG, "This should never happen");
	                }

	                this.callbackContext.success(obj);
	            } else if (resultCode == this.andContext.RESULT_CANCELED) {
	                JSONObject obj = new JSONObject();
	                try {
	                    // obj.put(CANCELLED, true);
	                    obj.put("tags", "");
	                } catch (JSONException e) {
	                    // Log.d(LOG_TAG, "This should never happen");
	                }
	                this.callbackContext.success(obj);
	            } else {
	                this.callbackContext.error("Unexpected error");
	            }
	            break;
		    /*case RADAR_CODE :
		    	if (resultCode == Activity.RESULT_CANCELED) {
	                JSONObject obj = new JSONObject();
	                try {
	                    obj.put(CANCELLED, true);
	                    obj.put("tags", "");
	                } catch (JSONException e) {
	                    Log.d(LOG_TAG, "This should never happen");
	                }
	                this.callbackContext.success(obj);
	            } else {
	                this.callbackContext.error("Unexpected error");
	            }
	            break;*/
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