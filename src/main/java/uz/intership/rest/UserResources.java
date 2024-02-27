package uz.intership.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.intership.dto.LoginDto;
import uz.intership.dto.ResponseDto;
import uz.intership.dto.UserDto;
import uz.intership.servise.UserService;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserResources {
    private final UserService userService;
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping("get-by-id")
    public ResponseDto<UserDto> getById(@RequestParam Integer id){
        System.out.println("gettttttttttttttttttttt");
        return userService.getById(id);
    }
    @PostMapping("add-new-user")
    public ResponseDto<UserDto> addNewUser(@RequestBody UserDto userDto){
        return userService.addNewUser(userDto);
    }
    @DeleteMapping("delete-by-id")
    public ResponseDto<UserDto> deleteUserById(@RequestParam Integer id){
        return userService.deleteUserById(id);
    }
    @PatchMapping("update")
    public ResponseDto<UserDto> updateUser(@RequestBody UserDto userDto){
        return userService.updateUser(userDto);
    }

    @PostMapping("login-user")
    public ResponseDto<String> loginUser(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }
}
