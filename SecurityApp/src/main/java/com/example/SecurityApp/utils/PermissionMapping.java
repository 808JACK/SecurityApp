package com.example.SecurityApp.utils;

import com.example.SecurityApp.entities.enums.Permissions;
import com.example.SecurityApp.entities.enums.Roles;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.SecurityApp.entities.enums.Permissions.*;
import static com.example.SecurityApp.entities.enums.Roles.*;

public class PermissionMapping {

    private static Map<Roles, Set<Permissions>> map = Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(USER_VIEW,POST_VIEW,USER_UPDATE,POST_UPDATE),
            ADMIN,Set.of(USER_VIEW,POST_VIEW,USER_UPDATE,POST_UPDATE,POST_DELETE)
    );
    public static Set<SimpleGrantedAuthority> getAuthorityForRole(Roles role){
        return map.get(role).stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }
}
