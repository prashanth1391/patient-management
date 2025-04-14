package code.pkdev.patientservice.exception;

public class EmailAlReadyExistsException extends RuntimeException {
    public EmailAlReadyExistsException(String message) {
        super(message);
    }
}
