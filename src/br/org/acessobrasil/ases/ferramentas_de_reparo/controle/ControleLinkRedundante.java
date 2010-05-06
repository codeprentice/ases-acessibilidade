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

package br.org.acessobrasil.ases.ferramentas_de_reparo.controle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.org.acessobrasil.ases.entidade.EstadoSilvinha;
import br.org.acessobrasil.ases.ferramentas_de_reparo.excessao.ExceptionImagemNotFound;
import br.org.acessobrasil.ases.nucleo.InterfClienteDoNucleo;
import br.org.acessobrasil.ases.nucleo.InterfNucleos;
import br.org.acessobrasil.ases.nucleo.NucleoEstruturado;
import br.org.acessobrasil.ases.nucleo.adapters.entidade.ArmazenaErroOuAviso;
import br.org.acessobrasil.ases.regras.RegrasHardCodedEmag;
import br.org.acessobrasil.silvinha.util.AlteradorDeCsv;
import br.org.acessobrasil.silvinha2.mli.GERAL;
/**
 * Filtra os erros e aplica as corre��es 
 * para o mapa de imagens
 * @author Fabio Issamu Oshiro
 */
public class ControleLinkRedundante implements InterfClienteDoNucleo{

	AlteradorDeCsv alteradorDeCsv;
	private boolean salva = false;
	
	ArrayList<ArmazenaErroOuAviso> arrErros;
	ArrayList<String> arrErroIni;
	private String codigo;
	InterfNucleos nucleo = new NucleoEstruturado();
	
	public void avalia(String codigo){
		arrErros = new ArrayList<ArmazenaErroOuAviso>();
		arrErroIni = new ArrayList<String>();
		
		
		this.codigo=codigo;
		nucleo = new NucleoEstruturado();
		nucleo.addCliente(this);
		nucleo.setCodHTML(codigo);
		nucleo.setWCAGEMAG(NucleoEstruturado.EMAG);
		nucleo.avalia();
	}
	public void avalia(String conteudo, boolean salva) {
		
		this.codigo=conteudo;
		if (conteudo == null)
			return;
		
		this.salva = salva;
		if(EstadoSilvinha.conteudoEmPainelResumo){
		alteradorDeCsv = new AlteradorDeCsv(EstadoSilvinha.hashCodeAtual);
		}
		nucleo = new NucleoEstruturado();
		nucleo.setCodHTML(conteudo);
		if (EstadoSilvinha.orgao == 2) {
			
			nucleo.setWCAGEMAG(InterfNucleos.EMAG);
		} else {
		
			nucleo.setWCAGEMAG(InterfNucleos.WCAG);
		}
		nucleo.addCliente(this);
		nucleo.avalia();
	

		}
	public String corrige(String text, int erroAtual) throws ExceptionImagemNotFound{
		int posToInsertLinks = getPosToInsertLinks(erroAtual);
		if (posToInsertLinks==-1){
			throw new ExceptionImagemNotFound(GERAL.SEM_IMAGEM_MAPA);
		}
		String newTag,tag = arrErros.get(erroAtual).getTagCompleta();
		RegrasHardCodedEmag regra = new RegrasHardCodedEmag();
		String href = regra.getAtributo(tag,"href");
		newTag="\n<a href=\"" + href + "\">" + text + "</a> | ";
		this.codigo = this.codigo.substring(0,posToInsertLinks)+newTag+this.codigo.substring(posToInsertLinks);
		return this.codigo;
	}

	public void avisosP1(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getAvisoP1(EstadoSilvinha.hashCodeAtual);

			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setAvisoP1(EstadoSilvinha.hashCodeAtual, valor);
		}
	
	}

	public void avisosP2(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getAvisoP2(EstadoSilvinha.hashCodeAtual);

			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setAvisoP2(EstadoSilvinha.hashCodeAtual, valor);
		}
	}

	public void avisosP3(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {	
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getAvisoP3(EstadoSilvinha.hashCodeAtual);

			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setAvisoP3(EstadoSilvinha.hashCodeAtual, valor);
		}
	}

	public void errosP1(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {
		
		if (salva && EstadoSilvinha.conteudoEmPainelResumo){
			String valor = alteradorDeCsv
					.getErroP1(EstadoSilvinha.hashCodeAtual);
			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setErroP1(EstadoSilvinha.hashCodeAtual, valor);
		}
		for(ArmazenaErroOuAviso erro:armazenaErroOuAviso){
			if(erro.getIdTextoRegra().equals("1.12")){
				arrErros.add(erro);
				arrErroIni.add(getErroIni(erro)+"");
			}
		}
	}

	public void errosP2(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getErroP2(EstadoSilvinha.hashCodeAtual);
			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setErroP2(EstadoSilvinha.hashCodeAtual, valor);
		}
	}

	public void errosP3(ArrayList<ArmazenaErroOuAviso> armazenaErroOuAviso) {		
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getErroP3(EstadoSilvinha.hashCodeAtual);
			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setErroP3(EstadoSilvinha.hashCodeAtual, valor);
		}
	}

	public void fimDaAvaliacao() {	
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			alteradorDeCsv.escreveEmDisco();
			}
			this.salva = false;
	}

	public void linksAchadosPeloNucleo(HashSet<String> links) {		
	}
	public int length() {
		return arrErros.size();
	}
	
	public int getColuna(int i) {
		return arrErros.get(i).getColuna();
	}
	
	public String getTag(int i) {
		return arrErros.get(i).getTagCompleta();
	}
	public int getLinha(int i) {
		return arrErros.get(i).getLinha();
	}
	
	public int getIniIndex(int i){
		return Integer.parseInt(arrErroIni.get(i).toString());
	}

	private int getErroIni(ArmazenaErroOuAviso erro){
		int linha = erro.getLinha();
		int contaLinha = 1, ini = 0;
		while (contaLinha < linha) {
			ini = this.codigo.indexOf('\n', ini + 1);
			if (ini != -1) {
				contaLinha++;
			} else {
				// linha n�o encontrada
				return -1;
			}
		}
		ini+=erro.getColuna();
		if (ini + 1 >= this.codigo.length())
			return -1;
		return ini;
	}

	private String getNomeMapImage(int erroAtual){
		String pai = this.codigo.substring(0, Integer.parseInt(arrErroIni.get(erroAtual).toString()));
		int iniPai = pai.toLowerCase().lastIndexOf("<map");
		if (iniPai==-1) return null;
		int fimPai = pai.indexOf(">",iniPai);
		if (fimPai==-1) return null;
		String tagPai = pai.substring(iniPai,fimPai+1);
		//System.out.println(tagPai);
		RegrasHardCodedEmag regras = new RegrasHardCodedEmag();
		return regras.getAtributo(tagPai, "name");
	}
	/**
	 * Escolhe o lugar para colocar os links redundantes
	 * @param erroAtual
	 * @return
	 */
	private int getPosToInsertLinks(int erroAtual){
		//pegar o nome do mapa de imagens
		String mapName = getNomeMapImage(erroAtual).toLowerCase();
		//System.out.println("mapName='" + mapName + "'");
		//achar a imagem que usa este mapa
		String regEx = "<img.*?\\susemap=(([\"'])#"+mapName+"\\2).*?>";
		//System.out.println("regEx = " +regEx);
		Pattern patVal = Pattern.compile(regEx);
		Matcher matVal = patVal.matcher(this.codigo.toLowerCase());
		if(matVal.find()){
			//System.out.println("tagImg = '"+ matVal.group()+"'");
			return matVal.end();
		}
		return -1;
	}
	
}

