package hu.karsany.util.fileintegrity;

import hu.karsany.util.fileintegrity.db.IntegrityDatabase;
import hu.karsany.util.fileintegrity.digest.DigestStrategy;
import hu.karsany.util.fileintegrity.event.IntegrityCheckListener;
import hu.karsany.util.fileintegrity.file.CachedIntegrityCheckedFile;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;
import java.util.Objects;

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
        Objects.requireNonNull(digestStrategy, "The provided DigestStrategy can not be null.");
        Objects.requireNonNull(ib, "The provided IntegrityDatabase can not be null.");

        this.integrityCheckListener = integrityCheckListener;
        this.digestStrategy = digestStrategy;
        this.ib = ib;
    }

    public FileIntegrityCheck(DigestStrategy digestStrategy, IntegrityDatabase ib) {
        this(null, digestStrategy, ib);
    }

    /**
     * Checks the integrity of the given file.
     * This method triggers hash calculation, data saving to {@link IntegrityDatabase}
     * and integrity check events through {@link IntegrityCheckListener}.
     * However integrity check events triggered only if an implementation
     * of {@link IntegrityCheckListener} was provided.
     *
     * @param file file which integrity we want to check.
     */
    public void check(File file) {
        Objects.requireNonNull(file, "The provided file can not be null.");

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
                if (Objects.nonNull(this.integrityCheckListener)) {
                    this.integrityCheckListener.hashUnchanged(integrityCheckedFile);
                }
            } else {
                if (Objects.nonNull(this.integrityCheckListener)) {
                    this.integrityCheckListener.hashChanged(integrityCheckedFile, fileHash);
                }
                this.ib.save(integrityCheckedFile);
            }
        } else {
            this.ib.save(integrityCheckedFile);
            if (Objects.nonNull(this.integrityCheckListener)) {
                this.integrityCheckListener.newFile(integrityCheckedFile);
            }
        }

    }

}
