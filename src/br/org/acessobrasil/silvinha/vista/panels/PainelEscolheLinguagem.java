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

package br.org.acessobrasil.silvinha.vista.panels;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.org.acessobrasil.silvinha.util.PropertyLoader;
import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
import br.org.acessobrasil.silvinha2.mli.GERAL;
/**
 * Painel para escolher o idioma 
 *
 */
public class PainelEscolheLinguagem extends JPanel {

	private static final long serialVersionUID = -5990912592376156813L;

	private JComboBox combo;
	private JOptionPane op;

	public PainelEscolheLinguagem(FrameSilvinha frameSilvinha) throws ClassNotFoundException, SQLException {
		
		
		HashMap<String, String> linguagens = verificaLinguagens();
		if (linguagens != null && linguagens.size() > 0) {
			combo = new JComboBox();
	        for (String linguagem : linguagens.keySet()) {
	            combo.addItem(linguagem);
	        }
			this.add(combo);
		} else {
			this.add(new JLabel(GERAL.NENHUM_IDIOMA));
		}
		op = new JOptionPane(this, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		JDialog dialog = op.createDialog(this, GERAL.SELECIONE_IDIOMA);
		dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dialog.setVisible(true);
		Object opcao = op.getValue();
		if (opcao != null) {
			if (opcao.equals(JOptionPane.OK_OPTION)) {
				String key = combo.getSelectedItem().toString();
				String path = linguagens.get(key);
				try {
					new TokenLang(path);
				} catch (Exception e) {
					//TODO tratamento de excess�o
				}
				TokenLang.setProperty(PropertyLoader.SILVINHA_LANGUAGE, path, frameSilvinha);
				frameSilvinha.inicializarGraficos();
				frameSilvinha.repaint();
				frameSilvinha.setVisible(true);
			} else if (opcao.equals(JOptionPane.CANCEL_OPTION)) {
			}
		}
	}
	
	private HashMap<String, String> verificaLinguagens() {
		HashMap<String, String> linguagens = new HashMap<String, String>();
		File diretorio = new File("config/lang");
		File[] files = diretorio.listFiles();
		for (File file : files) {
			Properties p = new Properties();
			try {
				p.load(new FileInputStream(file));
				linguagens.put(p.getProperty("IDIOMA"), file.getName());
			} catch (IOException ioe) {}
		}
		return linguagens;
	}
	
//	public static void main(String[] args) {
//		new PainelEscolheLinguagem(null);
//	}
	
}
