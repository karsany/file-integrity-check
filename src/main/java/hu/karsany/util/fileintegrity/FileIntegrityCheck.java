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

    public void check(File file) {

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
                this.logger.logHashUnchanged(integrityCheckedFile);
            } else {
                this.logger.logHashChanged(integrityCheckedFile, fileHash);
                this.ib.save(integrityCheckedFile);
            }
        } else {
            this.ib.save(integrityCheckedFile);
            this.logger.logNewFile(integrityCheckedFile);
        }

    }

}
