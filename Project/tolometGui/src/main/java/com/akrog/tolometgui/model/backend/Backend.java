package com.akrog.tolometgui.model.backend;

import com.google.android.gms.tasks.Task;

import java.util.List;

public interface Backend {
    Task<List<Motd>> getMotd(long stamp, String lang);
    Task<List<VersionUpdate>> getUpdates(int code, String lang);
    Task<List<ConfigUpdate>> getConfigs(long stamp, String lang);
}
