package exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) {

        super("Endereço com o id: '" + id + "' não encontrado.");
    }
}
