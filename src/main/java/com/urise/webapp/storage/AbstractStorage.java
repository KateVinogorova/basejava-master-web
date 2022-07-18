package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public Comparator<Resume> COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    /**
     * Searches for the resume with the same uuid, updates if found
     *
     * @param resume - resume we want to insert into storage instead of the existing
     */
    @Override
    public void update(Resume resume) {
        LOG.info("update " + resume);
        SK searchKey = getKeyExist(resume.getUuid());
        updateResume(searchKey, resume);
    }

    /**
     * Saves new Resume to storage if it isn't already there
     *
     * @param resume - Resume to save
     * @throws ExistStorageException if Resume is already exists
     */
    @Override
    public final void save(Resume resume) {
//        LOG.info("save " + resume);
        SK searchKey = getKeyNotExist(resume.getUuid());
        saveResume(searchKey, resume);
    }

    /**
     * Searches for the Resume with passed uuid and returns this Resume if found.
     * If Resume with passed uuid doesn't exist, displays information about it.
     *
     * @param uuid - unique id for searching of Resume
     * @return Resume with uuid equal to the passed parameter
     */
    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        SK searchKey = getKeyExist(uuid);
        return getResume(searchKey);
    }

    /**
     * Deletes Resume with passed uuid from storage and shifts the following elements
     * after deleted by index - 1
     * If Resume with passed uuid doesn't exist,
     * displays information about it and did not make any changes
     *
     * @param uuid - unique String id
     */
    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        SK searchKey = getKeyExist(uuid);
        deleteResume(searchKey);
    }

    private SK getKeyNotExist(String uuid) {
        SK key = getResumeKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " is already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getKeyExist(String uuid) {
        SK key = getResumeKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " is not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted ");
        List<Resume> list = getStorageAsList();
        list.sort(COMPARATOR);
        return list;
    }

    protected abstract void updateResume(SK searchKey, Resume resume);

    protected abstract void saveResume(SK searchKey, Resume resume);

    protected abstract void deleteResume(SK searchKey);

    protected abstract Resume getResume(SK searchKey);

    protected abstract SK getResumeKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getStorageAsList();
}
