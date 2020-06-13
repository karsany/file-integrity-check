package hu.karsany.util.fileintegrity.file;

import java.io.File;

/**
 * This class adds basic caching mechanism to {@link IntegrityCheckedFile}
 * holding checked file and the calculated hash.
 */
public class CachedIntegrityCheckedFile implements IntegrityCheckedFile {

    /**
     * {@link IntegrityCheckedFile} to cache.
     */
    private final IntegrityCheckedFile integrityCheckedFile;

    /**
     * {@link IntegrityCheckedFile}'s cached base file.
     */
    private File file;

    /**
     * {@link IntegrityCheckedFile}'s files cached hash string.
     */
    private String hash;

    public CachedIntegrityCheckedFile(IntegrityCheckedFile integrityCheckedFile) {
        this.integrityCheckedFile = integrityCheckedFile;
    }

    /**
     * Method returns the base file of the {@link IntegrityCheckedFile} with basic caching.
     * On the first call if the cached file is null, this method sets the file from the {@link IntegrityCheckedFile}
     * after that the file from the cache will be returned.
     *
     * @return {@link IntegrityCheckedFile}'s base file.
     */
    @Override
    public File file() {
        if (this.file == null) {
            this.file = this.integrityCheckedFile.file();
        }
        return this.file;
    }

    /**
     * Method returns the hash of the {@link IntegrityCheckedFile} with basic caching.
     * On the first call if the cached hash is null, this method sets the hash from the {@link IntegrityCheckedFile}
     * after that the hash from the cache will be returned.
     *
     * @return {@link IntegrityCheckedFile}'s calculated hash.
     */
    @Override
    public String hash() {
        if (this.hash == null) {
            this.hash = this.integrityCheckedFile.hash();
        }
        return this.hash;
    }
}
