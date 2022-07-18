package com.urise.webapp.storage;


import com.urise.webapp.storage.serializer.JSONStreamSerializer;

public class JSONPathStorageTest extends AbstractStorageTest {
    public JSONPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JSONStreamSerializer()));
    }
}