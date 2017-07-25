package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.ModePaiement;


public interface IModePaiementR extends JpaRepository<ModePaiement, Long> {

}
