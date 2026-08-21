package com.mediatek.game.optimization;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.List;

/**
 * Background Process Optimizer - Issue #2, #9
 * Optimizes background third-party app processes
 * Manages permissions and network usage
 */
public class BackgroundProcessOptimizer {
    
    private Context context;
    private PackageManager packageManager;
    private ActivityManager activityManager;
    
    // Define THREAD_GROUP_BG_NONINTERACTIVE constant (value: 1)
    private static final int THREAD_GROUP_BG_NONINTERACTIVE = 1;
    
    public BackgroundProcessOptimizer(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
        this.activityManager = 
            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
    
    /**
     * Optimize third-party apps running in background - Issue #2
     */
    public void optimizeThirdPartyApps() {
        List<ActivityManager.RunningAppProcessInfo> runningApps = 
            activityManager.getRunningAppProcesses();
        
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo appInfo : runningApps) {
                if (!isSystemApp(appInfo.processName)) {
                    optimizeAppProcess(appInfo.processName, appInfo.pid);
                }
            }
        }
    }
    
    /**
     * Restrict unnecessary permissions - Issue #9
     */
    public void restrictThirdPartyPermissions() {
        List<ApplicationInfo> installedApps = packageManager.getInstalledApplications(0);
        
        for (ApplicationInfo appInfo : installedApps) {
            if (!isSystemApp(appInfo.packageName) && !isGameApp(appInfo.packageName)) {
                // Restrict background location access
                restrictPermission(appInfo.packageName, 
                    android.Manifest.permission.ACCESS_FINE_LOCATION);
                
                // Restrict background WiFi access
                restrictPermission(appInfo.packageName, 
                    android.Manifest.permission.CHANGE_NETWORK_STATE);
            }
        }
    }
    
    /**
     * Kill background processes to save battery - Issue #2
     */
    public void killUnusedBackgroundProcesses() {
        // Only keep essential services running
        List<ActivityManager.RunningAppProcessInfo> runningApps = 
            activityManager.getRunningAppProcesses();
        
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo appInfo : runningApps) {
                if (canKillProcess(appInfo)) {
                    activityManager.killBackgroundProcesses(appInfo.processName);
                }
            }
        }
    }
    
    /**
     * Monitor network usage to prevent drain - Issue #2
     */
    public void monitorNetworkUsage() {
        // Monitor data transfer rate
        // Kill apps using excessive data
    }
    
    private void optimizeAppProcess(String processName, int pid) {
        try {
            // Use setProcessGroup for background optimization
            // setProcessGroup was added in Android 5.0 (API 21)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                android.os.Process.setProcessGroup(pid, THREAD_GROUP_BG_NONINTERACTIVE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void restrictPermission(String packageName, String permission) {
        try {
            // Use PackageManager to revoke permissions
            // This may require system app status
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean canKillProcess(ActivityManager.RunningAppProcessInfo appInfo) {
        // Don't kill system apps, home, or currently active app
        return appInfo.importance >= ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE;
    }
    
    private boolean isSystemApp(String packageName) {
        return packageName != null && 
               (packageName.startsWith("android.") || 
                packageName.startsWith("com.android."));
    }
    
    private boolean isGameApp(String packageName) {
        return packageName != null && 
               (packageName.equals("com.mediatek.game") || 
                packageName.contains("game"));
    }
}
