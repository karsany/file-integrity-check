package hu.karsany.util.fileintegrity.file;

import java.io.File;

public class CachedIntegrityCheckedFile implements IntegrityCheckedFile {

    private final IntegrityCheckedFile integrityCheckedFile;
    private File file = null;
    private String hash = null;

    public CachedIntegrityCheckedFile(IntegrityCheckedFile integrityCheckedFile) {
        this.integrityCheckedFile = integrityCheckedFile;
    }

    @Override
    public File file() {
        if (this.file == null) {
            this.file = this.integrityCheckedFile.file();
        }
        return this.file;
    }

    @Override
    public String hash() {
        if (this.hash == null) {
            this.hash = this.integrityCheckedFile.hash();
        }
        return this.hash;
    }
}
