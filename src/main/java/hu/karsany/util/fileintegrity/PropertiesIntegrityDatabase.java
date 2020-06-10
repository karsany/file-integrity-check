package hu.karsany.util.fileintegrity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Properties;

public class PropertiesIntegrityDatabase implements IntegrityDatabase {

    private final File propertyFile;
    private Properties properties = null;

    public PropertiesIntegrityDatabase(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    private void load() {
        try {

            if (this.properties == null) {
                this.properties = new Properties();
                if (this.propertyFile.exists()) {
                    this.properties.load(new FileInputStream(this.propertyFile));
                }
            }

        } catch (Exception e) {
            new RuntimeException(e);
        }

    }

    private void flush() {
        try {

            this.properties.save(new FileOutputStream(this.propertyFile), "Refresh at " + LocalDateTime.now());

        } catch (Exception e) {
            new RuntimeException(e);
        }

    }

    @Override
    public boolean exists(File file) {

        synchronized (propertyFile) {
            load();
            return properties.containsKey(file.getAbsolutePath());
        }

    }

    @Override
    public void save(IntegrityCheckedFile file) {

        synchronized (propertyFile) {
            load();
            this.properties.put(file.file().getAbsolutePath(), file.hash());
            flush();
        }


    }

    @Override
    public String getHash(File file) {
        synchronized (propertyFile) {
            load();
            return this.properties.getProperty(file.getAbsolutePath());
        }
    }
}
