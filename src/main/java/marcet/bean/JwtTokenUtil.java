package marcet.bean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//Класс генерации токена
@Component
@CrossOrigin
public class JwtTokenUtil {

    /*Получение секретного ключа*/
    @Value("${jwt.secret}")
    private String secret;

    /*Генерация токена*/
    public String generationToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        /*Создаём список ролей доступных пользователю*/
        List<String> rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        /*Добавляем данный список в MAP с клюём ROLE*/
        claims.put("roles", rolesList);

        /*Получаем текущую дату*/
        Date issuedDate = new Date();
        /*Устанавливаем время жизни токена*/
        Date expiredDate = new Date(issuedDate.getTime() + 20 * 60 * 10000);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /*Получение имени пользователя по токену*/
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    /*Получение роли по токену*/
    public List<String> getRoles(String token) {
        return getClaimFromToken(token, (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}

