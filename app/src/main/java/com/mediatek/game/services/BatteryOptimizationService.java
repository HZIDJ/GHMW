package com.mediatek.game.services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;
import com.mediatek.game.receivers.BatteryReceiver;
import com.mediatek.game.optimization.ThermalManager;
import com.mediatek.game.optimization.BackgroundProcessOptimizer;

/**
 * Battery Optimization Service - Issue #2, #9
 * Handles battery drain prevention and thermal management
 * Optimizes third-party app background processes
 */
public class BatteryOptimizationService extends Service {
    
    private BatteryReceiver batteryReceiver;
    private ThermalManager thermalManager;
    private BackgroundProcessOptimizer processOptimizer;
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initializeBatteryOptimization();
        initializeThermalManagement();
        initializeBackgroundOptimization();
        return START_STICKY;
    }
    
    private void initializeBatteryOptimization() {
        batteryReceiver = new BatteryReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }
    
    private void initializeThermalManagement() {
        thermalManager = new ThermalManager(this);
        thermalManager.startMonitoring();
    }
    
    private void initializeBackgroundOptimization() {
        processOptimizer = new BackgroundProcessOptimizer(this);
        processOptimizer.optimizeThirdPartyApps();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (batteryReceiver != null) {
            unregisterReceiver(batteryReceiver);
        }
        if (thermalManager != null) {
            thermalManager.stopMonitoring();
        }
    }
}
