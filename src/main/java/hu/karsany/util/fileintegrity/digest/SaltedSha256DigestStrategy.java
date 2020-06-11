package hu.karsany.util.fileintegrity.digest;

import hu.karsany.util.fileintegrity.digest.DigestStrategy;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Base64;

public class SaltedSha256DigestStrategy implements DigestStrategy {

    final String salt;

    public SaltedSha256DigestStrategy(String salt) {
        this.salt = salt;
    }


    @Override
    public String hash(File file) {
        try {
            byte[] buffer = new byte[8192];
            int count;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            while ((count = bis.read(buffer)) > 0) {
                digest.update(buffer, 0, count);
            }
            bis.close();

            digest.update(salt.getBytes());

            byte[] hash = digest.digest();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
