package com.fsilberberg.agendawatchface.agendawatchfacebatteryplugin;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class BatteryService extends IntentService {

    public BatteryService() {
        super("BatteryService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        IntentFilter bf = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent bt = registerReceiver(null, bf);

        // Battery Level
        int level = bt.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = bt.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int batteryPct = (int) ((level / (float)scale) * 100);

        // Battery Charging Status
        int status = bt.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        new BatteryProvider().publishBattery(this, batteryPct, isCharging);
    }
}
