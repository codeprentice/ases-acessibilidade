/*******************************************************************************
 * Copyright 2005, 2006, 2007, 2008 Acessibilidade Brasil
 * Este arquivo � parte do programa ASES - Avaliador e Simulador para AcessibilidadE de S�tios
 * O ASES � um software livre; voc� pode redistribui-lo e/ou modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como
 * publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o posterior.
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUA��O a qualquer  MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2005, 2006, 2007 Acessibilidade Brasil.
 * 
 * This file is part of ASES.
 *
 * ASES is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * A copy of the license can be found at 
 * http://www.gnu.org/copyleft/lesser.txt.
 *******************************************************************************/

package br.org.acessobrasil.silvinha.util;


import java.util.*;
import java.io.*;

/**
 * Faz algo parecido com "dir /s *.html" (no caso Windows) ou
 * find . -name '*.html' -print (no caso Unix). 
 * Cuidado: se houver um arquivo "XXX.HTML" (em mai�sculas) n�o vai
 * reconhecer, a menos que voc� explicite na express�o regular.
 */
public class BuscaHtmlemDiretorio {
	/**
	 * O diret�rio inicial.
	 * Uma express�o regular que deve ser aplicada
	 * ao nome do arquivo.
    */
	public BuscaHtmlemDiretorio(){};
	
	public ArrayList<File> findFiles (final File startingDirectory, final String pattern) {
		
		ArrayList<File> files = new ArrayList<File>();
		 if (startingDirectory.isDirectory()) {
		      File[] sub = startingDirectory.listFiles(new FileFilter() { // listfiles lista os arquivos dentro de algo, o m�todo implementaod � sempre chamado por esse m�todo
			      public boolean accept (File pathname) {
				      
			    	  //faz com que so seja retornado se atender os requisitos abaixo
			    	  //a classe accept fica sempre se repetindo
			    	  return pathname.isDirectory() || pathname.getName().matches (pattern);
				  }
			  });
			  for (File fileDir: sub) {
			      if (fileDir.isDirectory()) {
				       files.addAll (findFiles (fileDir, pattern));
				  } else {
				       files.add (fileDir); // 
				  }
			  }
		 }
		 return files;
	}
    /**
	 * 
	 */
  //  public static void main(String[] args) {
	 //   ExemploDir ed = new ExemploDir();
		// Listando todos os arquivos "*.html"
	 //   System.out.println (ed.findFiles (new File("."), ".*\\.html"));
		// Listando todos os arquivos "*.java" ou "abs*.txt"
	//    System.out.println (ed.findFiles (new File("."), "(.*\\.java|abs.*\\.txt)"));
		// Listando todos os arquivos "*.htm*" (incluindo arquivos *.HTM) - �til para Windows
	 //   System.out.println (ed.findFiles (new File("."), "(?i).*\\.htm[^.]*"));
		// Listando todos os arquivos "*.htm*" e "*.jsp" 
		// (incluindo arquivos *.HTM e *.JSP) no diret�rio C:\INETPUB - �til para Windows
	   // System.out.println (ed.findFiles (new File("C:/INETPUB"), "(?i)(.*\\.htm[^.]*|.*\\.jsp)"));
//	}
	
}