package hu.karsany.util.fileintegrity.file;

import java.io.File;

/**
 * An integrity checked file, that holds the base {@link File} and the hash of that file.
 */
public interface IntegrityCheckedFile {

    /**
     * The {@link File} which integrity was checked.
     *
     * @return the base {@link File}.
     */
    File file();

    /**
     * The hash of the base {@link File}, calculated using an implementation of {@link hu.karsany.util.fileintegrity.digest.DigestStrategy}.
     *
     * @return the hash string.
     */
    String hash();

}
