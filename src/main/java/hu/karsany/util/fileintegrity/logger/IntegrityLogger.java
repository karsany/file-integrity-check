package hu.karsany.util.fileintegrity.logger;

import java.io.File;

public interface IntegrityLogger {

    void logNewFile(File file, String hash);

    void logHashChanged(File file, String oldHash, String newHash);

    void logHashUnchanged(File file);

}
