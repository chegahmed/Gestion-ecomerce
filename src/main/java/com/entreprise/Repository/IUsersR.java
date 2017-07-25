package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.User;


public interface IUsersR extends JpaRepository<User, Long> {

}
