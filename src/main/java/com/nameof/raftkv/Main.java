package com.nameof.raftkv;

import cn.hutool.core.io.FileUtil;
import com.nameof.raft.Node;

public class Main {
    public static void main(String[] args) {
        KvStateMachineHandler stateMachineHandler = new KvStateMachineHandler(null);
        Node raft = new Node(FileUtil.getUserHomeDir(), stateMachineHandler);
        RaftKvStore kvStore = new RaftKvStore(raft);
        stateMachineHandler.setKvWriter(kvStore);
        raft.start();

        // start webserver
    }
}
