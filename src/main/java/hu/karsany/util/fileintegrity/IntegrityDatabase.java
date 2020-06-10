package hu.karsany.util.fileintegrity;

import java.io.File;

public interface IntegrityDatabase {

    boolean exists(File file);

    void save(IntegrityCheckedFile file);

    String getHash(File file);
}
