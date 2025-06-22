package com.hen.aula.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    public UserRepository repository;

    /* Método para buscar o usuário dado o nome do usuário ele retorna o usuário,
    s n existi vamos lançar a exceção UserNameNotFounException"
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> listUser = repository.searchUserAndRolesByEmail(username);
        if (listUser.size() == 0 ) {
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
            user.addRole(new Role(dto.getRoledId(), dto.getAuthority()));
        }

        return user;


    }
}
