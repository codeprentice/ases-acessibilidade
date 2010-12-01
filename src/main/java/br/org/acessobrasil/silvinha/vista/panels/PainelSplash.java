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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.frames.classePrincipal.FrameSilvinha;

/**
 * Classe da tela de abertuta com o logo do programa.
 * @author Danniel
 * Criado em 26/10/2005 09:04:49
 */
public class PainelSplash extends Thread {
	
	JWindow jw;
    /**
     * M�todo que exibe a tela de abertura.
     * @see java.lang.Runnable#run()
     */
	private boolean ficaAberto=true;
	
	/**
	 * Tempo limite para start do sistema
	 */
	private int timeLimit = 40;
	
	FrameSilvinha silvinha;
	public PainelSplash(FrameSilvinha silvinha) {
		this.silvinha=silvinha;
	}
	public void run() {
		
		jw = new JWindow();
		 /*
		  * Container imagem do splash.
		  */
		JLabel LogAcertaSilva = new JLabel();
		ImageIcon icon;
		if(FrameSilvinha.VISTA_ANTIGO_SILVINHA){
			icon = new ImageIcon("imagens/silvinha_splash-bkp.png");
		}else{
			icon = new ImageIcon("imagens/"+ TokenLang.LANG +"/silvinha_splash.png");
		}
		LogAcertaSilva.setIcon(icon);
		jw.getContentPane().add(LogAcertaSilva);
		/*
		 * Apresenta com o tamalho que ele tiver.
		 */
		jw.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		/*
		 * Posi��o no meio do desktop.
		 */
		jw.setBounds((screenSize.width - jw.getWidth()) / 2,
				     (screenSize.height - jw.getHeight()) / 2, 
				      jw.getWidth(), jw.getHeight());
        jw.setAlwaysOnTop(true);
        jw.setAlwaysOnTop(false);
		jw.setVisible(true);
		
		while (ficaAberto){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				/*
				 * Sair do sistema
				 */
				System.exit(0);
			}
			timeLimit--;
			if(timeLimit<0){
				/*
				 * Sair do sistema
				 */
				System.exit(0);
			}			
		}
		jw.setVisible(false);
		jw.dispose();
		
		/*
		 * Como colocar o foco novamente no silvinha?
		 */
		silvinha.setAlwaysOnTop(true);
		silvinha.setAlwaysOnTop(false);
		//silvinha.requestFocus();
	}
	/**
	 * Apaga.
	 *
	 */
	public void apaga(){
		ficaAberto=false;
	}
}
