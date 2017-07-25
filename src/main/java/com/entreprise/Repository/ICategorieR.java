package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.Categorie;


public interface ICategorieR extends JpaRepository<Categorie, Long> {

}
