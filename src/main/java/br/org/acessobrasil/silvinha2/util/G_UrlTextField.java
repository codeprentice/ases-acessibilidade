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

package br.org.acessobrasil.silvinha2.util;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JComboBox;
/**
 * Combo com urls recentes
 * @author Fabio Issamu Oshiro
 */
public class G_UrlTextField extends JComboBox{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OnComboBoxEdited listener;
	private HashSet<String> hashSetHistorico = new HashSet<String>();
	private ArrayList<String> arrHistorico = new ArrayList<String>();
	private G_File arqHist;
	private int maxUrl=10;
	private boolean salvaHistorico = true;
	public G_UrlTextField(String arq){
		this.setEditable(true);
		arqHist = new G_File(arq);
		String a[] = arqHist.read().split("\n");
		this.addItem("");
		for (int i = 0; i < a.length; i++) {
			hashSetHistorico.add(a[i]);
			this.addItem(a[i]);
			arrHistorico.add(a[i]);
		}
		
		this.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("comboBoxEdited")){
					salvaHistorico();
				}
			}
		});
	}
	private void salvaHistorico() {
		if(!salvaHistorico){
			return;
		}
		String novo=this.getSelectedItem().toString();
		if(hashSetHistorico.add(novo)){
			this.addItem(novo);
			arrHistorico.add(novo);
		}
		while(arrHistorico.size()>maxUrl){
			arrHistorico.remove(0);
		}
		StringBuffer sb = new StringBuffer();
		for (String url:arrHistorico) {
			if(!url.trim().equals("")){
				sb.append(url+"\n");
			}
		}
		arqHist.write(sb.toString());
		if(this.listener!=null){
			//System.out.println("onComboBoxEdited(novo)='" + novo +"'");
			this.listener.onComboBoxEdited(novo);
		}
	}
	public void setComboEditedListener(OnComboBoxEdited obj){
		this.listener = obj;
	}
	public interface OnComboBoxEdited{
		public void onComboBoxEdited(String valor);
	}
	public void setText(String valor) {
		salvaHistorico=false;
		this.setSelectedItem(valor);
		salvaHistorico=true;
	}
}
