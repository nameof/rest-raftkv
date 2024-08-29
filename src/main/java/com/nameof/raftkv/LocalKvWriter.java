package com.nameof.raftkv;

public interface LocalKvWriter {
    String putLocal(String key, String value);

    String removeLocal(Object key);

    void clearLocal();
}