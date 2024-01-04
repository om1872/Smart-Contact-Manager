package com.smartcontactmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.entities.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	 public User findByEmail(String email);
}
