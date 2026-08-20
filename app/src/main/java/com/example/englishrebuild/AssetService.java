package com.example.englishrebuild;

import android.content.Context;
import java.io.*;
import java.util.*;

public final class AssetService {
    private AssetService(){}

    public static List<String> listAssets(Context c) {
        ArrayList<String> out = new ArrayList<>();
        try {
            String[] files = c.getAssets().list("");
            if (files != null) Collections.addAll(out, files);
        } catch(Exception ignored) {}
        return out;
    }

    public static boolean exists(Context c, String name) {
        try (InputStream in=c.getAssets().open(name)) { return true; }
        catch(Exception e) { return false; }
    }

    public static File copyToFiles(Context c, String name) throws IOException {
        File out = new File(c.getFilesDir(), name);
        try (InputStream in=c.getAssets().open(name);
             OutputStream os=new FileOutputStream(out)) {
            byte[] buf=new byte[8192]; int n;
            while((n=in.read(buf))>0) os.write(buf,0,n);
        }
        return out;
    }
}
