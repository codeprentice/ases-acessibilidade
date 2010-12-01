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

package br.org.acessobrasil.silvinha2.util;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;

import br.org.acessobrasil.silvinha.entidade.RelatorioDaUrl;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha2.projetodosite.ProjetoDoSite;

/**
 * Guarda um texto qualquer num buffer para uso comum das classes do pacote
 * atual
 * 
 * @author Renato Tomaz Nati
 */
public class TxtBuffer {
	private static String path = null;

	private static SalvaAlteracoes salvaAlteracoes;

	private static G_File buffer;
	private static G_File bufferOriginal;

	private static String hashCodeAtual;

	/**
	 * SingleTown
	 * @param jtextpane
	 * @param salvar
	 * @param btnSalvar
	 * @param parentFrame
	 * @return SalvaAlteracoes
	 */
	public static SalvaAlteracoes getInstanciaSalvaAlteracoes(JTextPane jtextpane, JButton salvar, JMenuItem btnSalvar, FrameSilvinha parentFrame) {
		if (salvaAlteracoes == null) {
			salvaAlteracoes = new SalvaAlteracoes(jtextpane, salvar, btnSalvar, parentFrame);
		} else {
			salvaAlteracoes.setJtextpane(jtextpane);
			salvaAlteracoes.setSalvar(salvar);
			salvaAlteracoes.setBtnSalvar(btnSalvar);
			salvaAlteracoes.setParentFrame(parentFrame);
		}
		return salvaAlteracoes;
	}

	/**
	 * retorna o conteudo do buffer desde que este ja exista
	 */
	public static String getContent() {
		trabComTemp();
		try {
			return TxtBuffer.buffer.read();
		} catch (RuntimeException e) {
			System.out.println("hashCodeAtual="+hashCodeAtual);
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * retorna o conteudo original ou o alterado
	 * @param original true pega o original, false pega o alterado em edi��o
	 * @return conteudo
	 */
	public static String getContent(boolean original) {
		if(!original){
			trabComTemp();
			try {
				return TxtBuffer.buffer.read();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}else{
			return getConteudo(RelatorioDaUrl.pathHD + hashCodeAtual).toString();
		}
		return null;
	}
	
	private static StringBuilder getConteudo(String endArq) {

		G_File file = new G_File(endArq);
		StringBuilder sbd = new StringBuilder(file.read());

		return sbd;

	}

	/**
	 * Faz o buffer pegar o arquivo temporario
	 *
	 */
	private static void trabComTemp() {
		try{
			StringBuilder strBbuffer = new StringBuilder();
			if(ProjetoDoSite.getNomeDoProjeto()==null){
				ProjetoDoSite.setNomeDoProjeto("sem_nome");
			}
			String myPath=RelatorioDaUrl.pathHD + ProjetoDoSite.getNomeDoProjeto().replace(":","-") + "/reparo/temp/" + hashCodeAtual;
			if (!(new File(myPath).exists())) {
				//n�o existe na pasta de reparo
				if (ProjetoDoSite.getNomeDoProjeto() == null) {
					/*
					 * quando n�o existe projeto
					 * Ex.: quando abre uma URL do menu arquivo>abrir URL
					 */
					
					buffer = new G_File(RelatorioDaUrl.pathHD + "0t");
					if (!(new File(RelatorioDaUrl.pathHD + "0t")).exists()) {
						//n�o existe cria e coloca "" no buffer
						buffer.write("");
					}
				} else {
					//cria o arquivo
					strBbuffer = getConteudo(RelatorioDaUrl.pathHD + hashCodeAtual);
					buffer = new G_File(myPath);
					buffer.write(strBbuffer.toString());
					System.err.println("TxtBuffer.trabComTemp() arquivo n�o temporario em uso, arquivo (" + myPath + ") criado");
				}
			} else {
				//existe na pasta de arquivos reparados
				buffer = new G_File(myPath);
				System.err.println("TxtBuffer.trabComTemp() Existe na pasta reparados, arquivo temporario em uso");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * pega o caminho do buffer
	 */
	public static String getPath() {
		return path;
	}

	/**
	 * seta o path e cria o arquivo ou utiliza o existente
	 */
	public static void setPath(String path) {

		TxtBuffer.path = path;
		TxtBuffer.buffer = new G_File(path);
		TxtBuffer.buffer.write("");
	}

	public static void setHashCode(String hashCode) {
		System.out.println("TxtBuffer.setHashCode()"+hashCode);
		hashCodeAtual = hashCode;
		trabComTemp();
		System.out.println("TxtBuffer.setHashCode="+hashCode);
	}
	public static String getHashCode() {
		return hashCodeAtual;
	}

	/**
	 * apaga o conteudo do buffer
	 */

	public static void clear() {
		hashCodeAtual="";
		salvaAlteracoes = null;
	}

	/**
	 * deleta o arquivo buffer
	 */



	public static boolean isEmpty() {
		try {
			if (TxtBuffer.getContent().equals("")) {
				return true;
			} else {
				return false;
			}

		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static String getContentOriginal() {
		G_File t=new G_File(RelatorioDaUrl.pathHD + hashCodeAtual);
		return t.read();
	}
	/**
	 * Atribui o conteudo original nos casos de avalia��o de 
	 * URL feitas pelo menu superior e abertura de arquivos
	 * @param conteudo
	 */
	public static void setContentOriginal(String conteudo,String hashCode){
		G_File original = new G_File(RelatorioDaUrl.pathHD + hashCode);
		hashCodeAtual=hashCode;
		original.write(conteudo);
		setContent(conteudo);
	}

	/**
	 * Atribui o conteudo do buffer desde que este j� exista
	 */
	public static void setContent(String content) {
		trabComTemp();
		try {
			if (salvaAlteracoes != null) {
				salvaAlteracoes.setAlterado();
			}
			TxtBuffer.buffer.write(content);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
}
