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
                                if( data.hasChild("from") )
                                    upd.setFrom(data.child("from").getValue(Integer.class));
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
}
