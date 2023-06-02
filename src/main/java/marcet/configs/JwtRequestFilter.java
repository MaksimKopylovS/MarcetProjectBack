package marcet.configs;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marcet.bean.JwtTokenUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/*Класс для провекри токена*/
@Component
@RequiredArgsConstructor
@Slf4j
@ActiveProfiles("test")
@CrossOrigin
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenUtil jwtTokenUtil;

    /*Получаем токен из фронта*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*Проверяем есть ли в запросе заголовок с авторизацией*/
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        /*Если заголовок равен нулю и в заголовок начинается с ключевого слова получаем имя по токену*/
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (ExpiredJwtException e) {
                log.debug("The token is expired");
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /*Если имя пользователя не равна, проходит аутентификация пользователя*/
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenUtil.getRoles(jwt)
                            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            SecurityContextHolder.getContext().setAuthentication(token);
        }
        /*запрос и ответ проходит через цепочку фильтров*/
        filterChain.doFilter(request, response);
    }
}

