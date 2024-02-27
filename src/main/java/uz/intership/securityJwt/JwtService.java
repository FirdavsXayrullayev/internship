package uz.intership.securityJwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.intership.dto.UserDto;
import uz.intership.model.UserSession;
import uz.intership.repository.UserSessionRepo;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {
    @Value(value = "${spring.security.secret.key}")
    private String secretKey;

    @Autowired
    private Gson gson;
    @Autowired
    private UserSessionRepo userSessionRepository;

    public String createToken(UserDto userDto){
        System.out.println("Create tokkkkkkkkkkkkkken");
        String uuid = UUID.randomUUID().toString();
        userSessionRepository.save(new UserSession(uuid, gson.toJson(userDto)));
        return Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .subject(uuid)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public Claims getClaim(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean expired(String token){
        return getClaim(token).getExpiration().getTime() < System.currentTimeMillis();
    }
    public UserDto getSubject(String token){
        String uuid = getClaim(token).getSubject();
        return userSessionRepository.findById(uuid).map(s -> gson.fromJson(s.getInfo(), UserDto.class))
                .orElseThrow(()-> new JwtException("Token is expired"));
    }
}
