package com.urise.webapp.storage;


import com.urise.webapp.storage.serializer.XMLStreamSerializer;

public class XMLPathStorageTest extends AbstractStorageTest {
    public XMLPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XMLStreamSerializer()));
    }
}