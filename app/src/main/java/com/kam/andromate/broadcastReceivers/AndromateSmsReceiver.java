package com.kam.andromate.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.kam.andromate.utils.AppUtils;

public class AndromateSmsReceiver extends BroadcastReceiver {

    private static final String TAG = "AndromateSmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format"); // Needed for createFromPdu
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage;
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    Log.d(TAG, "SMS from " + sender + ": " + messageBody);
                    // You can broadcast this internally or update UI
                    if (messageBody != null && messageBody.toLowerCase().contains("open")) {
                        try {
                            AppUtils.openAndromateApp(context);
                        } catch (Throwable t) {
                            Log.e(TAG," cannot launch intent due to "+t);
                        }
                    }
                }
            }
        }
    }

}
