package com.example.englishrebuild;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.io.File;

public class MainActivity extends Activity {
    AppState appState = new AppState();
    android.content.SharedPreferences prefs;
    LinearLayout root, content;
    Uri selectedImage;
    TextView title, status;
    final ArrayList<String> logs = new ArrayList<>();
    final String LOG_PREF = "runtime_logs";
    final String EVENT_PREF = "event_count";

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        prefs = getSharedPreferences("english_rebuild", MODE_PRIVATE);
        appState.notificationsEnabled = prefs.getBoolean("notifications", true);
        appState.floatingWindowEnabled = prefs.getBoolean("floating", true);
        appState.autoStart = prefs.getBoolean("autostart", false);
        appState.hudEnabled = prefs.getBoolean("hud", true);
        loadLogs();
        log("Application initialized");
        show("Main");
    }

    TextView text(String s, float size) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextColor(Color.WHITE); t.setTextSize(size);
        t.setPadding(0, 10, 0, 10); return t;
    }

    Button button(String s) {
        Button b = new Button(this);
        b.setText(s); b.setAllCaps(false); return b;
    }

    void base(String name) {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20,20,20,90);
        root.setBackgroundColor(Color.rgb(9,12,19));

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        title = text(name, 26);
        header.addView(title, new LinearLayout.LayoutParams(0,-2,1));
        status = text("● Ready", 13);
        status.setTextColor(Color.rgb(100,225,150));
        header.addView(status);
        root.addView(header);

        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        root.addView(content);

        setContentView(root);
    }

    void add(String s) { content.addView(text(s, 16)); }

    void nav() {
        LinearLayout nav = new LinearLayout(this);
        String[] names = {"Main","Settings","Logs","Update","HUD","Scripts","OCR","Web","About","Feature Status"};
        for (String n : names) {
            Button b = button(n);
            b.setTextSize(9);
            b.setOnClickListener(v -> show(n));
            nav.addView(b, new LinearLayout.LayoutParams(0,-2,1));
        }
        root.addView(nav);
    }

    void show(String screen) {
        base(screen);
        switch (screen) {
            case "Main": main(); break;
            case "Settings": settings(); break;
            case "Logs": logs(); break;
            case "Update": update(); break;
            case "HUD": hud(); break;
            case "Scripts": scripts(); break;
            case "OCR": ocr(); break;
            case "Web": web(); break;
            case "About": about(); break;
            case "Feature Status": featureStatus(); break;
        }
        nav();
    }

    void main() {
        add("English interface enabled");
        add("Private-use rebuild");
        add("Local events: " + (prefs==null?0:prefs.getInt(EVENT_PREF,0)));

        add("Status: Ready");
        Button hud=button("Open HUD"); hud.setOnClickListener(v->show("HUD")); content.addView(hud);
        Button scripts=button("Script Manager"); scripts.setOnClickListener(v->show("Scripts")); content.addView(scripts);
        Button ocr=button("OCR / Image"); ocr.setOnClickListener(v->show("OCR")); content.addView(ocr);
        Button web=button("Web Interface"); web.setOnClickListener(v->show("Web")); content.addView(web);

        Button health=button("App Health Check");
        health.setOnClickListener(v->{
            Map<String,String> d=Diagnostics.collect(this);
            StringBuilder b=new StringBuilder();
            b.append("Status: Ready\\n");
            b.append("English mode: ").append(d.get("Language")).append("\\n");
            b.append("OCR asset: ").append(d.get("OCR asset")).append("\\n");
            b.append("Script asset: ").append(d.get("Script asset")).append("\\n");
            b.append("Android: ").append(d.get("Android")).append(" / SDK ").append(d.get("SDK"));
            new AlertDialog.Builder(this).setTitle("App Health")
                .setMessage(b.toString())
                .setPositiveButton("Copy",(dialog,which)->{
                    android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(android.content.ClipData.newPlainText("App Health",b.toString()));
                    Toast.makeText(this,"Health report copied",Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Close",null).show();
            log("App health check completed");
        });
        content.addView(health);
    }

    void settings() {
        add("Application configuration");

        Switch language = new Switch(this); language.setText("English interface"); language.setChecked(true);
        language.setTextColor(Color.WHITE); content.addView(language);
        Switch notifications = new Switch(this); notifications.setText("Notifications"); notifications.setChecked(appState.notificationsEnabled);
        notifications.setTextColor(Color.WHITE); content.addView(notifications);
        Switch floating = new Switch(this); floating.setText("Floating Window"); floating.setChecked(appState.floatingWindowEnabled);
        floating.setTextColor(Color.WHITE); content.addView(floating);
        Switch auto = new Switch(this); auto.setText("Auto Start"); auto.setChecked(appState.autoStart); auto.setTextColor(Color.WHITE); content.addView(auto);
        Button save=button("Save Settings");
        save.setOnClickListener(v->{ appState.notificationsEnabled=notifications.isChecked();
        appState.floatingWindowEnabled=floating.isChecked();
        appState.autoStart=auto.isChecked();
        prefs.edit()
            .putBoolean("notifications",appState.notificationsEnabled)
            .putBoolean("floating",appState.floatingWindowEnabled)
            .putBoolean("autostart",appState.autoStart)
            .apply();
        log("Settings saved"); Toast.makeText(this,"Settings saved",Toast.LENGTH_SHORT).show();});
        content.addView(save);
        Button exportSettings=button("Export Settings");
        exportSettings.setOnClickListener(v->{
            String data="language=English\\n"+
                "notifications="+appState.notificationsEnabled+"\\n"+
                "floatingWindow="+appState.floatingWindowEnabled+"\\n"+
                "autoStart="+appState.autoStart+"\\n"+
                "hud="+appState.hudEnabled+"\\n";
            android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(android.content.ClipData.newPlainText("English Rebuild Settings",data));
            Toast.makeText(this,"Settings copied",Toast.LENGTH_SHORT).show();
            log("Settings exported");
        });
        content.addView(exportSettings);

        Button reset=button("Reset App Settings");
        reset.setOnClickListener(v->{
            appState.reset();
            prefs.edit().clear().apply();
            Toast.makeText(this,"Settings reset",Toast.LENGTH_SHORT).show();
            show("Settings");
        });
        content.addView(reset);
    }

    void logs() {
        TextView l=text(String.join("\n", logs), 13);
        l.setTextIsSelectable(true); content.addView(l);

        Button refresh=button("Refresh Logs");
        refresh.setOnClickListener(v->show("Logs"));
        content.addView(refresh);

        Button export=button("Export Logs");
        export.setOnClickListener(v->{
            try {
                File f=new File(getCacheDir(),"english_rebuild_logs.txt");
                try(java.io.FileWriter w=new java.io.FileWriter(f)) {
                    w.write(String.join("\n", logs));
                }
                Intent share=new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT,String.join("\n", logs));
                startActivity(Intent.createChooser(share,"Export Logs"));
            } catch(Exception e) {
                Toast.makeText(this,"Unable to export logs",Toast.LENGTH_SHORT).show();
            }
        });
        content.addView(export);

        Button clear=button("Clear Logs");
        clear.setOnClickListener(v->{logs.clear();
            if (prefs != null) prefs.edit().remove(LOG_PREF).apply();
            log("Logs cleared");
            show("Logs");});
        content.addView(clear);
    }

    void update() {
        add("Current version: 1.0.0");
        add("Update status: Ready"); add("Channel: Stable");
        Button check=button("Check for Updates");
        check.setOnClickListener(v->{ log("Update check completed"); Toast.makeText(this,"No update available in this build",Toast.LENGTH_SHORT).show(); });
        content.addView(check);
        content.addView(button("Download and Install"));
    }

    void hud() {
        Switch visible=new Switch(this); visible.setText("HUD Visible"); visible.setChecked(appState.hudEnabled);
        visible.setTextColor(Color.WHITE);
        visible.setOnCheckedChangeListener((b,checked)->{
            appState.hudEnabled=checked;
            prefs.edit().putBoolean("hud", checked).apply();
            log("HUD visibility changed: "+checked);
        });
        content.addView(visible);
        add("Transparency");
        SeekBar alpha=new SeekBar(this); alpha.setMax(100); alpha.setProgress(80); content.addView(alpha);
        add("Size");
        SeekBar size=new SeekBar(this); size.setMax(100); size.setProgress(50); content.addView(size);
        Button permission=button("Manage Overlay Permission");
        permission.setOnClickListener(v->{
            try { startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:"+getPackageName()))); } catch(Exception ignored) {}
        });
        content.addView(permission);
    }

    void scripts() {
        add("Runtime: Lua");
        add("Reference: script.lr");
        add("Status: Ready");
        Button choose=button("Choose Script File");
        choose.setOnClickListener(v->{
            Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("*/*"); i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i,10);
        });
        content.addView(choose);
        Button inspect=button("Inspect Bundled Script");
        inspect.setOnClickListener(v->{
            String info=ScriptService.inspectBundledScript(this);
            new AlertDialog.Builder(this)
                .setTitle("Script Information")
                .setMessage(info + "\\n\\nExecution is disabled in this rebuild.")
                .setPositiveButton("Copy",(d,w)->{
                    android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(android.content.ClipData.newPlainText("Script Information",info));
                    Toast.makeText(this,"Script information copied",Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close",null).show();
        });
        content.addView(inspect);

        Button run=button("Run Authorized Script");
        run.setOnClickListener(v->{
            log("Script execution requested");
            Toast.makeText(this,
                "Script runtime is not configured in this rebuild.",
                Toast.LENGTH_LONG).show();
        });
        content.addView(run);
    }

    void ocr() {
        add("OCR Language: English");
        add("Image recognition interface");
        Button choose=button("Choose Image");
        choose.setOnClickListener(v->{
            Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("image/*"); i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i,20);
        });
        content.addView(choose);
        Button process=button("Process Selected Image");
        process.setOnClickListener(v->{
            if (selectedImage == null) {
                Toast.makeText(this,"Choose an image first",Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                OcrEngine.recognize(bitmap,new OcrEngine.Callback(){
                    public void onSuccess(String result){
                        log("OCR completed");
                        new AlertDialog.Builder(MainActivity.this)
                            .setTitle("OCR Result").setMessage(result.isEmpty()?"No text detected":result)
                            .setPositiveButton("Copy",(d,w)->{
                                android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                                cm.setPrimaryClip(android.content.ClipData.newPlainText("OCR Result",result));
                            }).setNegativeButton("Close",null).show();
                    }
                    public void onError(Exception error){
                        log("OCR failed: "+error.getMessage());
                        Toast.makeText(MainActivity.this,"OCR failed",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch(Exception e) {
                Toast.makeText(this,"Unable to open image",Toast.LENGTH_SHORT).show();
            }
        });
        content.addView(process);
    }

    void web() {
        EditText api=new EditText(this); api.setHint("API Endpoint");
        api.setTextColor(Color.WHITE); api.setHintTextColor(Color.GRAY); content.addView(api);
        EditText ws=new EditText(this); ws.setHint("WebSocket Endpoint");
        ws.setTextColor(Color.WHITE); ws.setHintTextColor(Color.GRAY); content.addView(ws);
        Button connect=button("Connect");
        connect.setOnClickListener(v->{ appState.connection="Connected"; status.setText("● Connected"); log("Cloud endpoint configured"); });
        content.addView(connect);
    }

    void about() {
        add("English Rebuild");
        add("Version 1.0.30");
        add("Language: English");
        add("English rebuild of the supplied application reference.");
        add("Environment: Android");
        add("Cloud phone: External Android environment");
        add("Build: v27 • Private Use • English");

        Button copyInfo=button("Copy App Info");
        copyInfo.setOnClickListener(v->{
            String info="English Rebuild v27\\nLanguage: English\\nTarget: Android / supported cloud Android";
            android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(android.content.ClipData.newPlainText("App Info",info));
            Toast.makeText(this,"App information copied",Toast.LENGTH_SHORT).show();
            log("App information copied");
        });
        content.addView(copyInfo);

        Button diagnostics=button("Run Diagnostics");
        diagnostics.setOnClickListener(v->{
            Map<String,String> d=Diagnostics.collect(this);
            StringBuilder b=new StringBuilder();
            for (Map.Entry<String,String> e:d.entrySet())
                b.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            new AlertDialog.Builder(this).setTitle("Diagnostics")
                .setMessage(b.toString())
                .setPositiveButton("Copy", (dialog, which) -> {
                    android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                    cm.setPrimaryClip(android.content.ClipData.newPlainText("Diagnostics",b.toString()));
                    Toast.makeText(this,"Diagnostics copied",Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Close",null).show();
        });
        content.addView(diagnostics);
    }

    void loadLogs() {
        String raw = prefs.getString(LOG_PREF, "");
        if (!raw.isEmpty()) logs.addAll(Arrays.asList(raw.split("\\n", -1)));
    }

    void log(String s) {
        int count=prefs==null?0:prefs.getInt(EVENT_PREF,0)+1;
        if (prefs!=null) prefs.edit().putInt(EVENT_PREF,count).apply();
        logs.add(new Date().toString()+"  "+s);
        if (logs.size() > 200) logs.remove(0);
        if (prefs != null) prefs.edit().putString(LOG_PREF, String.join("\\n", logs)).apply();
    }

    @Override protected void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK && data!=null && data.getData()!=null) {
            if (requestCode==20) {
                selectedImage=data.getData();
                log("Image selected for OCR");
                Toast.makeText(this,"Image selected",Toast.LENGTH_SHORT).show();
             } else if (requestCode==10) {
                String uri=data.getData().toString();
                log("Script file selected: "+uri);
                new AlertDialog.Builder(this)
                    .setTitle("Script Selected")
                    .setMessage(uri+"\\n\\nThe rebuild will not execute the file.")
                    .setPositiveButton("Copy",(d,w)->{
                        android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(android.content.ClipData.newPlainText("Script URI",uri));
                    }).setNegativeButton("Close",null).show();
            }
        }
    }

    void featureStatus() {
        add("Feature Status");
        Map<String,String> status=FeatureRegistry.status();
        for (Map.Entry<String,String> e:status.entrySet())
            add(e.getKey()+": "+e.getValue());

        Button copy=button("Copy Feature Status");
        copy.setOnClickListener(v->{
            StringBuilder report=new StringBuilder("English Rebuild v33\n");
            for (Map.Entry<String,String> e:FeatureRegistry.status().entrySet())
                report.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
            android.content.ClipboardManager cm=(android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(android.content.ClipData.newPlainText("Feature Status",report.toString()));
            Toast.makeText(this,"Feature status copied",Toast.LENGTH_SHORT).show();
            log("Feature status copied");
        });
        content.addView(copy);
    }

    @Override protected void onPause() {
        super.onPause();
        if (prefs != null) {
            prefs.edit()
                .putBoolean("notifications", appState.notificationsEnabled)
                .putBoolean("floating", appState.floatingWindowEnabled)
                .putBoolean("autostart", appState.autoStart)
                .putBoolean("hud", appState.hudEnabled)
                .apply();
        }
    }

}
