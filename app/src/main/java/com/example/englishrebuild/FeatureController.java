package com.example.englishrebuild;

import android.content.Context;
import android.net.Uri;
import java.io.*;
import java.util.*;

public final class FeatureController {
    private FeatureController(){}

    public static boolean assetExists(Context c, String name) {
        try (InputStream in=c.getAssets().open(name)) { return true; }
        catch(Exception e) { return false; }
    }

    public static String readTextAsset(Context c, String name) {
        try (InputStream in=c.getAssets().open(name);
             BufferedReader r=new BufferedReader(new InputStreamReader(in))) {
            StringBuilder b=new StringBuilder();
            String line;
            while((line=r.readLine())!=null) b.append(line).append('\n');
            return b.toString();
        } catch(Exception e) {
            return "";
        }
    }

    public static String describeUri(Uri uri) {
        return uri==null ? "" : uri.toString();
    }

    public static Map<String,String> runtimeStatus(Context c) {
        Map<String,String> m=new LinkedHashMap<>();
        m.put("Language","English");
        m.put("OCR asset", assetExists(c,"eng.traineddata") ? "Available" : "Missing");
        m.put("Script asset", assetExists(c,"script.lr") ? "Available" : "Missing");
        m.put("LuaSocket x86", assetExists(c,"luasocket_x86.zip") ? "Available" : "Missing");
        m.put("LuaSocket ARM", assetExists(c,"luasocket_arm.zip") ? "Available" : "Missing");
        return m;
    }
}
