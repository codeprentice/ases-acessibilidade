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

package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.imagem.analise_geral;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.org.acessobrasil.ases.persistencia.BancoSite;
import br.org.acessobrasil.ases.persistencia.SingBancoSite;
import br.org.acessobrasil.ases.regras.RegrasHardCodedEmag;
import br.org.acessobrasil.silvinha.vista.panels.PainelStatusBar;
import br.org.acessobrasil.silvinha2.util.G_Link;

/**
 * Coloca na tabela imagem as imagens a serem corrigidas
 * que est�o em diferentes p�ginas
 * @author Renato Tomaz Nati &amp; Fabio Issamu Oshiro
 */
public class AnaliseGeral {

	int progresso;

	int incrementoExtra;
	
	private static boolean tabelaCriada = false;
	
	/**
	 * inicializa as variaveis
	 * 
	 */
	private void init() {
		// new SingBancoSite("sdf");
		// bancoSite = SingBancoSite.getInstancia();
		progresso = 0;
		incrementoExtra=0;
	}

	
	
	/**
	 * 
	 * @param tag
	 *            tag img que possui src
	 * @param endPagina
	 *            e o endere�o da pagina onde ocorreu
	 */

	public AnaliseGeral(String tag, String endPagina) {
		init();

		BancoSite banco = SingBancoSite.getInstancia();
		
		try {
			if(tabelaCriada){
				//s� apaga os registros
				banco.delTabelaImagem();
			}else{
				banco.criaTabelaImagem();
				tabelaCriada = true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			RegrasHardCodedEmag regra = new RegrasHardCodedEmag();
			String src =regra.getAtributo(tag, "src");
			String pathImage = G_Link.getFullPath(endPagina, src);
			String nomeDaImagem = G_Link.getName(src);
			int total = banco.getTotalImagem(nomeDaImagem);
			ResultSet rs = banco.getImagensErro(nomeDaImagem);
			//System.out.println("\nTotal de imagens = '"+total+"'");
			while (rs.next()) {
				incrementoExtra++;
				PainelStatusBar.setValueProgress(incrementoExtra*100/total);
				src = regra.getAtributo(rs.getString("tag"), "src");
				String img2insert = G_Link.getFullPath(rs.getString("nomePagina"), src);
				if(img2insert.equals(pathImage)){
					banco.insertTabImagem(rs.getString("nomePagina"), rs.getInt("idPagina"), rs.getString("tag"), img2insert, rs.getInt("linha"), rs.getInt("coluna"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
