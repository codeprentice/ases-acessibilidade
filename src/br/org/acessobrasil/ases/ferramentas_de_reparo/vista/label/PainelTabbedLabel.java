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
package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.label;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.org.acessobrasil.silvinha.vista.componentes.MenuSilvinha;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.panels.SuperPainelCentral;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.util.TxtBuffer;
/**
 * Separa o c�digo em edi��o do original(n�o edit�vel)
 * @author Fabio Issamu Oshiro, Haroldo Veiga e Renato Tomaz Nati
 *
 */
public class PainelTabbedLabel extends SuperPainelCentral implements ChangeListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane editNaoEdit;

	public static int ultimaFerramenta;

	PainelLabel ferrLabelPanelEditavel;

	FrameSilvinha frameSilvinha;

	PainelLabel ferrLabelPanelNaoEditavel;

	private JMenuItem miBtnSalvar;

	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if (command == "AumentaFonte") {
			ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel().aumentaFontSize();
			ferrLabelPanelNaoEditavel.getScrollPaneCorrecaoLabel().aumentaFontSize();
		} else if (command == "DiminuiFonte") {
			ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel().diminuiFontSize();
			ferrLabelPanelNaoEditavel.getScrollPaneCorrecaoLabel().diminuiFontSize();
		} else if (command == "Contraste") {
			ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel().autoContraste();
			ferrLabelPanelNaoEditavel.getScrollPaneCorrecaoLabel().autoContraste();
		} else if (command == "Desfazer") {
			ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel().undo();
		}else{
			ferrLabelPanelEditavel.actionPerformed(arg0);
		}
	}

	public PainelTabbedLabel(FrameSilvinha frameSilvinha) {
		super(new BorderLayout());
		this.frameSilvinha = frameSilvinha;
		this.frameSilvinha.setTitle(GERAL.TIT_ASS_ROT);
		editNaoEdit = new JTabbedPane();
		this.setBackground(frameSilvinha.corDefault);
		ferrLabelPanelNaoEditavel = new PainelLabel(TxtBuffer.getContentOriginal(), PainelLabel.CONTEUDO, frameSilvinha);
		ferrLabelPanelEditavel = new PainelLabel(TxtBuffer.getContent(), PainelLabel.CONTEUDO, frameSilvinha);
		
		ferrLabelPanelEditavel.setPanelOriginal(ferrLabelPanelNaoEditavel);
		editNaoEdit.addChangeListener(this);
		editNaoEdit.add(GERAL.CODIGO_EDICAO, ferrLabelPanelEditavel);
		editNaoEdit.add(GERAL.CODIGO_ORIGINAL, ferrLabelPanelNaoEditavel);
		frameSilvinha.setJMenuBar(MenuSilvinha.criaMenuBar(frameSilvinha, this, miBtnSalvar, ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel()));
		this.add(editNaoEdit, BorderLayout.CENTER);

	}

	public PainelTabbedLabel(String conteudoHTML, int conteudoOuArquivo, FrameSilvinha parentFrame) {
		super(new BorderLayout());
		this.frameSilvinha = parentFrame;
		frameSilvinha.setTitle(GERAL.TIT_ASS_ROT);
		// TxtBuffer.setHashCode(EstadoSilvinha.hashCodeAtual);
		editNaoEdit = new JTabbedPane();
		this.setBackground(this.frameSilvinha.corDefault);
		ferrLabelPanelNaoEditavel = new PainelLabel(TxtBuffer.getContentOriginal(), PainelLabel.CONTEUDO, parentFrame);
		ferrLabelPanelEditavel = new PainelLabel(conteudoHTML, PainelLabel.CONTEUDO, parentFrame);		
		ferrLabelPanelEditavel.setPanelOriginal(ferrLabelPanelNaoEditavel);
		editNaoEdit.addChangeListener(this);
		editNaoEdit.add(GERAL.CODIGO_EDICAO, ferrLabelPanelEditavel);
		editNaoEdit.add(GERAL.CODIGO_ORIGINAL, ferrLabelPanelNaoEditavel);
		frameSilvinha.setJMenuBar(MenuSilvinha.criaMenuBar(frameSilvinha, this, miBtnSalvar, ferrLabelPanelEditavel.getScrollPaneCorrecaoLabel()));
		this.add(editNaoEdit, BorderLayout.CENTER);
	}

	@Override
	public boolean showBarraUrl() {
		return true;
	}

	public void avaliaUrl(String url) {
		ferrLabelPanelEditavel.avaliaUrl(url);
	}

	public void avaliaArq(String url) {
		ferrLabelPanelEditavel.avaliaArq(url);
	}

	public void stateChanged(ChangeEvent arg0) {
		new Thread(new Runnable() {
			public void run() {
				System.gc();
			}
		}).start();
	}

}
