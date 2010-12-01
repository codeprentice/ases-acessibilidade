/*******************************************************************************
 * Copyright 2005, 2006, 2007, 2008 Acessibilidade Brasil
 * Este arquivo � parte do programa ASES - Avaliador e Simulador para AcessibilidadE de S�tios
 * O ASES � um software livre; voc� pode redistribui-lo e/ou modifica-lo dentro dos termos da Licen�a P�blica Geral GNU como
 * publicada pela Funda��o do Software Livre (FSF); na vers�o 2 da Licen�a, ou (na sua opni�o) qualquer vers�o posterior.
 * Este programa � distribuido na esperan�a que possa ser  util, mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUA��O a qualquer  MERCADO ou APLICA��O EM PARTICULAR. Veja a Licen�a P�blica Geral GNU para maiores detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU, sob o t�tulo "LICENCA.txt", junto com este programa, se n�o, escreva para a Funda��o do Software Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *******************************************************************************/
package br.org.acessobrasil.silvinha2.util.onChange;


import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import br.org.acessobrasil.silvinha2.util.G_TextAreaSourceCode;
/**
 * Controla quando o texto � alterado como o JavaScript 
 * @author Renato Tomaz Nati
 */
public class OnChange extends JFrame implements  FocusListener, KeyListener,OnChangeListener{
	
	private static final long serialVersionUID = 1L;
	G_TextAreaSourceCode boxCode;
	boolean keyTyped=false;
	public OnChangeListener itam;
	
	
	public OnChange(G_TextAreaSourceCode boxCode,OnChangeListener itam){
		this.boxCode=boxCode;
		this.boxCode.getTextPane().addFocusListener(this);
		this.boxCode.getTextPane().addKeyListener(this);
		this.itam=itam;
	}
	
	/**
	 * se alterou alguma tecla e perde o focus chama
	 * o m�todo altTextFocusLost do objeto que foi repassado
	 * para esse objeto
	 */
	public void focusLost(FocusEvent focusevent) {
		if(keyTyped){
			itam.altTextFocusLost();
			keyTyped=false;
		}
	}
	
	public void keyPressed(KeyEvent arg0) {
		keyTyped=true;
	}
	
	
	public void focusGained(FocusEvent focusevent) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
	public void altTextFocusLost() {}
}