package com.akrog.tolometgui.model.backend;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.Collections;
import java.util.List;

public class LocalBackend implements Backend {
    @Override
    public Task<List<Motd>> getMotd(long stamp, String lang) {
        return Tasks.forResult(Collections.emptyList());
    }

    @Override
    public Task<List<VersionUpdate>> getUpdates(int code, String lang) {
        return Tasks.forResult(Collections.emptyList());
    }

    @Override
    public Task<List<ConfigUpdate>> getConfigs(long stamp, String lang) {
        return Tasks.forResult(Collections.emptyList());
    }
}
