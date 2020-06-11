package hu.karsany.util.fileintegrity.file;

import java.io.File;

public interface IntegrityCheckedFile {

    File file();

    String hash();

}
