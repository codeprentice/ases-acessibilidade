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
package br.org.acessobrasil.ases.ferramentas_de_reparo.vista.preenchedor_formulario;

import java.awt.BorderLayout;

import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import br.org.acessobrasil.silvinha.vista.componentes.MenuSilvinha;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.panels.SuperPainelCentral;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.util.TxtBuffer;

public class PainelTabbedPreenchedorFormulario extends SuperPainelCentral {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenuItem miBtnSalvar;

	private JTabbedPane editNaoEdit;

	FrameSilvinha frameSilvinha;

	PanelPreenchedorFormulario panelPreenchedorFormulario;

	public PainelTabbedPreenchedorFormulario(FrameSilvinha frameSilvinha) {
		super(new BorderLayout());

		editNaoEdit = new JTabbedPane();
		
		PanelPreenchedorFormulario painelOriginal = new PanelPreenchedorFormulario(frameSilvinha, TxtBuffer.getContentOriginal());
		panelPreenchedorFormulario = new PanelPreenchedorFormulario(frameSilvinha, TxtBuffer.getContent());
		
		panelPreenchedorFormulario.setPanelOriginal(painelOriginal);

		this.frameSilvinha = frameSilvinha;
		frameSilvinha.setTitle(GERAL.TIT_PREE_FORM);
		this.frameSilvinha.setJMenuBar(MenuSilvinha.criaMenuBar(frameSilvinha, panelPreenchedorFormulario, miBtnSalvar, panelPreenchedorFormulario.textAreaSourceCode));

		editNaoEdit.add(GERAL.CODIGO_EDICAO, panelPreenchedorFormulario);
		editNaoEdit.add(GERAL.CODIGO_ORIGINAL, painelOriginal);

		panelPreenchedorFormulario.salvaAlteracoes = TxtBuffer.getInstanciaSalvaAlteracoes(panelPreenchedorFormulario.textAreaSourceCode.getTextPane(), panelPreenchedorFormulario.btn_salvar,
				miBtnSalvar, this.frameSilvinha);

		this.setBackground(this.frameSilvinha.corDefault);
		this.add(editNaoEdit, BorderLayout.CENTER);
	}

	public void avaliaUrl(String url) {
		panelPreenchedorFormulario.avaliaUrl(url);
	}
	public void avaliaArq(String url) {
		panelPreenchedorFormulario.avaliaArq(url);
	}
	@Override
	public boolean showBarraUrl() {
		return true;
	}
}
