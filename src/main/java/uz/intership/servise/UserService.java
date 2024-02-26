package uz.intership.servise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.intership.dto.LoginDto;
import uz.intership.dto.ResponseDto;
import uz.intership.dto.UserDto;
import uz.intership.repository.UserRepository;

public interface UserService {
    ResponseDto<UserDto> getById(Integer id);

    ResponseDto<UserDto> addNewUser(UserDto userDto);

    ResponseDto<UserDto> deleteUserById(Integer id);

    ResponseDto<UserDto> updateUser(UserDto userDto);

    ResponseDto<String> login(LoginDto loginDto);
}
