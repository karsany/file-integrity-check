package hu.karsany.util.fileintegrity;

import hu.karsany.util.fileintegrity.db.IntegrityDatabase;
import hu.karsany.util.fileintegrity.digest.DigestStrategy;
import hu.karsany.util.fileintegrity.event.IntegrityCheckListener;
import hu.karsany.util.fileintegrity.file.CachedIntegrityCheckedFile;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;

/**
 * This class manages file integrity checks using the given {@link DigestStrategy},
 * {@link IntegrityDatabase} and {@link IntegrityCheckListener} implementations.
 *
 * @see IntegrityDatabase
 * @see DigestStrategy
 * @see IntegrityCheckListener
 */
public class FileIntegrityCheck {

    /**
     * {@link IntegrityCheckListener} that can handle integrity check events.
     */
    private final IntegrityCheckListener integrityCheckListener;

    /**
     * {@link DigestStrategy} to use for integrity check with file hashing.
     */
    private final DigestStrategy digestStrategy;

    /**
     * {@link IntegrityDatabase} to save the checked files information to.
     */
    private final IntegrityDatabase ib;

    public FileIntegrityCheck(IntegrityCheckListener integrityCheckListener, DigestStrategy digestStrategy, IntegrityDatabase ib) {
        this.integrityCheckListener = integrityCheckListener;
        this.digestStrategy = digestStrategy;
        this.ib = ib;
    }

    /**
     * Checks the integrity of the given file.
     * This method triggers hash calculation, data saving to {@link IntegrityDatabase}
     * and integrity check events through {@link IntegrityCheckListener}.
     *
     * @param file file which integrity we want to check.
     */
    void check(File file) {

        final IntegrityCheckedFile integrityCheckedFile = new CachedIntegrityCheckedFile(
                new IntegrityCheckedFile() {
                    @Override
                    public File file() {
                        return file;
                    }

                    @Override
                    public String hash() {
                        return FileIntegrityCheck.this.digestStrategy.hash(file);
                    }
                });

        if (this.ib.exists(file)) {
            String fileHash = this.ib.getHash(file);

            if (fileHash.equals(integrityCheckedFile.hash())) {
                this.integrityCheckListener.hashUnchanged(integrityCheckedFile);
            } else {
                this.integrityCheckListener.hashChanged(integrityCheckedFile, fileHash);
                this.ib.save(integrityCheckedFile);
            }
        } else {
            this.ib.save(integrityCheckedFile);
            this.integrityCheckListener.newFile(integrityCheckedFile);
        }

    }

}
