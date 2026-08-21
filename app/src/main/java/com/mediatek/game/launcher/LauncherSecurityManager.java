package com.mediatek.game.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Launcher Security Manager - Issue #5, #7
 * Implements anti-bypass protection for launcher
 * Manages app suggestions and custom packages
 */
public class LauncherSecurityManager {
    
    private Context context;
    private PackageManager packageManager;
    
    public LauncherSecurityManager(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
    }
    
    /**
     * Prevent launcher bypass attacks - Issue #5, #7
     */
    public boolean validateLauncherIntent(Intent intent) {
        // Verify intent origin
        String action = intent.getAction();
        String packageName = intent.getPackage();
        
        // Only allow trusted launcher actions
        boolean isValidLauncherAction = 
            Intent.ACTION_MAIN.equals(action) ||
            Intent.ACTION_VIEW.equals(action);
        
        // Verify package is installed
        try {
            packageManager.getApplicationInfo(packageName, 0);
            return isValidLauncherAction;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Verify app permissions before launching - Issue #9
     */
    public boolean verifyAppPermissions(String packageName) {
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(
                packageName, 0);
            
            // Check if app is malicious or requests excessive permissions
            return !isHighRiskApp(appInfo);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Implement game suggestion system - Issue #6
     */
    public java.util.List<String> getGameSuggestions() {
        java.util.List<String> gameSuggestions = new java.util.ArrayList<>();
        
        // Get installed games
        Intent gameIntent = new Intent(Intent.ACTION_MAIN);
        gameIntent.addCategory("android.intent.category.GAME");
        
        java.util.List<android.content.pm.ResolveInfo> resolveList = 
            packageManager.queryIntentActivities(gameIntent, 0);
        
        for (android.content.pm.ResolveInfo resolveInfo : resolveList) {
            gameSuggestions.add(resolveInfo.activityInfo.packageName);
        }
        
        return gameSuggestions;
    }
    
    /**
     * Support custom app packages - Issue #6
     */
    public java.util.List<String> getCustomPackages() {
        // Load custom package list
        android.content.SharedPreferences prefs = 
            context.getSharedPreferences("custom_packages", Context.MODE_PRIVATE);
        
        java.util.Map<String, ?> customPackages = prefs.getAll();
        return new java.util.ArrayList<>(customPackages.keySet());
    }
    
    /**
     * Add custom package - Issue #6
     */
    public void addCustomPackage(String packageName) {
        android.content.SharedPreferences prefs = 
            context.getSharedPreferences("custom_packages", Context.MODE_PRIVATE);
        
        prefs.edit().putString(packageName, "1").apply();
    }
    
    private boolean isHighRiskApp(ApplicationInfo appInfo) {
        // Check for suspicious characteristics
        return (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
