package com.keepsake.cordova.plugin;

import org.apache.cordova.*;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast; 

import rfid.ivrjacku1.*;

public class UHFReader extends CordovaPlugin implements IvrJackAdapter {

	private Context andContext;
	private IvrJackService ivrjacku1;

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
			this.readTags(args, callbackContext);
			// return true;
		} else if (action.equals("write")) {
			System.out.println("APPMSG - Write in Execute");
			this.writeTags(args, callbackContext);
			return true;
		}

		return false;
	}

	private void readTags(JSONArray args, CallbackContext callbackContext) {
		System.out.println("APPMSG - Read Status Change");
	}

	private void writeTags(JSONArray args, CallbackContext callbackContext) {

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

    @Override
    public void onInventory(String var1){
    	System.out.println("APPMSG - onInventory");
    }
}