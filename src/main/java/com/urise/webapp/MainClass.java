package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

public class MainClass {

    public static void main(String[] args) {
        Resume r1 = ResumeTestData.createResume("uuid1", "Peter Parker");
        Resume r2 = ResumeTestData.createResume("uuid2", "Normann Osborn");
        SqlStorage storage = (SqlStorage) Config.getInstance().getStorage();
        storage.save(r1);
        storage.save(r2);
    }
}
