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

package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.corretor_eventos;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import br.org.acessobrasil.silvinha.vista.componentes.MenuSilvinha;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.panels.SuperPainelCentral;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.util.TxtBuffer;
/**
 * UI para corrigir eventos, separa o c�digo original do em edi��o
 */
public class PainelTabbedCorretorEventos extends SuperPainelCentral implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenuItem miBtnSalvar;

	private JTabbedPane editNaoEdit;

	FrameSilvinha frameSilvinha;

	PanelCorretorEventos panelEditavel;

	public PainelTabbedCorretorEventos(FrameSilvinha frameSilvinha) {
		super(new BorderLayout());

		editNaoEdit = new JTabbedPane();
		panelEditavel = new PanelCorretorEventos(frameSilvinha, TxtBuffer.getContent());
		PanelCorretorEventos painelOriginal = new PanelCorretorEventos(frameSilvinha, TxtBuffer.getContentOriginal());
		panelEditavel.setPanelOriginal(painelOriginal);

		this.frameSilvinha = frameSilvinha;
		frameSilvinha.setTitle(GERAL.TIT_CORR_EVT);
		this.frameSilvinha.setJMenuBar(MenuSilvinha.criaMenuBar(frameSilvinha, this, miBtnSalvar, panelEditavel.textAreaSourceCode));

		editNaoEdit.add(GERAL.CODIGO_EDICAO, panelEditavel);
		editNaoEdit.add(GERAL.CODIGO_ORIGINAL, painelOriginal);

		panelEditavel.salvaAlteracoes = TxtBuffer.getInstanciaSalvaAlteracoes(panelEditavel.textAreaSourceCode.getTextPane(), panelEditavel.btn_salvar,
				miBtnSalvar, this.frameSilvinha);

		this.setBackground(this.frameSilvinha.corDefault);
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
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		PanelCorretorEventos panel = (PanelCorretorEventos)editNaoEdit.getSelectedComponent();
		if (cmd == "SelecionarTudo") {
			panel.textAreaSourceCode.getTextPane().requestFocus();
			panel.textAreaSourceCode.getTextPane().selectAll();
		} else if (cmd == "AumentaFonte") {
			panel.textAreaSourceCode.aumentaFontSize();
		} else if (cmd == "DiminuiFonte") {
			panel.textAreaSourceCode.diminuiFontSize();
		} else if (cmd == "Contraste") {
			panel.textAreaSourceCode.autoContraste();
		}else{
			panelEditavel.actionPerformed(e);
		}
	}
}
