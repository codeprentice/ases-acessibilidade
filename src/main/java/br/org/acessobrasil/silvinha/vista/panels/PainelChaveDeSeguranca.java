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
//modificado em 04/12/2006 retirando o campo de cnpj
package br.org.acessobrasil.silvinha.vista.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.org.acessobrasil.silvinha.criptografia.HashGenerator;
import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.vista.configs.CoresDefault;
import br.org.acessobrasil.silvinha.vista.configs.UsoAutorizado;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha.vista.listeners.ActivateNextTextFieldListener;
import br.org.acessobrasil.silvinha.vista.listeners.SairListener;
import br.org.acessobrasil.silvinha2.mli.GERAL;
import br.org.acessobrasil.silvinha2.mli.TradPainelChaveDeSeguranca;

/**
 * UI para colocar o serial, na vers�o paga
 */ 
public class PainelChaveDeSeguranca extends SuperPainelCentral implements ActionListener {

	private static final long serialVersionUID = -334810518777864767L;

	// TODO modificando dia 5/12/2006
	// private static final String key = "12.345.678/0001-00";
	private static final String key = UsoAutorizado.cnpj;

	public static boolean chaveOk;

	private JLabel lblCnpj;

	private JLabel lblChave;

	private JTextField txtCnpj;

	private JTextField txtChave;

	private JButton btnOK;

	private JButton btnCancel;

	private Color corDefault = CoresDefault.getCorPaineis();

	public PainelChaveDeSeguranca(FrameSilvinha frame) {

		lblCnpj = new JLabel(GERAL.CNPJ);
		lblChave = new JLabel(GERAL.CHAVE);
		txtChave = new JTextField();
		btnOK = new JButton("OK");
		btnCancel = new JButton(GERAL.BTN_CANCELAR);
		Dimension tamanhoFields = new Dimension(190, 20);

		MaskFormatter format_textField;
		try {
			format_textField = new MaskFormatter("**.***.***/****-**");
			format_textField.setValidCharacters("0123456789");
			txtCnpj = new JFormattedTextField(format_textField);
		} catch (ParseException pe) {
		}

		// TODO modificado 5/12/2006
		// txtCnpj.setText("12345678000100");
		UsoAutorizado cnpj = new UsoAutorizado();
		txtCnpj.setText(cnpj.retiraFormatacaoCnpj());

		txtCnpj.setEditable(false);
		GridBagLayout bag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(bag);

		lblCnpj.setHorizontalAlignment(JLabel.RIGHT);
		lblCnpj.setLabelFor(txtCnpj);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(2, 10, 2, 10);
		this.add(lblCnpj, gbc);

		txtCnpj.setAlignmentX(SwingConstants.WEST);
		txtCnpj.addActionListener(new ActivateNextTextFieldListener(txtChave));
		txtCnpj.setPreferredSize(tamanhoFields);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 0, 2, 10);
		this.add(txtCnpj, gbc);

		lblChave.setHorizontalAlignment(JLabel.RIGHT);
		lblChave.setLabelFor(txtChave);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(2, 10, 2, 10);
		this.add(lblChave, gbc);

		txtChave.setAlignmentX(SwingConstants.WEST);
		txtChave.addActionListener(this);
		txtChave.setPreferredSize(tamanhoFields);
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(2, 0, 2, 10);
		this.add(txtChave, gbc);

		JPanel btns = new JPanel();
		btns.setLayout(new FlowLayout());
		btnOK.addActionListener(this);
		btnOK.setPreferredSize(new Dimension(100, 30));
		btnCancel.addActionListener(new SairListener(frame));
		btnCancel.setPreferredSize(new Dimension(100, 30));
		btns.setBackground(corDefault);
		btns.add(btnOK);
		btns.add(btnCancel);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.weightx = 1.0;
		gbc.insets = new Insets(2, 10, 2, 10);
		this.add(btns, gbc);

		setSize(300, 130);
		setLocation(300, 300);
		// this.setResizable(true);
		this.setBackground(corDefault);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (txtCnpj.getText().equals(key)) {
			if (HashGenerator.generate(txtCnpj.getText()).equals(txtChave.getText())) {
				// JOptionPane.showMessageDialog(this, "ok");
				chaveOk = true;
			} else {
				// JOptionPane.showMessageDialog(this, "false");
				ExceptionDialog.showExceptionDialog(TradPainelChaveDeSeguranca.CHAVE_SEGURANCA_INVALIDA);
				chaveOk = false;
			}
		} else {
			chaveOk = false;
			ExceptionDialog.showExceptionDialog(TradPainelChaveDeSeguranca.CNPJ_INVALIDO);
		}
	}

	@Override
	public void avaliaUrl(String url) {

	}

	@Override
	public boolean showBarraUrl() {
		return false;
	}

	// public static void main(String[] args) {
	// JFrame frame = new JFrame();
	// frame.add(new PainelChaveDeSeguranca());
	// // frame.setSize(new Dimension(700, 700));
	// frame.pack();
	// frame.setVisible(true);
	// }
}
