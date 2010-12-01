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

package br.org.acessobrasil.ases.persistencia;

/**
 * Classe para gerar apenas uma instancia do banco 
 *
 */
public class SingBancoSite {
	private static BancoSite bancoSite;

	private static String nomeSite;

	public SingBancoSite(String nomeSite) {

		nomeSite = nomeSite.replaceAll("http://", "");

		int posFirstBarra;
		posFirstBarra = nomeSite.indexOf("/");
		if (posFirstBarra != -1) {
			SingBancoSite.nomeSite = nomeSite.substring(0, posFirstBarra);
		} else {
			SingBancoSite.nomeSite = nomeSite;
		}

	}
	/**
	 * Instancia corrente
	 * @return inst�ncia do banco
	 */
	public static BancoSite getInstancia() {
		if (bancoSite == null) {
			if(nomeSite==null){
				return null;
			}
			System.out.println("getInstancia");
			bancoSite = new BancoSite(nomeSite);
			return bancoSite;
		} else {
			// System.out.println(bancoSite);
			// System.out.println("else\n\tgetInstancia");
			// bancoSite.abreConexaoOuCriaBanco();
			return bancoSite;
		}
	}
	/**
	 * Altera o nome do banco criando outro
	 * @param nomeSite informa o nome do banco
	 */
	public static void setBancoNome(String nomeSite) {
		nomeSite = nomeSite.replaceAll("http://", "");
		nomeSite = nomeSite.replaceAll(":", "-");
		int posFirstBarra;
		posFirstBarra = nomeSite.indexOf("/");
		if (posFirstBarra != -1) {
			SingBancoSite.nomeSite = nomeSite.substring(0, posFirstBarra);
		} else {
			SingBancoSite.nomeSite = nomeSite;
		}
		bancoSite=null;
	}
	/**
	 * Retorna o banco de acordo com o nome
	 * @param nome
	 * @return instancia do banco
	 */
	public static BancoSite getBancoNome(String nome) {
		BancoSite.mantemTabelas = true;
		setBancoNome(nome);
		bancoSite = new BancoSite(nomeSite);
		BancoSite.mantemTabelas = false;
		return bancoSite;
	}

}
