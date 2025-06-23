package com.hen.aula.services;

import com.hen.aula.entities.User;
import com.hen.aula.services.execeptions.ForbbidenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
        /*Classe com regras para controle de acesso*/

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(long userId){
        User user = userService.authenticated();

        if (!user.hasRole("ROLE_ADMIN") && !user.getId().equals(userId)) { /*se o
         usuário n possuir o role admin e não for o usuário do argumento que chega nessa método
         userId, ele vai receber acesso negado para n conseguir acessar um pedido que n é dele*/

            throw new ForbbidenException("Access denied");
        }

    }

}
