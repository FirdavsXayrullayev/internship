package uz.intership.servise.mapper;

//import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.intership.dto.UserDto;
import uz.intership.model.User;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDto toDto(User user){
        return user == null ? null : new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User toEntity(UserDto dto){
        return dto == null ? null : new User(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getRole()
        );
    }
}
