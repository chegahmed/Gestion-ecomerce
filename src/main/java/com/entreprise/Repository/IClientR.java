package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.Client;

public interface IClientR extends JpaRepository<Client, Long> {

}
