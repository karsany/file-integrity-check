package hu.karsany.util.fileintegrity;

import hu.karsany.util.fileintegrity.db.IntegrityDatabase;
import hu.karsany.util.fileintegrity.db.PropertiesFileIntegrityDatabase;
import hu.karsany.util.fileintegrity.digest.DigestStrategy;
import hu.karsany.util.fileintegrity.digest.SaltedSha256DigestStrategy;
import hu.karsany.util.fileintegrity.digest.exception.DigestException;
import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;
import hu.karsany.util.fileintegrity.event.IntegrityCheckListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIntegrityCheckTest {

    @Test
    public void test1() throws IOException {

        // example logger for various events
        final IntegrityCheckListener integrityCheckListener = new IntegrityCheckListener() {
            @Override
            public void newFile(IntegrityCheckedFile integrityCheckedFile) {
                System.out.println("New file: " + integrityCheckedFile.file().getAbsolutePath() + " " + integrityCheckedFile.hash());
            }

            @Override
            public void hashChanged(IntegrityCheckedFile integrityCheckedFile, String oldHash) {
                System.out.println("Hash Changed: " + integrityCheckedFile.file().getAbsolutePath() + " from: " + oldHash + " to: " + integrityCheckedFile.hash());
            }

            @Override
            public void hashUnchanged(IntegrityCheckedFile integrityCheckedFile) {
                System.out.println("Hash OK: " + integrityCheckedFile.file().getAbsolutePath());
            }
        };

        // digest strategy - use the SaltedSha256DigestStrategy or implement your own
        final DigestStrategy digest = new SaltedSha256DigestStrategy("THIS_IS_A_SALT_FOR_THE_HASH");

        // integrity database interfae - use the PropertiesIntegrityDatabase or implement your own
        final IntegrityDatabase integrityDB = new PropertiesFileIntegrityDatabase(new File("check.properties"));

        // init the file integrity check
        final FileIntegrityCheck fileIntegrityCheck = new FileIntegrityCheck(integrityCheckListener, digest, integrityDB);

        // check the file, as you want
        fileIntegrityCheck.check(new File("pom.xml"));
        fileIntegrityCheck.check(new File("pom.xml"));
        Assertions.assertThrows(DigestException.class, () -> fileIntegrityCheck.check(new File("not_exists.txt")));

        final FileOutputStream fos1 = new FileOutputStream(new File("changing.txt"));
        fos1.write('x');
        fos1.close();
        fileIntegrityCheck.check(new File("changing.txt"));

        final FileOutputStream fos2 = new FileOutputStream(new File("changing.txt"));
        fos2.write('y');
        fos2.close();
        fileIntegrityCheck.check(new File("changing.txt"));


    }

}
