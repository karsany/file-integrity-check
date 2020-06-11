package hu.karsany.util.fileintegrity;

import hu.karsany.util.fileintegrity.db.IntegrityDatabase;
import hu.karsany.util.fileintegrity.digest.DigestStrategy;
import hu.karsany.util.fileintegrity.file.CachedIntegrityCheckedFile;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;
import hu.karsany.util.fileintegrity.logger.IntegrityLogger;

import java.io.File;

public class FileIntegrityCheck {
    private final IntegrityLogger logger;
    private final DigestStrategy digestStrategy;
    private final IntegrityDatabase ib;

    public FileIntegrityCheck(IntegrityLogger logger, DigestStrategy digestStrategy, IntegrityDatabase ib) {
        this.logger = logger;
        this.digestStrategy = digestStrategy;
        this.ib = ib;
    }

    void check(File file) {

        final IntegrityCheckedFile icf = new CachedIntegrityCheckedFile(
                new IntegrityCheckedFile() {
                    @Override
                    public File file() {
                        return file;
                    }

                    @Override
                    public String hash() {
                        return digestStrategy.hash(file);
                    }
                });

        if (ib.exists(file)) {
            if (ib.getHash(file).equals(icf.hash())) {
                logger.logHashUnchanged(file);
            } else {
                logger.logHashChanged(file, ib.getHash(file), icf.hash());
                ib.save(icf);
            }
        } else {
            ib.save(icf);
            logger.logNewFile(file, icf.hash());
        }

    }

}
