package com.example.englishrebuild;

import java.util.*;

public final class FeatureRegistry {
    private FeatureRegistry(){}

    public static LinkedHashMap<String,String> status() {
        LinkedHashMap<String,String> m=new LinkedHashMap<>();
        m.put("English UI","Ready");
        m.put("Settings persistence","Ready");
        m.put("HUD persistence","Ready");
        m.put("Runtime logs","Ready");
        m.put("Log export","Ready");
        m.put("Diagnostics","Ready");
        m.put("Local English OCR","Ready");
        m.put("Script asset inspection","Ready");
        m.put("Original proprietary runtime","Not included");
        m.put("Original private backend","Not included");
        return m;
    }
}
