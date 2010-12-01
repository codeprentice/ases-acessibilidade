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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

import br.org.acessobrasil.silvinha.entidade.RelatorioDaUrl;

/**
 * Extrai os links de uma p�gina 
 *
 */
public class AnotadorDeLinks {
	
	private static ArrayList<String> buscaLinks(String stringURL, Document doc)	{
		ArrayList<String> listaDeLinks = new ArrayList<String>();
		NodeList nl = doc.getElementsByTagName("a");
		for(int i = 0; i < nl.getLength(); i++) {
			String link = ((Element)nl.item(i)).getAttribute("href");
			link = validarURL(stringURL, link);
			if (link != null) {
				listaDeLinks.add(link);
			}
		}
		
		nl = doc.getElementsByTagName("frame");
		for(int i = 0; i < nl.getLength(); i++) {
			String link = ((Element)nl.item(i)).getAttribute("src");
			link = validarURL(stringURL, link);
			if (link != null) {
				listaDeLinks.add(link);
			}
		}
		
		nl = doc.getElementsByTagName("iframe");
		for(int i = 0; i < nl.getLength(); i++) {
			String link = ((Element)nl.item(i)).getAttribute("src");
			link = validarURL(stringURL, link);
			if (link != null) {
				listaDeLinks.add(link);
			}
		}
		return listaDeLinks;
	}
	
	public static ArrayList<String> geraLista(RelatorioDaUrl relatorio)
	throws IOException
	{
		
		ByteArrayInputStream bais = new ByteArrayInputStream(relatorio.getConteudo().toString().getBytes());
		Tidy tidy = new Tidy();
		tidy.setXmlOut(true);
		tidy.setXmlTags(true);
		tidy.setShowWarnings(false);
		tidy.setQuiet(true);
		tidy.setErrout(new PrintWriter(new File("silvinha-tidy.log")));
		Document doc = tidy.parseDOM(bais, null);
		return buscaLinks(relatorio.getUrl(), doc);
	}
	
	private static String validarURL(String urlBase, String link) {
		if (link == null) {
			return null;
		}
		String temp = link.toLowerCase().trim();
		if (temp.equals("")) {
			return null;
		}
		//nao considerar links para arquivos ou extensoes nao aceitas
		for (int i = 0; i < Token.NAOEXTS.length; i++) {
			if (temp.endsWith(Token.NAOEXTS[i])) {
				return null;
			}
		}
		//nao considerar links para email, com protocolos diversos e javascript
		if (temp.contains("javascript") || 
				temp.contains("mailto:") ||
				temp.contains("file://") ||
				temp.contains("ftp://") || 
				temp.contains("news://") ||
				temp.contains("telnet://") ) {
			return null;
		}
		//limpar os links internos
		if (temp.contains("#")) {
			String[] splits = temp.split("#");
			if (splits.length > 0 && !splits[0].trim().equals("")) {
				temp = splits[0].trim();
			} else {
				return null;
			}
		}
		//completa o link
		if (!temp.contains(":")) {
			temp = getAbsoluteUrl(urlBase, temp);
        }
		//TODO IMPLEMENTAR AQUI A TRAVA DE LINKS INDESEJADOS, RETORNANDO NULL
//		System.out.println(temp);
		
		//verifica se o link eh absoluto para outro dominio
		if (temp.startsWith("http") && !temp.contains(Token.URL.getHost() + "/" + Token.URL.getPath())) {
			return null;
		}
//		if (temp.startsWith("http") && !temp.contains(Token.URL_STRING)) {
//			return null;
//		}
		return temp;
	}
	
    /**
     * Retorna o link absoluto dentro da p�gina na URL.
     * @param vURL Dom�nio do site.
     * @param link Link a ser alterado.
     * @return O link completo da p�gina.
     */
    private static String getAbsoluteUrl(final String urlBase, final String link) {
//    	URL vURL = Token.URL;
    	URL vURL = null;
    	try {
    		vURL = new URL(urlBase);
    	} catch (Exception e) {
    		vURL = Token.URL;
    	}
        String str = link;
        /*
         * Link come�a com /.
         */
        if (str.charAt(0) == '/') {
            return vURL.getProtocol() + "://" + vURL.getHost() + str;
        }

        /*
         * Pega a raiz da URL.
         */
        String str2 = vURL.getPath();
        int pos = str2.lastIndexOf('/');
        str2 = str2.substring(0, pos + 1);

        if (str2.length() == 0) {
            str2 = "/";
        }

        /*
         *  no mesmo diret�rio.
         */
        if (str.startsWith("./")) {
            return vURL.getProtocol() + "://" + vURL.getHost()
                + str2 + str.substring(2);
        }

        /*
         * No diret�rio pai.
         */
        while (str.startsWith("../")) {
            /*
             * sobe um diret�rio.
             */
            pos = str2.lastIndexOf('/', pos - 1);

            /*
             * J� est� na raiz.
             */
            if (pos != -1) {
                str2 = str2.substring(0, pos + 1);
            }

            str = str.substring(3);
        }

        return vURL.getProtocol() + "://" + vURL.getHost() + str2 + str;
    }
	
	
//	public static void main(String args[]){
//	try
//	{
//	StringBuilder conteudo = null;
//	URL url = new URL("http://www.dasilva.org.br");
//	if(url != null)
//	{
//	HttpMethod metodo = new GetMethod(url.toString());
//	HttpClient cliente = new HttpClient();
//	String type = null;
//	InputStream ist = null;
//	metodo.setRequestHeader("user-agent", "Mozilla/5.0");
//	int status = cliente.executeMethod(metodo);
//	Header header = metodo.getResponseHeader("Content-Type");
//	if(header != null)
//	type = header.getValue();
//	if(type != null && status == 200 && type.toUpperCase().indexOf("TEXT") > -1)
//	{
//	conteudo = new StringBuilder();
//	ist = metodo.getResponseBodyAsStream();
//	byte dados[] = new byte[1024];
//	for(int bytesLidos = 0; (bytesLidos = ist.read(dados)) > 0;)
//	conteudo.append(new String(dados, 0, bytesLidos));
//	
//	ist.close();
//	}
//	metodo.releaseConnection();
//	}
//	System.out.println(conteudo.toString());
//	String stringURL = url.toString();
//	ArrayList<String> lista = geraLista(stringURL, conteudo);
//	if(lista == null) {
//	System.out.println("lista nula");
//	} else {
//	for(String pagina: lista) {
//	System.out.println(pagina);
//	}
//	}
//	System.out.println("terminou");
//	}
//	catch(Exception e)
//	{
//	System.out.println(e.getMessage());
//	}
//	}
	
	
}
