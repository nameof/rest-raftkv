package com.nameof.raftkv;

import cn.hutool.core.lang.Tuple;
import com.nameof.raft.handler.StateMachineHandler;
import com.nameof.raft.log.LogEntry;
import lombok.Setter;

import java.util.Collection;

import static com.nameof.raftkv.LogFormatter.*;

public class KvStateMachineHandler implements StateMachineHandler {
    @Setter
    private LocalKvWriter kvWriter;

    public KvStateMachineHandler(LocalKvWriter kvWriter) {
        this.kvWriter = kvWriter;
    }

    @Override
    public void apply(int i, Collection<LogEntry> collection) {
        for (LogEntry entry : collection) {
            Tuple tuple = LogFormatter.parseLogEntry(entry.getData());
            String command = tuple.get(0);
            String key = tuple.get(1);
            switch (command) {
                case SET:
                    kvWriter.putLocal(key, tuple.get(2));
                    break;
                case DELETE:
                    kvWriter.removeLocal(key);
                    break;
                case CLEAR:
                    kvWriter.clearLocal();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid command: " + command);
            }
            // TODO 返回客户端
        }
    }

    @Override
    public void failed(Collection<LogEntry> collection) {
        // TODO 返回客户端
    }
}
