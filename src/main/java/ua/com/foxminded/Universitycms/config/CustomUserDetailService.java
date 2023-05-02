package ua.com.foxminded.Universitycms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.foxminded.Universitycms.models.Person;
import ua.com.foxminded.Universitycms.models.enums.Role;
import ua.com.foxminded.Universitycms.repositories.PersonRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final PersonRepository<Person> personRepository;

    @Autowired
    public CustomUserDetailService(PersonRepository<Person> personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User was not found"));
        return new User(person.getEmail(), person.getPassword(), mapRolesToAuthorities(person.getRoles()));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }
}