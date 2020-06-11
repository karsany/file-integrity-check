package hu.karsany.util.fileintegrity.digest;

import hu.karsany.util.fileintegrity.digest.exception.DigestException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SaltedSha256DigestStrategy implements DigestStrategy {

    private final String salt;
    private final MessageDigest digest;

    public SaltedSha256DigestStrategy(String salt) {
        this.salt = salt;
        try {
            this.digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new DigestException(e);
        }
    }


    @Override
    public String hash(File file) {
        try {
            byte[] buffer = new byte[8192];
            int count;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(buffer)) > 0) {
                this.digest.update(buffer, 0, count);
            }
            bis.close();

            this.digest.update(this.salt.getBytes());

            byte[] hash = this.digest.digest();
            return Base64.getEncoder()
                         .encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
