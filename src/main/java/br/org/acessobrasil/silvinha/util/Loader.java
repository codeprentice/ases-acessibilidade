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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * Classe respons�vel por recuperar os recursos do sistema.
 * @author Andr� Santos.
 */
class Loader {
	
	private static Logger log = Logger.getLogger("br.org.acessobrasil.silvinha");

	/**
	 * Construtor de Loader.
	 */
	private Loader() {
		super();
		
	}

	/**
	 * M�todo que retorna um InputStream com o conte�do de um recurso.
	 * @param classe Classe que quer utilizar o recurso.
	 * @param recurso Recurso a ser obtido.
	 * @return Se o recurso existir retorna um InputStream, sen�o retorna null.
	 */
	public static InputStream getResourceAsInputStream(final Class classe,
			final String recurso) {
		return classe.getClassLoader().getResourceAsStream(recurso);
	}

	/**
	 * M�todo que retorna um StringBuilder com o conte�do de um recurso.
	 * @param classe Classe que quer utilizar o recurso.
	 * @param recurso Recurso a ser obtido.
	 * @return Se o recurso existir retorna um StringBuilder, sen�o retorna null.
	 */
	public static StringBuilder getResourceAsStringBuilder(final Class classe,
			final String recurso) {
		final InputStream ips = 
			Loader.getResourceAsInputStream(classe, recurso);
		final InputStreamReader ipsr = new InputStreamReader(ips);
        final BufferedReader bfr = new BufferedReader(ipsr);
        StringBuilder string = new StringBuilder();
        StringBuilder nulo = null;

        try {
			while (bfr.ready()) {
				string.append(bfr.readLine());
			}
	        bfr.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return string.length() > 0 ? string : nulo;
	}

	/**
	 * M�todo que retorna uma URL de um recurso.
	 * @param classe Classe que quer utilizar o recurso.
	 * @param recurso Recurso a ser obtido.
	 * @return Se o recurso existir retorna uma URL, sen�o retorna null.
	 */
	public static URL getResourceAsURL(final Class classe,
			final String recurso) {
		return classe.getClassLoader().getResource(recurso);
	}

}
