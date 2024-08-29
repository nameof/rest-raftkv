package com.nameof.raftkv;

import com.nameof.raft.Node;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

import static com.nameof.raftkv.LogFormatter.*;

public class RaftKvStore extends AbstractRaftKvStore implements LocalKvWriter {

    private final Node raft;

    public RaftKvStore(Node raft) {
        this.raft = raft;
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        String log = LogFormatter.log(SET, key, value);
        raft.appendEntry(Collections.singletonList(log), null);
        return null;
    }

    @Override
    public String remove(Object key) {
        String log = LogFormatter.log(DELETE, (String) key, null);
        raft.appendEntry(Collections.singletonList(log), null);
        return null;
    }
    @Override
    public void clear() {
        String log = LogFormatter.log(CLEAR,  null, null);
        raft.appendEntry(Collections.singletonList(log), null);
    }


    @Override
    public String putLocal(String key, String value) {
        return super.put(key, value);
    }

    @Override
    public String removeLocal(Object key) {
        return super.remove(key);
    }

    @Override
    public void clearLocal() {
        super.clear();
    }
}
