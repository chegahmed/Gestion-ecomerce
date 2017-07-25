package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.CommandeFournisseur;


public interface ICommandeFournisseurR extends JpaRepository<CommandeFournisseur, Long> {

}
