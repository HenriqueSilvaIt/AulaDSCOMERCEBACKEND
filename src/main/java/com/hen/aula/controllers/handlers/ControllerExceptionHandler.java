package com.hen.aula.controllers.handlers;
import com.hen.aula.dto.CustomError;
import com.hen.aula.dto.ValidationError;
import com.hen.aula.services.execeptions.DataBaseException;
import com.hen.aula.services.execeptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice/* Em uma classe  com a annotation @ControllerAdvice, podemos definir tratamentos globais para execeçõs espefícas, sem precisar ficar colocando try-catch em várias partes
 do código*/
public class ControllerExceptionHandler {

        // Método para interceptar exceção de recuso não encontrado e criar um body personalizado no http como retorno
        @ExceptionHandler(ResourceNotFoundException.class) // Aqui tem que incluir
        // a exceção personalizada que você quer interceptar que nesse caso é
        // ResourceNotFoundException
        public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
            // o 1º argumento é a exceção personalizada que você quer capturar e o 2º é para pegar o obter a url
            // que for chamada e deu a exceção
            HttpStatus status = HttpStatus.NOT_FOUND;
            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
            // Timestamp: Instant.now(), que trás o horário atual,
            // Statu: status(varivavel do tipo enumerado que criamos acima) colocamos .value para converter para inteiro,
            // Mensagem de erro: o erro, nós colocarmos e.getMessage(), onde e é 1º argumento do método
            // que puxa a mensagem da exceção personalizada
            // Caminho da url: e por último  request.getRequestURI() que é a Url que foi chamada
            return ResponseEntity.status(status).body(err);
        }

    // Método para interceptar exceção de falha de integridade referencial, criar um body personalizado no http como retorno
    @ExceptionHandler(DataBaseException.class) // Aqui tem que incluir
    // a exceção personalizada que você quer interceptar que nesse caso é
    // ResourceNotFoundException
    public ResponseEntity<CustomError> databaseException(DataBaseException e, HttpServletRequest request) {
        // o 1º argumento é a exceção personalizada que você quer capturar e o 2º é para pegar o obter a url
        // que for chamada e deu a exceção
        HttpStatus status = HttpStatus.BAD_REQUEST; // CONSULTAR LISTA "Código de erros comuns"
        // para ver oque mais se adequa a essa exceção
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        // Timestamp: Instant.now(), que trás o horário atual,
        // Statu: status(varivavel do tipo enumerado que criamos acima) colocamos .value para converter para inteiro,
        // Mensagem de erro: o erro, nós colocarmos e.getMessage(), onde e é 1º argumento do método
        // que puxa a mensagem da exceção personalizada
        // Caminho da url: e por último  request.getRequestURI() que é a Url que foi chamada
        return ResponseEntity.status(status).body(err);
    }


    // Método para interceptar exceção de  validação, criar um body personalizado no http como retorno
    @ExceptionHandler(MethodArgumentNotValidException.class) // Aqui tem que incluir
    // a exceção personalizada que você quer interceptar que nesse caso é
    // ResourceNotFoundException
    public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        // o 1º argumento é a exceção personalizada que você quer capturar e o 2º é para pegar o obter a url
        // que for chamada e deu a exceção
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY; // Esse é o 422 caso dado seja inválido, CONSULTAR LISTA "Código de erros comuns"
        // para ver oque mais se adequa a essa exceção
        ValidationError err = new ValidationError(Instant.now(), status.value(), "Dados inválidos", request.getRequestURI()); // dados invalidos é a primeira mensagem
        // depois o detalhe dos dados invalidos vai vir no for abaixo, porque no corpo de http retorna uma lista com informações dos erros no formulário
        // Aqui acima colocamos o ValidationError, porque ele herda do CustomError que é o tipo
        // desse método, como ele é um subtipo de CustomError isso funciona(upcasting/9
        //  o Validation erro diferente do Custom erro, ele aceita inserir itens nessa lista
        for ( FieldError f : e.getBindingResult().getFieldErrors() ){
            // e.getBindingResult().getFieldErrors() // esse campo vai ter uma lista das exceções das
            // anotations (Bean validation) que configuramos para cada  campo, por exemplo caractere tem que ser no minímo 3
            // campo tem que ser positivo e etc
                err.addError(f.getField(), f.getDefaultMessage()); /* ai pegamos o
            nome do campo e mensagem que configuramos lá na validação dentro do DTo*/
        }

        // Timestamp: Instant.now(), que trás o horário atual,
        // Statu: status(varivavel do tipo enumerado que criamos acima) colocamos .value para converter para inteiro,
        // Mensagem de erro: o erro, nós colocarmos e.getMessage(), onde e é 1º argumento do método
        // que puxa a mensagem da exceção personalizada
        // Caminho da url: e por último  request.getRequestURI() que é a Url que foi chamada
        return ResponseEntity.status(status).body(err);
    }
}
