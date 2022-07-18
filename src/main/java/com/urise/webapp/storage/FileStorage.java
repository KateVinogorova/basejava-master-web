package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer serializationMethod;

    protected FileStorage(File directory, StreamSerializer serializationMethod) {
        Objects.requireNonNull(directory, "must not be null");
        this.serializationMethod = serializationMethod;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            serializationMethod.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException ioException) {
            throw new StorageException("Update resume IO error", resume.getUuid(), ioException);
        }
    }

    @Override
    protected void saveResume(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException ioException) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), ioException);
        }
        updateResume(file, resume);
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return serializationMethod.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException ioException) {
            throw new StorageException("File read error", file.getName(), ioException);
        }
    }

    @Override
    protected File getResumeKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getStorageAsList() {
        File[] files = directory.listFiles();

        if (files == null) {
            throw new StorageException("Directory read error");
        }
        List<Resume> listOfResumes = new ArrayList<>(files.length);
        for (File file : files) {
            listOfResumes.add(getResume(file));
        }
        return listOfResumes;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteResume(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error");
        }
        return list.length;
    }
}
