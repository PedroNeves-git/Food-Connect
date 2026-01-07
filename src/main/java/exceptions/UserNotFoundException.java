package exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário com o id: '" + id + "' não encontrado.");
    }
}
