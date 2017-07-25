package com.entreprise.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entreprise.entities.BonLivraison;


public interface IBonLivraisonR extends JpaRepository<BonLivraison, Long> {

}
