package com.entreprise.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUser ; 
	private String username ;
	private String password ;
	private String nom ;
	private String prenom ;
	private String photo ;
	private String roleUser ;


	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogin() {
		return username;
	}

	public void setLogin(String login) {
		this.username = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
	

	public User(String username, String password, String nom, String prenom) {
		super();
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
	}

	

	public User(String login, String password) {
		super();
		this.username = login;
		this.password = password;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(String roleUser) {
		this.roleUser = roleUser;
	}
	
	
	
	
	

}
