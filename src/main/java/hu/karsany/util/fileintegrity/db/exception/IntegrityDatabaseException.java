package hu.karsany.util.fileintegrity.db.exception;

/**
 * Custom {@link RuntimeException} thrown when error occurs on
 * {@link hu.karsany.util.fileintegrity.db.IntegrityDatabase} related operations.
 */
public class IntegrityDatabaseException extends RuntimeException {

    private static final long serialVersionUID = -1729310734968234585L;

    public IntegrityDatabaseException(Throwable cause) {
        super(cause);
    }
}
