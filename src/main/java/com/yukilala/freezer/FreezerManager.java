package com.yukilala.freezer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Centralized freezer guard. With allowAll = true this class ensures
 * no app will be frozen. Wire existing freeze checks/calls to use this.
 */
public final class FreezerManager {
    // When true, freezing is disabled for everyone (no-op).
    private static volatile boolean allowAll = true;

    private FreezerManager() {}

    // Called by code that decides whether to freeze an app.
    public static boolean shouldFreeze(String packageName) {
        // return false when freezing is globally disabled
        return !allowAll;
    }

    // Optional runtime toggle (keeps change reversible)
    public static void setAllowAll(boolean allow) {
        allowAll = allow;
    }

    // Optional no-op freeze/unfreeze helpers if code calls these directly.
    public static void freezeApp(String packageName) {
        // intentionally do nothing (no-op)
    }

    public static void unfreezeApp(String packageName) {
        // intentionally do nothing (no-op)
    }

    // Optional: read default from resource freezer-config.json if present
    static {
        try (InputStream in = FreezerManager.class.getResourceAsStream("/freezer-config.json")) {
            if (in != null) {
                try (BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) sb.append(line);
                    String s = sb.toString();
                    if (s.contains("\"allowAll\"")) {
                        // naive check: if config contains allowAll:true (no JSON parsing dependency)
                        if (s.contains("true")) setAllowAll(true);
                        else setAllowAll(false);
                    }
                }
            }
        } catch (IOException e) {
            // ignore and keep default
        }
    }
}
