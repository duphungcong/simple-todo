package com.duphungcong.simpletodo;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

public class SimpleTodoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiates DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        // Add for verbose logging
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);
    }
}
