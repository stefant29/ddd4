package com.softdignitas.ddd.service.mapper;

import com.softdignitas.ddd.domain.Authority;
import com.softdignitas.ddd.domain.User;
import com.softdignitas.ddd.domain.Utilizator;
import com.softdignitas.ddd.service.dto.AdminUserDTO;
import com.softdignitas.ddd.service.dto.UserDTO;
import com.softdignitas.ddd.service.dto.UtilizatorDTO;
import java.util.*;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    @Value("${ddd.new-user.default-password}")
    private String NEW_USER_DEFAULT_PASSWORD;

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).toList();
    }

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<AdminUserDTO> usersToAdminUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).toList();
    }

    public AdminUserDTO userToAdminUserDTO(User user) {
        return new AdminUserDTO(user);
    }

    public List<User> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
        return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).toList();
    }

    public User userDTOToUser(AdminUserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }

    public Utilizator fromUtilizatorDto(UtilizatorDTO utilizatorDTO) {
        return new Utilizator(utilizatorDTO);
    }

    public User createNewUserFromUtilizatorDto(UtilizatorDTO utilizatorDTO) {
        if (utilizatorDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setLogin(utilizatorDTO.getNume() + "_" + utilizatorDTO.getPrenume());
            user.setPassword(passwordEncoder.encode(NEW_USER_DEFAULT_PASSWORD));
            user.setFirstName(utilizatorDTO.getPrenume());
            user.setLastName(utilizatorDTO.getNume());
            user.setEmail(utilizatorDTO.getEmail());
            user.setActivated(true);
            return user;
        }
    }

    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(string -> {
                        Authority auth = new Authority();
                        auth.setName(string);
                        return auth;
                    })
                    .collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public UserDTO toDtoId(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        return userDto;
    }

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public Set<UserDTO> toDtoIdSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoId(userEntity));
        }

        return userSet;
    }

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public UserDTO toDtoLogin(User user) {
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        return userDto;
    }

    @Named("loginSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    public Set<UserDTO> toDtoLoginSet(Set<User> users) {
        if (users == null) {
            return Collections.emptySet();
        }

        Set<UserDTO> userSet = new HashSet<>();
        for (User userEntity : users) {
            userSet.add(this.toDtoLogin(userEntity));
        }

        return userSet;
    }
}
