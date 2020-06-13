package hu.karsany.util.fileintegrity;

import hu.karsany.util.fileintegrity.db.IntegrityDatabase;
import hu.karsany.util.fileintegrity.digest.DigestStrategy;
import hu.karsany.util.fileintegrity.event.IntegrityCheckListener;
import hu.karsany.util.fileintegrity.file.CachedIntegrityCheckedFile;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;

public class FileIntegrityCheck {
    private final IntegrityCheckListener integrityCheckListener;
    private final DigestStrategy digestStrategy;
    private final IntegrityDatabase ib;

    public FileIntegrityCheck(IntegrityCheckListener integrityCheckListener, DigestStrategy digestStrategy, IntegrityDatabase ib) {
        this.integrityCheckListener = integrityCheckListener;
        this.digestStrategy = digestStrategy;
        this.ib = ib;
    }

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
