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

package br.org.acessobrasil.silvinha.autenticador;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import br.org.acessobrasil.silvinha.entidade.Login;
import br.org.acessobrasil.silvinha.util.PropertyLoader;
import br.org.acessobrasil.silvinha.util.Token;

/**
 * Autenticador para o servidor
 * @author Acessibilidade Brasil.
 */
public class AutenticadorConector {

	private String url;
	
	private static Logger log = Logger.getLogger("br.org.acessobrasil.silvinha");
	
	public AutenticadorConector() throws IOException {
   	
		PropertyLoader pl = new PropertyLoader();
		Properties props = pl.loadProperty(this);
		url = props.getProperty("server");
	}
	
	public AutenticadorConector(String url) {
		
		this.url = url;
	}

	public boolean autenticarLogin(Login login, String url) {
		this.url = url;
		return autenticarLogin(login);
	}

	public boolean autenticarLogin(Login login) {
		boolean autenticado = false;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		NameValuePair x1 = new NameValuePair("x1" , login.getUser());
		NameValuePair x2 = new NameValuePair("x2" , login.getPass());
        method.setRequestBody(new NameValuePair[] {x1, x2});		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + method.getStatusLine());
			}
			
			// Read the response body.
			byte[] responseBody = method.getResponseBody();
			String rb = new String(responseBody).trim();
			// Deal with the response.
			if (rb.startsWith("OK")) {
				autenticado = true;
				String[] rbLines = rb.split("\n");
				Token.setUrl(rbLines[1]);
			}
		} catch (HttpException he) {
			log.error("Fatal protocol violation: " + he.getMessage(), he);
		} catch (IOException ioe) {
			log.error("Fatal transport error: " + ioe.getMessage(), ioe);
		} finally {
			/*
			 *  Release the connection.
			*/
			method.releaseConnection();
		}

		return autenticado;
	}
}
