package com.example.englishrebuild;

import android.content.Context;
import java.io.*;

public final class ScriptService {
    private ScriptService(){}

    public static String inspectBundledScript(Context c) {
        try {
            long size = 0;
            try(InputStream in=c.getAssets().open("script.lr")) {
                byte[] buf=new byte[8192]; int n;
                while((n=in.read(buf))>0) size += n;
            }
            return "Bundled script asset available (" + size + " bytes).";
        } catch(Exception e) {
            return "Bundled script asset is unavailable.";
        }
    }
}
