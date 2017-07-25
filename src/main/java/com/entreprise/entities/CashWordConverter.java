package com.entreprise.entities;

import java.util.StringTokenizer;

public class CashWordConverter {

public static final String[] units = {
    "", "un", "deux", "trois", "quatre", "cinq", "six", "sept",
    "huit", "neuf", "dix", "onze", "douze", "treize", "quatorze",
    "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
};

public static final String[] tens = {
    "", // 0
    "", // 1
    "vingt", // 2
    "trente", // 3
    "quarante", // 4
    "cinquante", // 5
    "soixante", // 6
    "soixante-dix", // 7
    "quatre-vingt", // 8
    "quatre-vingt-dix" // 9
};

public static String doubleConvert(final double n) {
    String pass = n + "";
    StringTokenizer token = new StringTokenizer(pass, ".");
    String first = token.nextToken();
    String last = token.nextToken();
    try {
    	
    	String pass2 ="" ;
    	 for (int i = 0; i < last.length(); i++) {
             String get=convert(Integer.parseInt(last.charAt(i)+""));
             if(get.isEmpty()){
             pass2=pass2+" "+"zero";
             }else{
             pass2=pass2+" "+get;    
             }
         }
    	
    	pass = convert(Integer.parseInt(first))+" ";
    	if(!pass2.equals(" zero")) {
        
        pass=pass+"virgule";
        for (int i = 0; i < last.length(); i++) {
            String get=convert(Integer.parseInt(last.charAt(i)+""));
            if(get.isEmpty()){
            pass=pass+" "+"zero";
            }else{
            pass=pass+" "+get;    
            }
        }
    	}

    } catch (NumberFormatException nf) {
    }
    return pass;
}


public static String convert(final int n) {
    if (n < 0) {
        return "minus " + convert(-n);
    }

    if (n < 20) {
        return units[n];
    }

    if (n < 100) {
        return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
    }

    
    if (n < 1000) {
        return units[n / 100] + " cent " + ((n % 100 != 0) ? " " : "") + convert(n % 100);
    }

    if (n < 1000000) {
        return convert(n / 1000) + " mille " + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
    }

    if (n < 1000000000) {
        return convert(n / 1000000) + " million " + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
    }

    return convert(n / 1000000000) + " millard " + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
  }
}