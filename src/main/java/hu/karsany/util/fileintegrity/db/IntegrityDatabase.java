package hu.karsany.util.fileintegrity.db;

import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;

/**
 * Basic integrity database, that can hold one or more {@link IntegrityCheckedFile}.
 */
public interface IntegrityDatabase {

    /**
     * Checks if the file already exists in the database.
     *
     * @param  file file to check if exists in the database.
     * @return true if the file exists in the database, else return false.
     */
    boolean exists(File file);

    /**
     * Saves the given ${@link IntegrityCheckedFile} to the database.
     *
     * @param file {@link IntegrityCheckedFile} to save to the database.
     */
    void save(IntegrityCheckedFile file);

    /**
     * Gets the hash of the file if exists in the database.
     *
     * @param  file file to get hash of.
     * @return the hash of the file or null if the file has no entry in the {@link IntegrityDatabase}.
     */
    String getHash(File file);
}
