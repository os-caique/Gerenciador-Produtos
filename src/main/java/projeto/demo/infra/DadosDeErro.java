package projeto.demo.infra;

import org.springframework.validation.FieldError;

public record DadosDeErro (
        String messagem,
        String campo
) {
    public DadosDeErro(FieldError fieldError) {
        this(fieldError.getDefaultMessage(), fieldError.getField());
    }

}
