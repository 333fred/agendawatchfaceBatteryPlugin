package com.fsilberberg.agendawatchface.agendawatchfacebatteryplugin;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.janbo.agendawatchface.api.AgendaItem;
import de.janbo.agendawatchface.api.AgendaWatchfacePlugin;
import de.janbo.agendawatchface.api.LineOverflowBehavior;
import de.janbo.agendawatchface.api.TimeDisplayType;

/**
 * Created by 333fr_000 on 10/14/14.
 */
public class BatteryProvider extends AgendaWatchfacePlugin {
    private static final int INTENT_ID = 3;

    @Override
    public String getPluginId() {
        return "com.fsilberberg.agendawatchface.agendawatchfacebatteryplugin";
    }

    @Override
    public String getPluginDisplayName() {
        return "Battery Display";
    }

    @Override
    public void onRefreshRequest(Context context) {
        Intent serviceIntent = new Intent(context, BatteryService.class);
        PendingIntent pi = PendingIntent.getService(context, INTENT_ID, serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, AlarmManager.INTERVAL_HALF_HOUR, pi);
    }

    public void publishBattery(Context context, int level, boolean charging) {
        List<AgendaItem> batteryItemList = new ArrayList<>();
        AgendaItem batteryItem = new AgendaItem(getPluginId());
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        batteryItem.startTime = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        batteryItem.endTime = cal.getTime();

        batteryItem.line1.timeDisplay = TimeDisplayType.NONE;
        batteryItem.line1.textBold = false;
        batteryItem.line1.overflow = LineOverflowBehavior.OVERFLOW_IF_NECESSARY;
        batteryItem.line1.text = String.format("Battery: %d%%, %s", level, charging ? "Charging" : "Discharging");
        batteryItemList.add(batteryItem);
        publishData(context, batteryItemList, false);
    }

    @Override
    public void onShowSettingsRequest(Context context) {

    }
}
