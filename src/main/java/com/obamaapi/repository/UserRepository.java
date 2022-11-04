package com.obamaapi.repository;

import com.obamaapi.enums.Roles;
import com.obamaapi.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByEmail(String Email);
    UserDetails findByContactNumber(String ContactNumber);
    UserDetails findByContactNumberAndRole(String ContactNumber, Roles roles);
    List<UserDetails> findAllByRole(Roles roles);
    UserDetails findByUserId(long userId);
}
