package com.entreprise.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class NumeroDisque {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDisque ;
	private String numDisque ;
	public long getIdDisque() {
		return idDisque;
	}
	public void setIdDisque(long idDisque) {
		this.idDisque = idDisque;
	}
	public String getNumDisque() {
		return numDisque;
	}
	public void setNumDisque(String numDisque) {
		this.numDisque = numDisque;
	}
	public NumeroDisque(String numDisque) {
		super();
		this.numDisque = numDisque;
	}
	public NumeroDisque() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
