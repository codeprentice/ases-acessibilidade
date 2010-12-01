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

package br.org.acessobrasil.nucleuSilva.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.auth.CredentialsProvider;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import br.org.acessobrasil.silvinha.entidade.RelatorioDaUrl;
import br.org.acessobrasil.silvinha.util.TimeOut;
import br.org.acessobrasil.silvinha.vista.frames.mensagens.PaginasNaoAnalisadas;
import br.org.acessobrasil.silvinha.vista.panels.PainelSenha;
import br.org.acessobrasil.silvinha2.util.G_Log;
 
/**
 * Classe respons�vel pela captura do conte�do de uma p�gina na Internet.
 * 
 * @author Acessibilidade Brasil, em 09/11/2005.
 */
public final class PegarPaginaWEB {

	private G_Log log = new G_Log("PegarPaginaWeb.log"); 

	public boolean ativo;

	private HttpClient httpClient;

	private PainelSenha ps;

	public HttpMethod metodo;

	public boolean paginaObtida;
	
	/**
	 * Construtor de PegarPaginaWEB.
	 */
	public PegarPaginaWEB(int a) {
	}

	public PegarPaginaWEB() {
		// me parece que o construtor aqui apenas inicializa alguns parametros e
		// registra o protocolo

		httpClient = new HttpClient();
		ps = new PainelSenha();
		httpClient.getParams().setParameter(CredentialsProvider.PROVIDER, ps); // coloca
																				// no
																				// objeto
																				// httpclient
																				// o
																				// parametro
																				// provider
																				// associado
																				// a ps
		EasySSLProtocolSocketFactory sssl = new EasySSLProtocolSocketFactory();
		// StrictSSLProtocolSocketFactory sssl = new
		// StrictSSLProtocolSocketFactory();
		// sssl.setHostnameVerification(false);
		Protocol easyhttps = new Protocol("https", sssl, 443);
		Protocol.registerProtocol("https", easyhttps);
	}
	/**
	 * Independente de Relatorio
	 * @param url
	 * @return
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws NotHTML
	 * @throws TempoExcedido
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public String getContent(String url) throws HttpException, IOException{
		metodo = new GetMethod(url);
		metodo.setRequestHeader("user-agent", "Mozilla/5.0");
		metodo.setFollowRedirects(true);
		int status = httpClient.executeMethod(metodo);
		String type = getContentType(metodo);
		String location = getLocation(metodo);
		//Verificar os poss�veis erros
		if (status!=HttpStatus.SC_OK){
			//N�o foi aceito, ocorreu um erro 500 404
			if(status==HttpStatus.SC_NOT_FOUND){
			}
			return "";
		}
		if ((status == HttpStatus.SC_OK) && (type.toUpperCase().indexOf("TEXT/HTML") == -1)) {
			//N�o � do tipo texto/html
			return "";
		}
		//Verifica redirecionamento
		if (location!=""){
			//System.out.print(url+" to "+location+"\n");
		}
		String conteudoHTML=metodo.getResponseBodyAsString();
		
		return conteudoHTML;
	}
	/**
	 * Pega o c�digo css
	 * @param url
	 * @return
	 * @throws Exception 
	 */
	public String getCssContent(String url) throws Exception{
		metodo = new GetMethod(url);
		metodo.setRequestHeader("user-agent", "Mozilla/5.0");
		metodo.setFollowRedirects(true);
		int status = httpClient.executeMethod(metodo);
		String location = getLocation(metodo);
		//Verificar os poss�veis erros
		if (status!=HttpStatus.SC_OK){
			//N�o foi aceito, ocorreu um erro 500 404
			if(status==HttpStatus.SC_NOT_FOUND){
			}
			throw new Exception("Erro de http " +status + " para url='"+url+"'");
			//return "";
		}
		//Verifica redirecionamento
		if (location!=""){
			//System.out.print(url+" to "+location+"\n");
		}
		String conteudoHTML=metodo.getResponseBodyAsString();
		
		return conteudoHTML;
	}
	/**
	 * M�todo que extrai o conte�do de uma p�gina.
	 * 
	 * @param url
	 *            URL da p�gina a ter seu conte�do extra�do.
	 * @return Conte�do de uma p�gina.
	 * @throws IOException
	 *             Erro ao conectar a p�gina.
	 * @deprecated Utilize o m�todo getContent().
	 */
	public static StringBuilder pegar(final URL url) throws IOException {
		StringBuilder buf = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(url.openStream());
		BufferedReader in = new BufferedReader(isr);
		while (in.ready()) {
			buf.append(in.readLine() + "\n");
		}

		in.close();
		// aqui simplesmente � lido uma pagina da internet e retornada em buf,
		// sem tratamentos
		return buf;
	}

	/**
	 * M�todo que extra� o conte�do de uma p�gina web.
	 * 
	 * @param url
	 *            P�gina que vai ser pesquisada.
	 * @return Conte�do da p�gina HTML.
	 * @throws IOException
	 *             Erro ao tentar extrair o conte�do da p�gina html.
	 */
	public void getContent(final RelatorioDaUrl relatorio) {
		/*
		 * Melhorar este c�digo!!! 
		 */
		log.addLog("getContent("+relatorio.getUrl()+")\n");
		int status = 9999;
		setAtivo(false);
		metodo = null;
		final int mb = 1024;

		String type = new String();
		InputStream ist = null;
		StringBuilder sbd = null;

		setPaginaObtida(true);

		TimeOut tc = new TimeOut(this);

		setAtivo(true);
		Thread thrTc = new Thread(tc);
		thrTc.start();
		try{
			metodo = new GetMethod(relatorio.getUrl());
			metodo.setRequestHeader("user-agent", "Mozilla/5.0");
			metodo.setFollowRedirects(true);
		}catch(Exception e){
			log.addLog("Erro no GetMetodo: "+e.getMessage()+"\n");
			//Ini - nati code
			colocaNaTabelaErros("timeOut", relatorio);
			setPaginaObtida(false);
			setAtivo(false);
			//Fim - nati code
			metodo=null;
			thrTc=null;
			tc=null;
			return;
		}
		if (!tc.timeOut) {
			// httpClient.setConnectionTimeout(arg0)
			try {
				status = httpClient.executeMethod(metodo);
				type = getContentType(metodo);
				String tam=getContentLength(metodo);
				String location = getLocation(metodo);
				if (location!=""){
					//System.out.print(relatorio.getUrl()+" to "+location+"\n");
				}
				log.addLog("type="+type+" tam="+tam+"\n");
				if ((status == HttpStatus.SC_OK)
						&& (type.toUpperCase().indexOf("TEXT/HTML") > -1)) {
					if (!tc.timeOut) {
						sbd = new StringBuilder();
						//ist = metodo.getResponseBodyAsStream();
					} else {
						colocaNaTabelaErros("timeOut", relatorio);
						setPaginaObtida(false);
						if (!thrTc.interrupted())
							if (thrTc.isAlive())
								thrTc.interrupt();
						setAtivo(false);
					}

					if (!tc.timeOut) {
						/*
						byte[] dados = new byte[mb];
						int bytesLidos = 0;
						
						while ((bytesLidos = ist.read(dados)) > 0) {
							sbd.append(new String(dados, 0, bytesLidos));
						}
						
						ist.close();
						*/
						sbd.append(metodo.getResponseBodyAsString());
					} else {
						colocaNaTabelaErros("timeOut", relatorio);
						setPaginaObtida(false);
						if (!thrTc.interrupted())
							if (thrTc.isAlive())
								thrTc.interrupt();
						setAtivo(false);
					}
					//verifica se existe conte�do
					if (sbd.toString().equals("") || sbd==null || sbd.toString().trim().length()<=1){
						colocaNaTabelaErros("Sem conte�do", relatorio);
						setPaginaObtida(false);
						if (!thrTc.interrupted())
							if (thrTc.isAlive())
								thrTc.interrupt();
						setAtivo(false);
					}
				}else{
					//verifica se o tipo est� errado
					if(type.toUpperCase().indexOf("TEXT/HTML")== -1){
						colocaNaTabelaErros("N�o HTML", relatorio);						
					}else if(status == HttpStatus.SC_NOT_FOUND){
						colocaNaTabelaErros("N�o Encontrado", relatorio);
					}else{
						colocaNaTabelaErros("Status error "+status, relatorio);
					}
					setPaginaObtida(false);
					if (!thrTc.interrupted())
						if (thrTc.isAlive())
							thrTc.interrupt();
					setAtivo(false);
				}
				
				if (!tc.timeOut) {
					metodo.abort(); 
					metodo.releaseConnection();
				} else {
					colocaNaTabelaErros("timeOut", relatorio);
					setAtivo(false);
					setPaginaObtida(false);
					if (!thrTc.interrupted())
						if (thrTc.isAlive())
							thrTc.interrupt();
				}

				if (!thrTc.interrupted())
					if (thrTc.isAlive())
						thrTc.interrupt();

			} catch (Exception e) {
				log.addLog("Erro: "+e.getMessage()+"\n");
				colocaNaTabelaErros("Erro: "+e.getMessage(), relatorio);
				setAtivo(false);
				setPaginaObtida(false);
				if (!thrTc.interrupted())
					if (thrTc.isAlive())
						thrTc.interrupt();
			}
		} else {
			colocaNaTabelaErros("timeOut", relatorio);
			setPaginaObtida(false);
			setAtivo(false);
			if (!thrTc.interrupted())
				if (thrTc.isAlive())
					thrTc.interrupt();
		}
		try{
			metodo.abort(); 
			metodo.releaseConnection();
		}catch(Exception e){
		
		}
		if (sbd != null && (type.toUpperCase().indexOf("TEXT") > -1)
				&& !tc.timeOut && isAtivo()) {
			setAtivo(false);
			//System.out.println("PPW:\n"+sbd.toString());
			relatorio.setConteudo(sbd);
		}
		if (!tc.isTimeOut()) {
			setPaginaObtida(true);
		}

		tc.timeOut = false;
		setAtivo(false);
		if (!thrTc.interrupted())
			if (thrTc.isAlive())
				thrTc.interrupt();
		
		log.addLog("Ok \n");
	}

	/**
	 * M�todo que retorna o Content-type de uma p�gina web.
	 * 
	 * @param metodo
	 *            Uma inst�ncia de org.apache.commons.httpclient.HttpMethod
	 *            inicializada pela p�gina.
	 * @return O Content-Type da p�gina pesquisada.
	 */
	private static String getContentType(final HttpMethod metodo) {
		String type = "";
		Header header = metodo.getResponseHeader("Content-Type");
		if (header != null) {
			type = header.getValue();
		}
		return type;
	}
	
	private static String getContentLength(final HttpMethod metodo){
		String retorno = "";
		Header header = metodo.getResponseHeader("Content-Length");
		if (header != null) {
			retorno = header.getValue().toString();
		}
		return retorno;
	}

	private static String getLocation(final HttpMethod metodo){
		String retorno = "";
		Header header = metodo.getResponseHeader("Location");
		if (header != null) {
			retorno = header.getValue().toString();
		}
		return retorno;
	}
	
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean isPaginaObtida() {
		return paginaObtida;
	}

	public void setPaginaObtida(boolean paginaObtida) {
		this.paginaObtida = paginaObtida;
	}

	/**
	 * Retirar desta classe
	 * @param mensagem
	 * @param relatorio
	 */
	private void colocaNaTabelaErros(String mensagem, RelatorioDaUrl relatorio) {
		RelatorioDaUrl relatorionaoavaliado = new RelatorioDaUrl();
		relatorionaoavaliado.setUrl(relatorio.getUrl());
		PaginasNaoAnalisadas.relatorios.add(relatorionaoavaliado);
		PaginasNaoAnalisadas.mensagens.add(mensagem);
	}
}