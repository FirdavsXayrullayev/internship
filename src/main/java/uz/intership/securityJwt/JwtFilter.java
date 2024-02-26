package uz.intership.securityJwt;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.intership.dto.UserDto;
import uz.intership.model.User;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final Gson gson;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String aut = request.getHeader("Authorization");
        if (aut != null && aut.startsWith("Bearer ")){
            String token = aut.substring(7);
            if (jwtService.expired(token)){
                response.getWriter().println(gson.toJson(ResponseDto.builder()
                        .code(-2)
                        .message("Token is expired!")
                        .build()));
                response.setContentType("application/json");
                response.setStatus(400);
            }else {
                UserDto userDto = jwtService.getSubject(token);

                UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDto, null, userDto.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(passwordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
