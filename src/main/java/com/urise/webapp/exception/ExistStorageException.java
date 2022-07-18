package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(String uuid) {
        super("Resume with uuid \"" + uuid + "\" is already exist", uuid);
    }
}
