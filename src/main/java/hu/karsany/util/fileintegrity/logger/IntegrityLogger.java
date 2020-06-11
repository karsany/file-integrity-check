package hu.karsany.util.fileintegrity.logger;

import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

public interface IntegrityLogger {

    void logNewFile(IntegrityCheckedFile integrityCheckedFile);

    void logHashChanged(IntegrityCheckedFile integrityCheckedFile, String oldHash);

    void logHashUnchanged(IntegrityCheckedFile integrityCheckedFile);

}
