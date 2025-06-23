package com.hen.aula.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{


    // Inejeção da lista de Field Message
    private List<FieldMessage> errors = new ArrayList <>();

    //Como é uma classe filha ele precisa do contrutor abaixo herdando os atributos
    // do pai
    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErros() {
        return errors;
    }


    // Método para adicionar mensagem de erro para validação dos campos na classe FieldMessage
    public void addError(String fieldName, String message) {

        /*Remove o error existentente antes de adionar o novo erro
        para n ficar vários erros mostrando na tela (meio que um histórico de erro
         */
        errors.removeIf(x -> x.getFieldName().equals(fieldName));

        errors.add(new FieldMessage(fieldName, message));
    }
}
