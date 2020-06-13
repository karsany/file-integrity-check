package hu.karsany.util.fileintegrity.db;

import hu.karsany.util.fileintegrity.db.exception.IntegrityDatabaseException;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Properties;

/**
 * Properties file based implementation of {@link IntegrityDatabase},
 * that uses properties file to hold the data of one or more {@link IntegrityCheckedFile}.
 */
public class PropertiesFileIntegrityDatabase implements IntegrityDatabase {

    /**
     * Properties file to use as a database.
     */
    private final File propertyFile;
    /**
     * Properties loaded from the properties file.
     */
    private Properties properties;

    public PropertiesFileIntegrityDatabase(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    /**
     * Load properties from the specified file.
     * If the file is already loaded, than this method won't do anything.
     */
    private void load() {
        try {

            if (this.properties == null) {
                this.properties = new Properties();
                if (this.propertyFile.exists()) {
                    this.properties.load(new FileInputStream(this.propertyFile));
                }
            }

        } catch (Exception e) {
            throw new IntegrityDatabaseException(e);
        }

    }

    /**
     * Writes the last refresh datetime to the properties file as a comment.
     */
    private void flush() {
        try (OutputStream out = new FileOutputStream(this.propertyFile)) {

            this.properties.store(out, "Refresh at " + LocalDateTime.now());

        } catch (Exception e) {
            throw new IntegrityDatabaseException(e);
        }

    }

    /**
     * Checks if the properties file already contains an entry about the give {@link File}.
     *
     * @param file file to check if has entry in the properties file.
     * @return true if the properties file has entry about the given file, else returns false.
     */
    @Override
    public boolean exists(File file) {

        synchronized (this.propertyFile) {
            this.load();
            return this.properties.containsKey(file.getAbsolutePath());
        }

    }

    /**
     * Saves an entry to the properties file about the given {@link IntegrityCheckedFile}.
     * If the file already contains and entry, this method will overwrite it.
     *
     * @param file {@link IntegrityCheckedFile} to save an entry about.
     */
    @Override
    public void save(IntegrityCheckedFile file) {

        synchronized (this.propertyFile) {
            this.load();
            this.properties.put(file.file()
                                    .getAbsolutePath(), file.hash());
            this.flush();
        }


    }

    /**
     * Get saved hash from the properties file.
     *
     * @param file file to get hash of.
     * @return the saved hash.
     */
    @Override
    public String getHash(File file) {
        synchronized (this.propertyFile) {
            this.load();
            return this.properties.getProperty(file.getAbsolutePath());
        }
    }
}
