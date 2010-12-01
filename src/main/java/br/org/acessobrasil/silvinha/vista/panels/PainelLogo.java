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

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.configs.CoresDefault;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;
/**
 * Cabe�alho do sistema
 *
 */
public class PainelLogo extends JPanel {
	private static final long serialVersionUID = -9198602705163208132L;

	public PainelLogo() {
		if (FrameSilvinha.VISTA_ANTIGO_SILVINHA){
			JLabel logo = new JLabel();
			ImageIcon ico;
	        this.setBackground(CoresDefault.getCorPaineis());
	        logo.setBackground(CoresDefault.getCorPaineis());
	        logo.setForeground(CoresDefault.getCorPaineis());
        	ico = new ImageIcon("imagens/silvinha_imagem_top-bkp.png");
	        logo.setIcon(ico);       
	        this.add(logo);
	        this.setVisible(true);
		}else{
			this.setLayout(new BorderLayout());
			JLabel logo = new JLabel();
			ImageIcon ico;
	        this.setBackground(CoresDefault.getCorPaineis());
	        logo.setBackground(CoresDefault.getCorPaineis());
	        logo.setForeground(CoresDefault.getCorPaineis());
        	ico = new ImageIcon("imagens/"+TokenLang.LANG+"/topEsq.png");
	        logo.setIcon(ico);
	        logo.setSize(317,69);
	        this.add(logo,BorderLayout.WEST);
	        
	        JLabel fundo = new JLabel();
	        ico = new ImageIcon("imagens/topBg.png");
	        fundo.setIcon(ico);
	        this.add(fundo,BorderLayout.CENTER);
	        
	        JLabel gov = new JLabel();
	        ico = new ImageIcon("imagens/gov.png");
	        gov.setIcon(ico);
	        gov.setSize(451,69);
	        this.add(gov,BorderLayout.EAST);
	        
	        this.setVisible(true);
		}
	}

}
