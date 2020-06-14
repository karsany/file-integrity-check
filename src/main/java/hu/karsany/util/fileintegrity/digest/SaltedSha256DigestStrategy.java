package hu.karsany.util.fileintegrity.digest;

import hu.karsany.util.fileintegrity.digest.exception.DigestException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * {@link DigestStrategy} implementation using SHA-256 as the digest algorithm with the specified salt.
 */
public class SaltedSha256DigestStrategy implements DigestStrategy {

    /**
     * Salt to use when calculating hash.
     */
    private final String salt;

    /**
     * Java platform's digest algorithm provider.
     */
    private final MessageDigest digest;

    public SaltedSha256DigestStrategy(String salt) {
        this.salt = salt;
        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new DigestException(e);
        }
    }


    /**
     * Implementation of {@link DigestStrategy#hash}.
     *
     * @param file file to the hash of.
     * @return the calculated hash.
     * @throws DigestException in case of error.
     * @see DigestStrategy#hash
     */
    @Override
    public String hash(File file) {
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[8192];

            while (bis.read(buffer) > 0) {
                this.digest.update(buffer);
            }

            this.digest.update(this.salt.getBytes());

            byte[] hash = this.digest.digest();
            return Base64.getEncoder()
                         .encodeToString(hash);
        } catch (Exception e) {
            throw new DigestException(e);
        }
    }
}
