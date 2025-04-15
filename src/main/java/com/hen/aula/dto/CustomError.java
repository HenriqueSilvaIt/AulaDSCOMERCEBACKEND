package com.hen.aula.dto;

import java.time.Instant;

public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    //Como só vai ter métodos get não precisa do construtor padrão
    // só o construtor com argumentos, dessa forma
    // estamos exigindo que os dados sejam informados na hora de instanciar o objeto

    // Construtor com argumentos
    public CustomError(Instant timestamp, Integer status, String error, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.path = path;

    }

    public Instant getTimeStamp() { return timestamp;}
    public Integer getStatus() {return status;}
    public String getError() {return error;}
    public String getPath() { return path;}
}
