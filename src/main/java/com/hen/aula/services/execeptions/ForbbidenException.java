
package com.hen.aula.services.execeptions;

public class ForbbidenException extends RuntimeException {
/* Para que essa classe seja uma execeção do java que pode
    utilizar o try catch precisa extend/herda a RuntimeExeception
     se fosse herda só Exception ele iria exigir o try catch
     mas RuntimExceptio não exige o try catch
     */

    /*Construtor que exige que informe uma mensagem na hora
   de instanciar o objeto */
    public ForbbidenException(String msg) {
        super(msg);
    }

}
