package com.fsilberberg.agendawatchface.agendawatchfacebatteryplugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by 333fr_000 on 10/14/14.
 */
public class BatteryChargingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null &&
                (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED") ||
                        intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED"))) {
            Intent serviceIntent = new Intent(context, BatteryService.class);
            context.startService(serviceIntent);
        }
    }
}
