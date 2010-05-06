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

package br.org.acessobrasil.silvinha.vista.componentes;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.ZipException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import br.org.acessobrasil.ases.entidade.EstadoSilvinha;
import br.org.acessobrasil.ases.persistencia.SingBancoSite;
import br.org.acessobrasil.silvinha.entidade.ResumoDoRelatorio;
import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.negocio.Gerente;
import br.org.acessobrasil.silvinha.util.Extensions;
import br.org.acessobrasil.silvinha.util.GravadorDeRelatorio;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.panels.PainelResumo;
import br.org.acessobrasil.silvinha.vista.panels.PainelStatusBar;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.projetodosite.ConfiguracaoDaAvaliacao;
import br.org.acessobrasil.silvinha2.projetodosite.ProjetoDoSite;
import br.org.acessobrasil.silvinha2.util.Zipper;

/**
 * Caixa de di�logo para abrir arquivo 
 *
 */
public class AbrirArquivoFileChooser extends JFileChooser{
	private static final long serialVersionUID = -6102115366294050832L;
	private static String lastPath = "";
	
	public AbrirArquivoFileChooser(final FrameSilvinha frameSilvinha) {
		super(lastPath);
		this.setFileFilter(new SilvinhaFileFilter());
		int salvar = showOpenDialog(frameSilvinha);
		if (salvar == JOptionPane.YES_OPTION) {
			String path = getSelectedFile().getPath();
			lastPath = path;
			final File file = new File(path);
			if (!file.exists()) {
				JOptionPane.showMessageDialog(this, GERAL.ARQUIVO_INEXISTENTE, 
						                      GERAL.BTN_ABRIR, JOptionPane.ERROR_MESSAGE);
			} else {
				if (!file.getName().endsWith(Extensions.silvinha)) {
					JOptionPane.showMessageDialog(this, GERAL.ARQUIVO_INVALIDO, 
		                      GERAL.BTN_ABRIR, JOptionPane.ERROR_MESSAGE);
				} else {
					
					Thread t = new Thread(){
						public void run(){
							PainelResumo.setDesabilitarBtnContinuar(false);
							PainelStatusBar.showProgTarReq();
							final Zipper zipper = new Zipper();
							zipper.setTrabalhando(true);
							Thread t = new Thread(){
								public void run(){
									try {
										zipper.extrairZip(file,new File("temp"));
									} catch (ZipException e) {
										ExceptionDialog.showExceptionDialog("[LeitorDeRelatorio]: " + e.getMessage());
										e.printStackTrace();
										return;
									} catch (IOException e) {
										ExceptionDialog.showExceptionDialog("[LeitorDeRelatorio]: " + e.getMessage());
										e.printStackTrace();
								    	return ;
									}
								}
							};
							t.start();
							try{
								while(zipper.getTrabalhando()){
									PainelStatusBar.setValueProgress(zipper.getProgresso());
									Thread.sleep(300);
								}
							}catch(Exception e){
								
							}
							ConfiguracaoDaAvaliacao cf = new ConfiguracaoDaAvaliacao();				    
						    cf.loadConf();
						    EstadoSilvinha.orgao = cf.getIntRegraWcagEmag();
						    Properties opcoes = cf.getAsProperties();
						    Gerente.setProperties(opcoes);
						    String endAserAvaliado = cf.getUrl();
							String endSemHttp = endAserAvaliado.replaceAll("http://", "");
							String nomeProjeto = null;
							if (endSemHttp.indexOf("/") != -1) {
								nomeProjeto = endSemHttp.substring(0, endSemHttp.indexOf("/"));
							} else {
								nomeProjeto = endSemHttp;
							}
							ProjetoDoSite.setNomeDoProjeto(nomeProjeto);
							//Reconectar o banco
						    SingBancoSite.getBancoNome(cf.getUrl());
							PainelResumo.valorVelocidade=100;
							
							frameSilvinha.showPainelResumo(new ResumoDoRelatorio(opcoes), false);
							GravadorDeRelatorio.recuperarPaginasNaoAnalisadas();
							PainelStatusBar.hideProgTarReq();
						}
					};
					t.start();
					
				}
			}
		} else if (salvar == JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(this, GERAL.OP_CANCELADA_USUARIO);
		}
	}
	/*
	public AbrirArquivoFileChooser(FrameSilvinha frameSilvinha) {
		super(lastPath);
		this.setFileFilter(new SilvinhaFileFilter());
		int salvar = showOpenDialog(frameSilvinha);
		if (salvar == JOptionPane.YES_OPTION) {
			String path = getSelectedFile().getPath();
			lastPath = path;
			File file = new File(path);
			if (!file.exists()) {
				JOptionPane.showMessageDialog(this, TokenLang.ERRO_7, 
						                      TokenLang.ABRIR_MNU, JOptionPane.ERROR_MESSAGE);
			} else {
				if (!file.getName().endsWith(Extensions.silvinha)) {
					JOptionPane.showMessageDialog(this, TokenLang.ERRO_8, 
		                      TokenLang.ABRIR_MNU, JOptionPane.ERROR_MESSAGE);
				} else {
					//recupera os arquivos
					HashMap<String, Document> docs = LeitorDeRelatorio.lerRelatorios(path);
					if (docs != null) {
						ResumoDoRelatorio resumo = ResumoDoRelatorio.lerArquivoResumoEmXml(docs.remove("resumo.xml"));
						if (resumo.isGravaCompleto()) {
							ArrayList<RelatorioDaUrl> relatorios = new ArrayList<RelatorioDaUrl>();
							for (String key : docs.keySet()) {
								RelatorioDaUrl relatorio = RelatorioDaUrl.lerArquivoRelatorioEmXml(docs.get(key));
								relatorios.add(relatorio);
							}
							resumo.setRelatorios(relatorios);
							frameSilvinha.showPainelResumo(resumo, false);
						} else {
							frameSilvinha.showPainelResumo(resumo, true);
						}
					} else {
						JOptionPane.showMessageDialog(frameSilvinha, TokenLang.ERRO_9, TokenLang.LBL_ERRO, 
														JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		} else if (salvar == JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(this, TokenLang.ERRO_10);
		}
	}
	*/

}
