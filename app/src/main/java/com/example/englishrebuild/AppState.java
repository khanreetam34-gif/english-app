package com.example.englishrebuild;

public final class AppState {
    public boolean hudEnabled = true;
    public boolean notificationsEnabled = true;
    public boolean floatingWindowEnabled = true;
    public boolean autoStart = false;
    public String connection = "Disconnected";
    public String version = "1.0.0";

    public void reset() {
        hudEnabled = true;
        notificationsEnabled = true;
        floatingWindowEnabled = true;
        autoStart = false;
        connection = "Disconnected";
    }
}
