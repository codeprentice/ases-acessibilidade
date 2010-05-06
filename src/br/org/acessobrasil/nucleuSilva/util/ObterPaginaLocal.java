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

import static br.org.acessobrasil.silvinha.entidade.NomeArquivoOuDiretorioLocal.nomeArquivoOuDiretorio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;

import br.org.acessobrasil.silvinha.entidade.RelatorioDaUrl;

/**
 * Classe respons�vel pela captura do conte�do de uma p�gina local
 * @author Renato Tomaz Nati
 * Criado em 11/1/2007 
 */
public final class ObterPaginaLocal {
	
	private static Logger log = Logger.getLogger("br.org.acessobrasil.silvinha"); 
	
		
    /**
     * Construtor de PegarPaginaWEB.
     */
    public ObterPaginaLocal(String nomeArquivo) {
    	
    }

    /**
     * M�todo que extrai o conte�do de uma p�gina.
     * @param url URL da p�gina a ter seu conte�do extra�do.
     * @return Conte�do de uma p�gina.
     * @throws IOException Erro ao conectar a p�gina.
     * @deprecated Utilize o m�todo getContent().
     */
    public static StringBuilder pegar(final URL url) throws IOException {
    	//JOptionPane.showMessageDialog(null,"oi1");
    	StringBuilder buf = new StringBuilder();
    	File file = new File(nomeArquivoOuDiretorio); 
    	FileReader reader = new FileReader(file); 
    	BufferedReader leitor = new BufferedReader(reader);
    	

        while (leitor.ready()) {
            buf.append(leitor.readLine() + "\n");
        }

        leitor.close();
   //JOptionPane.showMessageDialog(null,"oi2");
        return buf;
    }

    /**
     * M�todo que extra� o conte�do de uma p�gina web.
     * @param relatorio P�gina que vai ser pesquisada.
     * @throws IOException Erro ao tentar extrair o conte�do da p�gina html.
     */
    public void getContent(final RelatorioDaUrl relatorio) {
    	final int mb = 1024;
    	
    	try {
    		StringBuilder sbd = null;
    		sbd=new StringBuilder();
    		FileInputStream fis = null;
    		ObjectInputStream ois = null;
    		try
    		{
    			//JOptionPane.showMessageDialog(null,"arq = " + relatorio.getUrl()); 
    			File file = new File(relatorio.getUrl());
    			// JOptionPane.showMessageDialog(null,"fileexist");
    			if (file.exists())
    			{
    				
    				fis = new FileInputStream(file);
    				
    				   				
    				byte[] dados = new byte[mb];
        			int bytesLidos = 0;
        			
        			while ((bytesLidos = fis.read(dados)) > 0) {
        				        				sbd.append(new String(dados, 0, bytesLidos));
        			}
        			
        			fis.close();
        		}
    			
    		}
    		catch (Exception e)
    		{
    			log.error(e);
    		}
    		
    		finally
    		{
    			if (fis != null)
    			{
    				try {
    					fis.close();
    				} 
    				catch (Exception e){}
    			}
    			if (ois != null)
    			{
    				try
    				{
    					ois.close();
    				} 
    				catch (Exception e){}
    			}
    		}
    		
    		if (sbd != null)
    		{
    	
    			relatorio.setConteudo(sbd);
    			     		}
    	} catch (Exception e) {
    		
    		log.error(e.getMessage(), e);
    	}
    	 
    }
    
    
    
    /**
     * M�todo que retorna o Content-type de uma p�gina web.
     * @param metodo Uma inst�ncia de org.apache.commons.httpclient.HttpMethod
     * inicializada pela p�gina.
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
}

