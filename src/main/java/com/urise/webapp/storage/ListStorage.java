package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void updateResume(Integer searchKey, Resume resume) {
        int index = searchKey;
        storage.add(index, resume);
    }

    @Override
    public void saveResume(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    public Resume getResume(Integer searchKey) {
        int index = (int) searchKey;
        return storage.get(index);
    }

    @Override
    public void deleteResume(Integer searchKey) {
        int index = (int) searchKey;
        storage.remove(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Integer getResumeKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return storage;
    }
}
