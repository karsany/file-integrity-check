package hu.karsany.util.fileintegrity.digest.exception;

/**
 * Custom {@link RuntimeException} thrown when error occurs on
 * {@link hu.karsany.util.fileintegrity.digest.DigestStrategy} related operations.
 */
public class DigestException extends RuntimeException {
    private static final long serialVersionUID = 3401809267408972638L;

    public DigestException(Throwable cause) {
        super(cause);
    }
}
