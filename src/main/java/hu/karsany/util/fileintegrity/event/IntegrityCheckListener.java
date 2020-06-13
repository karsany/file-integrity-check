package hu.karsany.util.fileintegrity.event;

import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

/**
 * Listener for event occur while doing integrity checks.
 * This listener can be registered to a{@link hu.karsany.util.fileintegrity.FileIntegrityCheck} instance.
 */
public interface IntegrityCheckListener {

    /**
     * Method called when the check file is a new one.
     *
     * @param integrityCheckedFile the checked file.
     */
    void newFile(IntegrityCheckedFile integrityCheckedFile);

    /**
     * Method called when the checked file hash has been changed.
     *
     * @param integrityCheckedFile the checked file.
     * @param oldHash              the file's old hash.
     */
    void hashChanged(IntegrityCheckedFile integrityCheckedFile, String oldHash);

    /**
     * Method called when the checked file hash has not been changed.
     *
     * @param integrityCheckedFile the checked file.
     */
    void hashUnchanged(IntegrityCheckedFile integrityCheckedFile);

}
