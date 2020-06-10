package hu.karsany.util.fileintegrity;

import java.io.File;

public interface IntegrityCheckedFile {

    File file();

    String hash();

}
