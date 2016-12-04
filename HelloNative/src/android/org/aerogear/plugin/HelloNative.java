package org.aerogear.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;

public class HelloNative extends CordovaPlugin {
  @Override
  public boolean execute(
    String action,
    JSONArray args,
    CallbackContext callbackContext
  ) throws JSONException {
    if ("hello".equals(action)) {
      sayHello(args.getString(0), callbackContext);
      return true;
    }

    return false;
  }

  private void sayHello(
    String name,
    CallbackContext callbackContext
  ) {
    if (name == null || name.length() == 0) {
      callbackContext.error("Empty message!");
    } else {
      Toast.makeText(
        webView.getContext(),
        "Hello " + name,
        Toast.LENGTH_LONG
      ).show();
      callbackContext.success("Hello " + name);
    }
  }
}