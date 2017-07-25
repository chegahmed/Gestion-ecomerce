package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.Fournisseur;


public interface IFournisseurR extends JpaRepository<Fournisseur, Long> {

}
