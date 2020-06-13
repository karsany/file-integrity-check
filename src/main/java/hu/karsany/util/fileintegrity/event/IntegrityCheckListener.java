package hu.karsany.util.fileintegrity.event;

import hu.karsany.util.fileintegrity.file.IntegrityCheckedFile;

public interface IntegrityCheckListener {

    void newFile(IntegrityCheckedFile integrityCheckedFile);

    void hashChanged(IntegrityCheckedFile integrityCheckedFile, String oldHash);

    void hashUnchanged(IntegrityCheckedFile integrityCheckedFile);

}
