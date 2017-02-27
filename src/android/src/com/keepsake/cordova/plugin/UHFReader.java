package com.keepsake.cordova.plugin;

import org.apache.cordova.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import android.*;
import rfid.ivrjacku1.*;

public class UHFReader extends CordovaPlugin implements IvrJackAdapter {

	private Context andContext;
	private IvrJackService ivrjacku1;

	String [] permissions = { Manifest.permission.RECORD_AUDIO };

	private IvrJackService getIvrJackService(){
		if(ivrjacku1 == null)
			ivrjacku1 = new IvrJackService();
		return ivrjacku1;
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
		System.out.println("APPMSG - Execute");
		this.andContext = this.cordova.getActivity().getApplicationContext(); 
		getIvrJackService().open(andContext, this);

		if (action.equals("read")) {
			System.out.println("APPMSG - Read in Execute");

			if (hasPermisssion()) {
                PluginResult r = new PluginResult(PluginResult.Status.OK);
                callbackContext.sendPluginResult(r);
                return true;
            } else {
                PermissionHelper.requestPermissions(this, 0, permissions);
            }
            return true;

			// this.readTags(args, callbackContext);
		}

		return false;
	}

	public boolean hasPermisssion() {
        for (String p : permissions) {
            if (!PermissionHelper.hasPermission(this, p)) {
                return false;
            }
        }
        return true;
    }

	private void readTags(JSONArray args, CallbackContext callbackContext) {
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