package com.authandautenticate.JwtProject.config;

import com.authandautenticate.JwtProject.entitites.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

//UsernamePasswordAuthenticationFilter é uma das diversas estratégias que temos para authenticar um usuário
//Ao extendermos a nossa classe de UsernamePasswordAuthenticationFilter, podemos então modificar essa estratégia
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/user/login");
    }


    /*
        Como o nome indica attemptAuthentication é a tentativa do usuário se autorizar. Mas como ela ocorre?
        1 - Pegamos o JSON que o usuário enviou e transformamos em um POJO (nada mais é que transformar a informação em uma classe JAVA)
        2 - UsernamePasswordAuthenticationToken pegamos esse cara e criamos um token com as credenciais que o usuário digitou
        2.1 - Essas credenciais são importantes pelo fato que agora as creds estão dentro do securityContext, que é um conceito semelhante ao applicationContext.
        3 - 'Carimbamos' essa credencial gerada no passo anterior. Se estiver ok, retornamos um authenticated=true para dentro de nosso contexto spring. Se não, retornamos um erro
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userEntity.getEmail(),
                    userEntity.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
