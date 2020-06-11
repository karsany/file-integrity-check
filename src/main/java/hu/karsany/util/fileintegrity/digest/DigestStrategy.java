package hu.karsany.util.fileintegrity.digest;

import java.io.File;

public interface DigestStrategy {

    String hash(File file);

}
