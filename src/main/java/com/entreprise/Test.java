package com.entreprise;

import java.util.List;

import com.entreprise.Repository.IClientR;
import com.entreprise.entities.Client;

public class Test {
	public Test( IClientR i  ) {
		
		List<Client> l = i.findAll() ;
		System.out.println("-----------ça marche-------------") ;
		System.out.println("-----------ça marche-------------") ;
		System.out.println("-----------ça marche-------------") ;
		
	}
	 
}
