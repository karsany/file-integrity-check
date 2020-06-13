package hu.karsany.util.fileintegrity.digest;

import java.io.File;

/**
 * A basic interface for calculating the hash of a {@link File}.
 * The implementations of this interface can use {@link java.security.MessageDigest} to calculate the hash.
 */
public interface DigestStrategy {

    /**
     * Calculates the hash of a {@link File}.
     *
     * @param  file file to the hash of.
     * @return the hash of the file.
     */
    String hash(File file);

}
