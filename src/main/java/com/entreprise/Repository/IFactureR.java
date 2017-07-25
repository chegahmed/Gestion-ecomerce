package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.Facture;

public interface IFactureR extends JpaRepository<Facture, Long> {

}
