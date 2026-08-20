package com.example.englishrebuild;

import android.content.Context;
import android.os.Build;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Diagnostics {
    private Diagnostics(){}

    public static Map<String,String> collect(Context c) {
        Map<String,String> out = new LinkedHashMap<>();
        out.put("Android", Build.VERSION.RELEASE);
        out.put("SDK", String.valueOf(Build.VERSION.SDK_INT));
        out.put("Package", c.getPackageName());
        out.put("Language", "English");
        out.put("Cloud phone", "External Android environment");
        out.put("OCR asset", FeatureController.assetExists(c,"eng.traineddata") ? "Available":"Missing");
        out.put("Script asset", FeatureController.assetExists(c,"script.lr") ? "Available":"Missing");
        return out;
    }
}
