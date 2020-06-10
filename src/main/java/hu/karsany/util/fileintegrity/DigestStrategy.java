package hu.karsany.util.fileintegrity;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface DigestStrategy {

    String hash(File file);

}
