package hu.karsany.util.fileintegrity.db;

import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;

public interface IntegrityDatabase {

    boolean exists(File file);

    void save(IntegrityCheckedFile file);

    String getHash(File file);
}
