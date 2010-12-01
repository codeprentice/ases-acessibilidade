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

package br.org.acessobrasil.silvinha2.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * M�todos comuns com links
 * @author Fabio Issamu Oshiro
 *
 */
public class G_Link {
	
	/**
	 * Converte o caminho relativo para um caminho inteiro
	 * 
	 * @param base caminho do arquivo relativo ex: file:///diretorio/arquivo.html
	 * @param parteLink parte do link relativo ao base imagens/teste.jpg
	 * @return o caminho traduzido
	 * @throws MalformedURLException 
	 * @throws URISyntaxException 
	 */
	public static String getFullPath(String base,String parteLink) throws MalformedURLException, URISyntaxException{
		String res=getSemiFullPath(base,parteLink);
		/* 
		 * Cont�m casos como este teste:
		 * 		http://www.globo.com/nivel1/nivel2/../teste.jpg
		 * 
		 * Converter para
		 * 		http://www.globo.com/nivel1/teste.jpg
		 */
		
		int fim=res.indexOf("../");
		while(fim!=-1){
			int ini = res.lastIndexOf("/",fim-2);
			if(ini!=-1){
				res = res.substring(0,ini+1)+res.substring(fim+3);
				fim=res.indexOf("../");
			}else{
				break;
			}
		}
		//res="";
		
		return res;
	}
	
	/**
	 * Converte o caminho relativo para um caminho inteiro
	 * @param base caminho do arquivo relativo ex: file:///diretorio/arquivo.html
	 * @param parteLink parte do link relativo ao base imagens/teste.jpg
	 * @return o caminho traduzido
	 */
	private static String getSemiFullPath(String base,String parteLink){
		String fPath = "", fname = "";
		URL link = null;
		try {
			link = new URL(base);
			fPath = link.getProtocol() + "://" + link.getHost();
			fname = link.getFile();
			try{
				if(fname.indexOf("/")!=-1){
					String a[]=fname.split("/");
					if(a.length>0){
						fname = a[a.length-1];
					}
				}
			}catch(Exception e){
				System.err.println("fname='"+fname+"'");
				System.err.println("link.getFile()='"+link.getFile()+"'");
				e.printStackTrace();
			}
			//System.out.println("fname="+fname);
			//System.out.println("externalform="+link.toExternalForm());
			
		} catch (MalformedURLException e1) {
			System.out.println("getSemiFullPath(base='"+base+"','" + parteLink +"')");
			e1.printStackTrace();
			return "";
		}
		if(base.startsWith("file:///")){
			/*
			 * Retirar o nome do arquivo
			 */
			String pAtual = link.getPath();
			String protocol = link.getProtocol();
			String a[] = base.split("/");
			fname=a[a.length-1];
			String caminho = pAtual.substring(0, pAtual.length()-fname.length());
			if(parteLink.startsWith("/")){
				return protocol +"://" + caminho + parteLink.substring(1);
			}else{
				return protocol +"://" + caminho + parteLink;
			}
		}
		
		//System.out.print("fPath " + fPath + "\n");
		if (parteLink.startsWith("/")) {
			parteLink = fPath + parteLink;
		} else if (parteLink.startsWith("http://")) {
			// ok
			return parteLink;
		} else {
			if (base.endsWith("/")) {
				//� um folder
				return base + parteLink;
			} else {
				//verificar se � um folder
				String pAtual = base;
				return pAtual.substring(0, pAtual.length() - fname.length()) + parteLink;
			}

		}
		return parteLink;
	}

	public static String getName(String src) {
		if(src.indexOf("/")!=-1){
			String a[] = src.split("/");
			return a[a.length-1];
		}
		if(src.indexOf("\\")!=-1){
			String a[] = src.split("\\");
			return a[a.length-1];
		}
		return src;
	}
}
