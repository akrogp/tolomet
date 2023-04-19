package com.akrog.tolometgui.model.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseBackend implements Backend {
    private static final int MAX_RESULTS = 3;

    @Override
    public Task<List<Motd>> getMotd(long stamp, String lang) {
        return FirebaseDatabase.getInstance().getReference("tolomet")
            .child("motd")
            .orderByKey()
            .startAfter(String.valueOf(stamp))
            .limitToLast(MAX_RESULTS)
            .get()
            .continueWith(task -> {
                DataSnapshot result = task.getResult();
                List<Motd> list = new ArrayList<>((int)result.getChildrenCount());
                for( DataSnapshot data : result.getChildren() ) {
                    String child = data.hasChild(lang) ? lang : "en";
                    if( data.hasChild(child) ) {
                        Motd motd = new Motd();
                        motd.setMsg(data.child(child).getValue(String.class));
                        motd.setStamp(Long.parseLong(data.getKey()));
                        fillConditions(data, motd);
                        list.add(motd);
                    }
                }
                Collections.reverse(list);
                return list;
            });
    }

    @Override
    public Task<List<VersionUpdate>> getUpdates(int code, String lang) {
        return FirebaseDatabase.getInstance().getReference("tolomet")
            .child("version")
            .orderByKey()
            .startAfter(String.valueOf(code))
            .limitToLast(MAX_RESULTS)
            .get()
            .continueWith(
                task -> {
                    DataSnapshot result = task.getResult();
                    List<VersionUpdate> list = new ArrayList<>();
                    for( DataSnapshot data : result.getChildren() ) {
                        String child = data.hasChild(lang) ? lang : "en";
                        if( data.hasChild(child) ) {
                            DataSnapshot msgs = data.child(child);
                            if(msgs.hasChildren()) {
                                VersionUpdate upd = new VersionUpdate();
                                upd.setCode(Integer.parseInt(data.getKey()));
                                fillConditions(data, upd);
                                for( DataSnapshot msg : msgs.getChildren() )
                                    upd.getUpdates().add(msg.getValue(String.class));
                                list.add(upd);
                            }
                        }
                    }
                    Collections.reverse(list);
                    return list;
                }
            );
    }

    @Override
    public Task<List<ConfigUpdate>> getConfigs(long stamp, String lang) {
        return FirebaseDatabase.getInstance().getReference("tolomet")
                .child("cfg")
                .orderByKey()
                .startAfter(String.valueOf(stamp))
                .get()
                .continueWith(
                    task -> {
                        DataSnapshot result = task.getResult();
                        List<ConfigUpdate> list = new ArrayList<>();
                        for( DataSnapshot item : result.getChildren() ) {
                            for( DataSnapshot data : item.getChildren() ) {
                                ConfigUpdate configUpdate = new ConfigUpdate();
                                configUpdate.setStamp(Long.parseLong(item.getKey()));
                                String child = data.hasChild(lang) ? lang : "en";
                                configUpdate.setDescription(data.child(child).getValue(String.class));
                                configUpdate.setKey(data.child("key").getValue(String.class));
                                configUpdate.setType(data.child("type").getValue(String.class));
                                configUpdate.setValue(data.child("value").getValue(String.class));
                                fillConditions(data, configUpdate);
                            }
                        }
                        Collections.reverse(list);
                        return list;
                    }
                );
    }

    private void fillConditions(DataSnapshot data, BackendNotification notification) {
        if( data.hasChild("minv") )
            notification.setVmin(data.child("minv").getValue(Integer.class));
        if( data.hasChild("maxv") )
            notification.setVmax(data.child("maxv").getValue(Integer.class));
    }
}
