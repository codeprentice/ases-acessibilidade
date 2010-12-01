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

package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.doctype;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.org.acessobrasil.silvinha.vista.componentes.MenuSilvinha;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.frames.mensagens.Creditos;
import br.org.acessobrasil.silvinha.vista.panels.SuperPainelCentral;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.util.TxtBuffer;
/**
 * UI separa o c�digo original do em edi��o
 */
public class PainelTabbedDoctype<PAINELFERR extends ferramentaDoctype> extends SuperPainelCentral implements ChangeListener, ActionListener {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	private JTabbedPane editNaoEdit;

	public static int ultimaFerramenta;

	PAINELFERR panelEditavel;

	PAINELFERR ferrNaoEditavel;

	FrameSilvinha frameSilvinha;

	private JMenuItem miBtnSalvar;

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command == "AumentaFonte") {
			panelEditavel.getArea_de_texto().aumentaFontSize();
			ferrNaoEditavel.getArea_de_texto().aumentaFontSize();
		} else if (command == "DiminuiFonte") {
			panelEditavel.getArea_de_texto().diminuiFontSize();
			ferrNaoEditavel.getArea_de_texto().diminuiFontSize();
		} else if (command == "Contraste") {
			panelEditavel.getArea_de_texto().autoContraste();
			ferrNaoEditavel.getArea_de_texto().autoContraste();
		} else if (command == "SelecionarTudo") {
			panelEditavel.getArea_de_texto().getTextPane().selectAll();
			ferrNaoEditavel.getArea_de_texto().getTextPane().selectAll();
		} else if (command == "Creditos") {
			new Creditos();
		} else if (command == "Sair") {
			System.exit(0);
		} else if (command == "Desfazer") {
			panelEditavel.getArea_de_texto().undo();
		} else {
			panelEditavel.actionPerformed(e);
		}
	}

	public void init(FrameSilvinha frameSilvinha) {
		this.frameSilvinha = frameSilvinha;
		this.frameSilvinha.setTitle(GERAL.TIT_EDT_DTD);
		editNaoEdit = new JTabbedPane();

		editNaoEdit.addChangeListener(this);
		this.setBackground(this.frameSilvinha.corDefault);
	}

	@SuppressWarnings("unchecked")
	public PainelTabbedDoctype(FrameSilvinha frameSilvinha) {
		super(new BorderLayout());
		ferrNaoEditavel = (PAINELFERR) new ferramentaDoctype(TxtBuffer.getContentOriginal(), frameSilvinha);
		panelEditavel = (PAINELFERR) new ferramentaDoctype(TxtBuffer.getContent(), frameSilvinha);

		init(frameSilvinha);

		adicionaPaineis(panelEditavel, ferrNaoEditavel);
	}

	@SuppressWarnings("unchecked")
	public PainelTabbedDoctype(String string, FrameSilvinha frameSilvinha) {
		super(new BorderLayout());	
		ferrNaoEditavel = (PAINELFERR) new ferramentaDoctype(TxtBuffer.getContentOriginal(), frameSilvinha);
		panelEditavel = (PAINELFERR) new ferramentaDoctype(string, frameSilvinha);
		init(frameSilvinha);

		adicionaPaineis(panelEditavel, ferrNaoEditavel);
	}

	private void adicionaPaineis(PAINELFERR ferrEditavel, PAINELFERR ferrNaoEditavel) {
		// frameSilvinha.setJMenuBar(this.criaMenuBar());
		frameSilvinha.setJMenuBar(MenuSilvinha.criaMenuBar(frameSilvinha, this, miBtnSalvar, panelEditavel.getArea_de_texto()));
		editNaoEdit.add(GERAL.CODIGO_EDICAO, ferrEditavel);
		editNaoEdit.add(GERAL.CODIGO_ORIGINAL, ferrNaoEditavel);
		ferrEditavel.setPanelOriginal(ferrNaoEditavel);
		this.add(editNaoEdit, BorderLayout.CENTER);
	}

	public void avaliaUrl(String url) {
		panelEditavel.avaliaUrl(url);
	}

	public void avaliaArq(String url) {
		panelEditavel.avaliaArq(url);
	}

	@Override
	public boolean showBarraUrl() {
		return true;
	}

	public void stateChanged(ChangeEvent arg0) {
		new Thread(new Runnable() {
			public void run() {
				System.gc();
			}
		}).start();
	}

}
