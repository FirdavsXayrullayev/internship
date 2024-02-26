package uz.intership.securityJwt;

import java.util.ArrayList;
import java.util.List;

import static uz.intership.securityJwt.UserPermission.*;

public enum UserRole {
    SUPER_ADMIN(List.of(READ, CREATE, UPDATE, DELETE)),
    ADMIN(List.of(READ, CREATE, UPDATE)),
    USER(List.of(READ)),
    MODERATOR(List.of(READ, CREATE));

    final List<UserPermission> permissionList;

    UserRole(List<UserPermission> permissionList) {
        this.permissionList = permissionList;
    }
    public List<String> getPermissions(){
        List<String> list = new ArrayList<>(this.permissionList.stream()
                .map(UserPermission::getName)
                .toList());
        list.add("ROLE_" + this.name());
        return list;
    }
}
