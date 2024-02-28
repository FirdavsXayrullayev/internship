package uz.intership.servise.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.intership.dto.LoginDto;
import uz.intership.dto.ResponseDto;
import uz.intership.dto.UserDto;
import uz.intership.model.User;
import uz.intership.repository.UserRepository;
import uz.intership.securityJwt.JwtService;
import uz.intership.servise.UserService;
import uz.intership.servise.mapper.UserMapper;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {
     private final UserRepository userRepository;
     private final UserMapper userMapper;
     private final JwtService jwtService;
     private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDto<UserDto> getById(Integer id) {
        return userRepository.findById(id).filter(p-> p.getIsActive() == 0).map(user ->
                ResponseDto.<UserDto>builder()
                        .code(0)
                        .info("OK")
                        .success(true)
                        .data(userMapper.toDto(user))
                        .build())
                .orElse(ResponseDto.<UserDto>builder()
                        .success(false)
                        .code(-1)
                        .info("Not found")
                        .build());
    }

    @Override
    public ResponseDto<UserDto> addNewUser(UserDto userDto) {
        try {
            Optional<User> u = userRepository.findFirstByEmail(userDto.getEmail());
            if (u.isPresent()) {
                User user = u.get();
                if (user.getIsActive() == 1) {
                    user.setIsActive(0);
                    return ResponseDto.<UserDto>builder()
                            .code(0)
                            .info("OK")
                            .success(true)
                            .data(userMapper.toDto(userRepository.save(user)))
                            .build();
                } else {
                    return ResponseDto.<UserDto>builder()
                            .code(-2)
                            .info("With email already exist")
                            .success(false)
                            .data(userDto)
                            .build();
                }
            }else {
                User user = userMapper.toEntity(userDto);
                if (user.getRole() == null) user.setRole("USER");
                user.setIsActive(0);
                return ResponseDto.<UserDto>builder()
                        .code(0)
                        .info("OK")
                        .success(true)
                        .data(userMapper.toDto(userRepository.save(user)))
                        .build();
            }
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(1)
                    .info("Database Error")
                    .data(userDto)
                    .success(false)
                    .build();
        }

    }

    @Override
    public ResponseDto<UserDto> deleteUserById(Integer id) {
        return userRepository.findById(id).map(user -> {
            if (user.getIsActive() == 0) {
                user.setIsActive(1);
                userRepository.save(user);
                return ResponseDto.<UserDto>builder()
                        .code(0)
                        .info("OK")
                        .data(userMapper.toDto(user))
                        .success(true)
                        .build();
            }else {
                return ResponseDto.<UserDto>builder()
                        .code(-1)
                        .info("Not found")
                        .success(false)
                        .build();
            }
        }).orElse(
                ResponseDto.<UserDto>builder()
                        .code(-1)
                        .info("Not found")
                        .success(false)
                        .build()
        );
    }

    @Override
    public ResponseDto<UserDto> updateUser(UserDto userDto) {
        if (userDto.getId() == null){
            return ResponseDto.<UserDto>builder()
                    .code(-2)
                    .info("Validation Error")
                    .success(false)
                    .data(userDto)
                    .build();
        }
        Optional<User> optionalUserDto = userRepository.findById(userDto.getId());
        if (optionalUserDto.isEmpty()){
            return ResponseDto.<UserDto>builder()
                    .code(-1)
                    .info("Not found")
                    .success(false)
                    .data(userDto)
                    .build();
        }
        User user = optionalUserDto.get();
        if (user.getIsActive() == 1){
            return ResponseDto.<UserDto>builder()
                    .code(-1)
                    .info("Not found")
                    .success(false)
                    .data(userDto)
                    .build();
        }
        if (userDto.getFirstName() != null){
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null){
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getPassword() != null){
            user.setPassword(userDto.getPassword());
        }
        try {
            userRepository.save(user);

            return ResponseDto.<UserDto>builder()
                    .code(0)
                    .info("OK")
                    .data(userMapper.toDto(user))
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<UserDto>builder()
                    .code(-1)
                    .data(userDto)
                    .info("Database Error")
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<String> login(LoginDto loginDto) {
        UserDto users = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(),users.getPassword())){
            return ResponseDto.<String>builder()
                    .info("Password is not correct"+users.getPassword())
                    .code(-2)
                    .build();
        }

        return ResponseDto.<String>builder()
                .success(true)
                .info("OK")
                .data(jwtService.createToken(users))
                .build();
    }

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findFirstByEmail(username);
        if (user.isEmpty()) throw new UsernameNotFoundException("User with email" + username + " is not found");
        return userMapper.toDto(user.get());
    }
}
