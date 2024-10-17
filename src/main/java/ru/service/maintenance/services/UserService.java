package ru.service.maintenance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.service.maintenance.dtos.UsersDto;
import ru.service.maintenance.entyties.Role;
import ru.service.maintenance.entyties.User;
import ru.service.maintenance.repositories.RoleRepository;
import ru.service.maintenance.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionesService regionesService;
    private final RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> FindById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> FindByIdRegiones(Long IdRegion) {
        return userRepository.findById(IdRegion);
    }

    public Collection<User> FindAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void createNewUser(UsersDto usersDto) {
        User user = new User();
        String bcryptCachedPassword = passwordEncoder.encode(usersDto.getPassword());
        user.setFirstName(usersDto.getFirstName());
        user.setLastName(usersDto.getLastName());
        user.setUsername(usersDto.getUsername());
        user.setPassword(bcryptCachedPassword);
        user.setEmail(usersDto.getEmail());
        user.setPhone(usersDto.getPhone());
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER")));
        user.setRegiones(regionesService.FindByTitle(usersDto.getRegionesTitle()).get());

        userRepository.save(user);
    }
}