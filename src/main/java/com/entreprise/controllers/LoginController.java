package com.entreprise.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entreprise.metier.IComMetier;

@Configuration
@Controller
public class LoginController {
	
	@Autowired
	private IComMetier metier ;
	

	@RequestMapping("/login.html")
	public String login(){
		
		String NEWLINE = System.getProperty("line.separator");
	    StringBuffer buffer = new StringBuffer();
	    try{

	      Process pb = new ProcessBuilder("cmd","/c", "vol").start();  
	      InputStream in = pb.getInputStream();  
	      BufferedReader br = new BufferedReader(new InputStreamReader(in));  
	      String line;  
	      while ((line = br.readLine()) != null) {  
	        buffer.append(line + NEWLINE);  
	      }
	    }
	    catch(Exception e){e.printStackTrace();
	    }
	    
	  //  String resultat = buffer.toString().substring(buffer.toString().length() - 11) ;
	    
	    String resultat = buffer.toString().substring(buffer.toString().length() - 11 ,buffer.toString().length()-2) ;

	    System.out.println("le resultat est "+resultat) ;
	    String un ="1" ;
	    if(verifSerie2(resultat).equals(un)) {
	    	
	    	return "login" ;
	    }
	    
	    else {
	    	
	    	return "erreur" ;
	    	
	    }
		
	}
	
	@RequestMapping("/logout")
	public String logout(){
		return "login" ;
	}
	
	@RequestMapping("/erreur")
	public String erreur(){
		return "erreur.html" ;
	}
	
	@ResponseBody
	@RequestMapping("/verifSerie2")
	public String verifSerie2(String num) {
		return metier.verifSerie(num);
	}

}
