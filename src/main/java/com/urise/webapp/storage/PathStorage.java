package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path path;
    private final StreamSerializer serializationMethod;

    PathStorage(String dir, StreamSerializer serializationMethod) {
        Objects.requireNonNull(dir, "directory must not be null");
        path = Paths.get(dir);
        this.serializationMethod = serializationMethod;
        if (!Files.isDirectory(path) || !Files.isWritable(path) || !Files.isReadable(path)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable or is not readable");
        }
    }

    @Override
    protected void updateResume(Path searchKey, Resume resume) {
        try {
            serializationMethod.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(searchKey)));
        } catch (IOException ioException) {
            throw new StorageException("Path write error", resume.getUuid(), ioException);
        }
    }

    @Override
    protected void saveResume(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (FileAlreadyExistsException e) {
            throw new ExistStorageException(resume.getUuid());
        } catch (IOException ioException) {
            throw new StorageException("Couldn't create path " + path, getFileName(path), ioException);
        }
        updateResume(searchKey, resume);
    }

    @Override
    protected void deleteResume(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException ioException) {
            throw new StorageException("Path delete error", getFileName(path), ioException);
        }
    }

    @Override
    protected Resume getResume(Path searchKey) {
        try {
            return serializationMethod.doRead(new BufferedInputStream(Files.newInputStream(searchKey)));
        } catch (IOException ioException) {
            throw new StorageException("Couldn't read path", getFileName(path), ioException);
        }
    }

    @Override
    protected Path getResumeKey(String uuid) {
        return path.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.isRegularFile(searchKey);
    }

    @Override
    protected List<Resume> getStorageAsList() {
        return getFilesList().map(this::getResume).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFilesList().forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getFilesList().count();
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }

    private Stream<Path> getFilesList() {
        try {
            return Files.list(path);
        } catch (IOException ioException) {
            throw new StorageException("Directory read error", ioException);
        }
    }
}
