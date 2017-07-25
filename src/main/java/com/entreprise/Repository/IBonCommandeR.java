package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.CommandeClient;


public interface IBonCommandeR extends JpaRepository<CommandeClient, Long> {

}
