package com.mediatek.game.version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Version Manager - Issue #1
 * Manages APK version and build number properly
 * Prevents random version changes during upgrades
 */
public class VersionManager {
    
    private Context context;
    private PackageManager packageManager;
    private static final String VERSION_PREF_KEY = "app_version";
    private static final String BUILD_NUMBER_PREF_KEY = "build_number";
    
    public VersionManager(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
    }
    
    /**
     * Get current version name from manifest - Issue #1
     */
    public String getVersionName() {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }
    
    /**
     * Get current version code - Issue #1
     */
    public int getVersionCode() {
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0);
            
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                return (int) packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }
    
    /**
     * Store version info for consistency - Issue #1
     */
    public void storeVersionInfo() {
        android.content.SharedPreferences prefs = 
            context.getSharedPreferences("version_info", Context.MODE_PRIVATE);
        
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putString(VERSION_PREF_KEY, getVersionName());
        editor.putInt(BUILD_NUMBER_PREF_KEY, getVersionCode());
        editor.apply();
    }
    
    /**
     * Verify version consistency - Issue #1
     */
    public boolean verifyVersionConsistency() {
        android.content.SharedPreferences prefs = 
            context.getSharedPreferences("version_info", Context.MODE_PRIVATE);
        
        String storedVersion = prefs.getString(VERSION_PREF_KEY, "");
        int storedBuildNumber = prefs.getInt(BUILD_NUMBER_PREF_KEY, 0);
        
        String currentVersion = getVersionName();
        int currentBuildNumber = getVersionCode();
        
        boolean versionMatches = currentVersion.equals(storedVersion);
        boolean buildMatches = currentBuildNumber == storedBuildNumber;
        
        return versionMatches && buildMatches;
    }
    
    /**
     * Get build identifier without randomization - Issue #1
     */
    public String getBuildIdentifier() {
        return getVersionName() + "." + getVersionCode();
    }
}
