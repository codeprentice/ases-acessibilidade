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

package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.imagem;

import java.util.ArrayList;
import java.util.HashSet;

import br.org.acessobrasil.ases.entidade.EstadoSilvinha;
import br.org.acessobrasil.ases.nucleo.InterfClienteDoNucleo;
import br.org.acessobrasil.ases.nucleo.InterfNucleos;
import br.org.acessobrasil.ases.nucleo.MethodFactNucleos;
import br.org.acessobrasil.ases.nucleo.adapters.entidade.ArmazenaErroOuAviso;
import br.org.acessobrasil.silvinha.util.AlteradorDeCsv;
import br.org.acessobrasil.silvinha2.util.G_File;
/**
 * Controla o processo de corre��o do c�digo HTML 
 *
 */
class FerramentaDescricaoCtrl implements InterfClienteDoNucleo {

	/**
	 * 
	 */
	AlteradorDeCsv alteradorDeCsv;

	private boolean salva = false;

	private final PanelDescricaoImagens imagens;

	private InterfNucleos nucleo;

	ArrayList<ArmazenaErroOuAviso> errosP1;

	public FerramentaDescricaoCtrl(PanelDescricaoImagens imagens) {
		this.imagens = imagens;
	}

	public FerramentaDescricaoCtrl(PanelDescricaoImagens imagens, String string,int nomeArquivoouConteudo) {
		this.imagens = imagens;
		if(string==null) return;
		nucleo = MethodFactNucleos.mFNucleos("Estruturado");
		if(nomeArquivoouConteudo==0){
		G_File arq = new G_File(string);
		nucleo.setCodHTML(arq.read());
		}
		if(nomeArquivoouConteudo==1){
		nucleo.setCodHTML(string);
		}
		
		if (EstadoSilvinha.orgao == 2) {
			
			nucleo.setWCAGEMAG(InterfNucleos.EMAG);
		} else {
		
			nucleo.setWCAGEMAG(InterfNucleos.WCAG);
		}
		
		nucleo.addCliente(this);
		nucleo.avalia();
		this.imagens.codigo = nucleo.getCodHTMLOriginal();

	}
	public FerramentaDescricaoCtrl(PanelDescricaoImagens imagens, String conteudo) {
		this.imagens = imagens;
		if(conteudo==null) return;
		
		nucleo = MethodFactNucleos.mFNucleos("Estruturado");
		nucleo.setCodHTML(conteudo);
	if (EstadoSilvinha.orgao == 2) {
			
			nucleo.setWCAGEMAG(InterfNucleos.EMAG);
		} else {
		
			nucleo.setWCAGEMAG(InterfNucleos.WCAG);
		}
		
		nucleo.addCliente(this);
		nucleo.avalia();
		this.imagens.codigo = nucleo.getCodHTMLOriginal();

	}
	public FerramentaDescricaoCtrl(PanelDescricaoImagens imagens,String conteudo, boolean salva) {
		this.imagens = imagens;
		if (conteudo == null)
			return;
		
		this.salva = salva;
		if(EstadoSilvinha.conteudoEmPainelResumo){
		alteradorDeCsv = new AlteradorDeCsv(EstadoSilvinha.hashCodeAtual);
		}
		nucleo = MethodFactNucleos.mFNucleos("Estruturado");
		nucleo.setCodHTML(conteudo);
	if (EstadoSilvinha.orgao == 2) {
			
			nucleo.setWCAGEMAG(InterfNucleos.EMAG);
		} else {
		
			nucleo.setWCAGEMAG(InterfNucleos.WCAG);
		}
		
		nucleo.addCliente(this);
		nucleo.avalia();
		this.imagens.codigo = nucleo.getCodHTMLOriginal();

	}
	private void reload() {
		//G_File arq = new G_File("C:\\ buffer");
		G_File arq = new G_File();

		// errosP2.clear();
		this.imagens.posicaoeTag.clear();
		nucleo = MethodFactNucleos.mFNucleos("Estruturado");
		nucleo.setCodHTML(arq.read());
	if (EstadoSilvinha.orgao == 2) {
			
			nucleo.setWCAGEMAG(InterfNucleos.EMAG);
		} else {
		
			nucleo.setWCAGEMAG(InterfNucleos.WCAG);
		}
		
		nucleo.addCliente(this);
		nucleo.avalia();
		this.imagens.codigo = nucleo.getCodHTMLOriginal();

		this.imagens.tableLinCod.clearTab();
		this.imagens.tableLinCod.initComponents(this.imagens.posicaoeTag);

	}
	private String reconstroiComBarraENovoValor(String valor, int novoValor) {
		int pos = valor.indexOf("/");
		if (pos == -1) {
			valor += " / " + novoValor;
		} else {
			valor = valor.substring(0, pos);
			valor += "/ " + novoValor;
		}
		return valor;
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
		if (salva && EstadoSilvinha.conteudoEmPainelResumo) {
			String valor = alteradorDeCsv
					.getErroP1(EstadoSilvinha.hashCodeAtual);
			valor = alteradorDeCsv.reconstroiComBarraENovoValor(valor, armazenaErroOuAviso
					.size());

			alteradorDeCsv.setErroP1(EstadoSilvinha.hashCodeAtual, valor);
		}
		errosP1 = armazenaErroOuAviso;
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
		if (nucleo.getCodWcagEmag() == InterfNucleos.EMAG) {
			for (ArmazenaErroOuAviso aEA : errosP1) {

				if (aEA.getIdTextoRegra().equals("1.11")) {

					FerramentaDescricaoModel errado = new FerramentaDescricaoModel(aEA.getLinha(), aEA.getColuna());
					errado.setTexto(aEA.getTagCompleta());
					if(aEA.getTagCompleta().toLowerCase().indexOf("<img")!=-1){
					this.imagens.posicaoeTag.add(errado);
					}
				}
			}
		} else if (nucleo.getCodWcagEmag() == InterfNucleos.WCAG) {
			for (ArmazenaErroOuAviso aEA : errosP1) {
				if (aEA.getIdTextoRegra().equals("1.1")) {
					FerramentaDescricaoModel errado = new FerramentaDescricaoModel(aEA.getLinha(), aEA.getColuna());
					errado.setTexto(aEA.getTagCompleta());
					if(aEA.getTagCompleta().toLowerCase().indexOf("<img")!=-1){
						this.imagens.posicaoeTag.add(errado);
						}
					
				}
			}
		}
	}

	public void linksAchadosPeloNucleo(HashSet links) {

	}

	public ArrayList<FerramentaDescricaoModel> getPosicaoeTag() {
		return this.imagens.posicaoeTag;
	}

	public void setPosicaoeTag(ArrayList<FerramentaDescricaoModel> posiceTag) {
		this.imagens.posicaoeTag = posiceTag;
	}

}