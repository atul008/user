package com.cityfox.user.service;

import com.cityfox.user.dao.RoleRepository;
import com.cityfox.user.dao.UserRepository;
import com.cityfox.user.model.Role;
import com.cityfox.user.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@ConfigurationProperties(prefix = "user")
public class UserService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @NotBlank
  private String passphrase;

  @Autowired
  public UserService(UserRepository userRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public User findUserByUserName(String userName) {
    return userRepository.findByUserName(userName);
  }

  public User saveUser(User user) {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    Role userRole = roleRepository.findByRole("USER");
    List<Role> roles = new ArrayList<>();
    roles.add(userRole);
    if(passphrase.equalsIgnoreCase(user.getPassphrase())){
      Role adminRole = roleRepository.findByRole("ADMIN");
      roles.add(adminRole);
    }
    user.setRoles(new HashSet<Role>(roles));
    return userRepository.save(user);
  }

  public void setPassphrase(String passphrase) {
    this.passphrase = passphrase;
  }
}