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

package br.org.acessobrasil.silvinha2.projetodosite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import br.org.acessobrasil.ases.nucleo.InterfNucleos;
import br.org.acessobrasil.ases.nucleo.MethodFactNucleos;
/**
 * Possui um relat�rio consolidado quando o usu�rio escolhe por avaliar "Site"
 * @author Fabio Issamu Oshiro
 *
 */
public class ProjetoDoSite {
	/**
	 * Guarda o Path do projeto
	 * Ex: C:/meuprojeto
	 */
	private static String localDoProjetoNoHD;
	private static String nomeDoProjeto;
	private HashSet<String> arr_imagemSemAlt =  new HashSet<String>();
	private HashMap<String,Integer> erroWcagP1 =  new HashMap<String,Integer>();
	private HashMap<String,Integer> erroWcagP2 =  new HashMap<String,Integer>();
	private HashMap<String,Integer> erroWcagP3 =  new HashMap<String,Integer>();
	private HashMap<String,Integer> erroEmagP1 =  new HashMap<String,Integer>();
	private HashMap<String,Integer> erroEmagP2 =  new HashMap<String,Integer>();
	private HashMap<String,Integer> erroEmagP3 =  new HashMap<String,Integer>();
	
	private HashSet<String> totalLinks = new HashSet<String>();
	
	private ArrayList<String> arrUrlParaAvaliar = new ArrayList<String>();
	private HashSet<String> arrUrlAvaliadas = new HashSet<String>();
	
	/**
	 * Guarda em qual indice est� da avalia��o.
	 * Relativo ao arrUrlParaAvaliar
	 */
	private int indiceAvaliado=0;
	
	public static void main(final String[] args) {
		Pagina pg = new Pagina();
		InterfNucleos nucleo = MethodFactNucleos.mFNucleos("Estruturado");
		pg.setNucleo(nucleo);
		pg.avaliaPagina();
	}
	
	/**
	 * Adiciona uma imagem �nica sem alt
	 * @param imagemSemAlt Caminho inteiro da imagem
	 */
	public void addImagemSemAlt(String imagemSemAlt){
		if (!arr_imagemSemAlt.contains(imagemSemAlt)){
			arr_imagemSemAlt.add(imagemSemAlt);
		}
	}

	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroWcagP1(String url_ou_path,int numero_total_de_erro){
		//tratamento de erro dos valores
		addErro(erroWcagP1,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroWcagP2(String url_ou_path,int numero_total_de_erro){
		//tratamento de erro dos valores
		addErro(erroWcagP2,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroWcagP3(String url_ou_path,int numero_total_de_erro){
		//tratamento de erro dos valores
		addErro(erroWcagP3,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroEmagP1(int numero_total_de_erro,String url_ou_path){
		//tratamento de erro dos valores
		addErro(erroEmagP1,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroEmagP2(int numero_total_de_erro,String url_ou_path){
		//tratamento de erro dos valores
		addErro(erroEmagP2,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Adiciona o total de erro de uma p�gina
	 * 
	 * @param numero_total_de_erro Passe o n�mero total de erro esta p�gina para este ponto de verifica��o
	 * @param url_ou_path Informe a url ou path do arquivo
	 */
	public void addErroEmagP3(int numero_total_de_erro,String url_ou_path){
		//tratamento de erro dos valores
		addErro(erroEmagP3,url_ou_path,numero_total_de_erro);
	}
	
	/**
	 * Retorna o n�mero total de erros do Emag para prioridade 1
	 * @return int
	 */
	public int getTotalErrosEmagP1(){
		return contaErros(erroWcagP1);
	}
	
	/**
	 * Retorna o n�mero total de erros do Emag para prioridade 2
	 * @return int
	 */
	public int getTotalErrosEmagP2(){
		return contaErros(erroWcagP2);
	}
	
	/**
	 * Retorna o n�mero total de erros do Emag para prioridade 3
	 * @return int
	 */
	public int getTotalErrosEmagP3(){
		return contaErros(erroWcagP3);
	}
	
	/**
	 * Retorna o n�mero total de erros do Wcag para prioridade 1
	 * @return int
	 */
	public int getTotalErrosWcagP1(){
		return contaErros(erroWcagP1);
	}
	
	/**
	 * Retorna o n�mero total de erros do Wcag para prioridade 2
	 * @return int
	 */
	public int getTotalErrosWcagP2(){
		return contaErros(erroWcagP2);
	}
	
	/**
	 * Retorna o n�mero total de erros do Wcag para prioridade 3
	 * @return int
	 */
	public int getTotalErrosWcagP3(){
		return contaErros(erroWcagP3);
	}
	
	/**
	 * Conta os erros do projeto, ou seja, soma os erros de todas as p�ginas
	 * @param mapa
	 * @return
	 */
	private int contaErros(HashMap<String,Integer> mapa){
		int total=0;
		Collection a = mapa.values();
		Iterator it = a.iterator();
		while (it.hasNext()) {
			total+=Integer.parseInt(it.next().toString());;
		}
		return total;
	}
	
	/**
	 * Adiciona um erro
	 */
	private void addErro(HashMap<String,Integer> mapa,String url_ou_path,int total){
		if(total<0){
			//Erro
		}else{
			mapa.put(url_ou_path, total);
		}
	}

	public static String getLocalDoProjetoNoHD() {
		return localDoProjetoNoHD;
	}

	public static void setLocalDoProjetoNoHD(String localDoProjetoNoHD) {
		ProjetoDoSite.localDoProjetoNoHD = localDoProjetoNoHD;
	}

	public static String getNomeDoProjeto() {
		return nomeDoProjeto;
	}

	public static void setNomeDoProjeto(String nomeDoProjeto) {
		ProjetoDoSite.nomeDoProjeto = nomeDoProjeto;
	}
	
}
