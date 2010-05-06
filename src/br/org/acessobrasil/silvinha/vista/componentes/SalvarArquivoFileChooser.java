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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import br.org.acessobrasil.silvinha.entidade.ResumoDoRelatorio;
import br.org.acessobrasil.silvinha.negocio.Gerente;
import br.org.acessobrasil.silvinha.util.GravadorDeRelatorio;
import br.org.acessobrasil.silvinha2.mli.GERAL;
/**
 * Caixa de di�logo para salvar aquivos 
 *
 */
public class SalvarArquivoFileChooser extends JFileChooser{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6102115366294050832L;
	
	public SalvarArquivoFileChooser(JFrame frameSilvinha, ResumoDoRelatorio resumo) {
		super();
		
		this.setFileFilter(new SilvinhaFileFilter());
		int salvar = showSaveDialog(frameSilvinha);
		if (salvar == JFileChooser.APPROVE_OPTION) {
			String path = getSelectedFile().getPath();
			File file = new File(path);
			if (file.exists()) {
				int confirmar = JOptionPane.showConfirmDialog(this, GERAL.ARQUIVO_EXISTENTE + GERAL.SOBRESCREVER_ARQUIVO, 
						                      GERAL.BTN_SALVAR, JOptionPane.YES_NO_OPTION);
				if (confirmar == JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(this, GERAL.GRAV_CANCELADA_USUARIO);
					Gerente.pausa=false;//pausa enquanto n�o acaba de salvar
				} else {
					//if (!GravadorDeRelatorio.gravarRelatorios(file.getPath(), resumo.gerarArquivosRelatorios())) {
					if (!GravadorDeRelatorio.gravarRelatorios(file.getPath())) {
						JOptionPane.showMessageDialog(this, GERAL.FALHA_GRAV_ARQUIVO, GERAL.ERRO, 
								                      JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, GERAL.ARQUIVO_GRAV_SUCESSO);
						Gerente.pausa=false;//pausa enquanto n�o acaba de salvar
					}
				}
			} else {
				//if (!GravadorDeRelatorio.gravarRelatorios(file.getPath(), resumo.gerarArquivosRelatorios())) {
				if (!GravadorDeRelatorio.gravarRelatorios(file.getPath())) {
					JOptionPane.showMessageDialog(this, GERAL.GRAV_CANCELADA_USUARIO, GERAL.ERRO,
												  JOptionPane.ERROR_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, GERAL.ARQUIVO_GRAV_SUCESSO);
					Gerente.pausa=false;//pausa enquanto n�o acaba de salvar
				}
			}
			
		} else if (salvar == JFileChooser.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(this, GERAL.GRAV_CANCELADA_USUARIO);
			Gerente.pausa=false;//pausa enquanto n�o acaba de salvar
		}
		
	}

}
