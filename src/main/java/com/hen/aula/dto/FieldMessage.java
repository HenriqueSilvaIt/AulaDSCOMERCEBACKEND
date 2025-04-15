package com.hen.aula.dto;

public class FieldMessage {

    //Nome do campo DTO
    private String fieldName;
    // Mensagem de erro para cada campo
    private String message;

    public FieldMessage(String filedName, String message) {
        this.fieldName = filedName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
