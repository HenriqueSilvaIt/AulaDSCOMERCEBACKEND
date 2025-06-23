package com.hen.aula.services;

import com.hen.aula.dto.UserDTO;
import com.hen.aula.entities.Role;
import com.hen.aula.entities.User;
import com.hen.aula.projections.UserDetailsProjection;
import com.hen.aula.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


    @Autowired
    public UserRepository repository;

    /* Método para buscar o usuário dado o nome do usuário ele retorna o usuário,
    s n existi vamos lançar a exceção UserNameNotFounException"
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> listUser = repository.searchUserAndRolesByEmail(username);
        if (listUser.size() == 0) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        /*Como o relacionamento é muitos para muitos
         * não é possível trazer os roles dos usuários, somente
         * passando o usuário, porque muitos para muitos
         * o carregamento é Lazy
         * para isso fazemos a consulta do usuário no SQLRAIZ
         *  para buscar o usuário já com os roles dele e com base na consulta, pegamos as informação do role*/
        User user = new User();
        user.setEmail(username);
        user.setPassword(listUser.get(0).getPassword()); //  salva a senha do banco de dados no objeto use
        for (UserDetailsProjection dto : listUser) {
            user.addRole(new Role(dto.getRoleId(), dto.getAuthority()));
        }

        return user;


    }


    /*Protected só pode ser chamado nas classe do pacote service*/
    protected User authenticated() {
        try {
            /*Pega uma objeto abaixo Authentication do Spring Security, se o  usuário
             * estiver authenticado e dentro do objeto authentication é possível
             * fazer um casting getPrincipal para o tipo Jwt na segunda linha e o Jwt
             * Tem o claim que colocamos lá no authorization server que tem username
             *  então a terceira linha abaixo é onde ele pega o email do cara até do claim
             * username*/
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal(); // nome do passwor
            String username = jwtPrincipal.getClaim("username"); /* o email nome do usuário
        do usuário que está no token vai cair nessa variavel*/

            return repository.findByEmail(username).get(); /* o get é para
            pegar a classe user que está dentro do optional
            se ele n encontrar nada ou der algum outro erro o catch
            abaixo vai gerar exceção*/
        } catch (Exception e) {
            throw new UsernameNotFoundException("Email not found");
        }

    }

    /*Método para pegar usuário que está logado  e converte ele para pegar
    somente os campos do UserDTO,  o User vai pegar o método authenticated para pegar
    o usuário com base no token que está guardado no spring security e depois fazer um
    UserDTO a partir disso*/
    @Transactional(readOnly = true)// transaction para garantir que eles vai fazer todo o processo sem interrupção
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }

}