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
package br.org.acessobrasil.silvinha2.bv.panel;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.org.acessobrasil.silvinha2.bv.componente.SlidebarLabel;
import br.org.acessobrasil.silvinha2.bv.filtros.Catarata;
import br.org.acessobrasil.silvinha2.mli.TradPaineisBV;

/**
 * UI para controle do desfocamento da imagem
 * 
 * @author Fabio Issamu Oshiro
 * 
 */
public class PanelCatarata extends JPanel implements ChangeListener {
	/**
	 * Serial default
	 */
	private static final long serialVersionUID = 1L;

	public static SlidebarLabel sliderBlur;

	public static SlidebarLabel sliderYellow;

	private Catarata catarata;
	public PanelCatarata(Catarata catarata,String nome) {
		//super(new GridLayout(2,1));
		super(new BorderLayout());
		LabelNome lblNome = new LabelNome(nome);
		JPanel painelSlider = new JPanel(new GridLayout(2,1));
		//System.out.print("PanelHipermetropia(Hipermetropia hipermetropia)()\n");

		sliderBlur = new SlidebarLabel(TradPaineisBV.FOCO,JSlider.HORIZONTAL, 0, 20, 2);
		sliderBlur.setName("Blur");
		sliderYellow = new SlidebarLabel(TradPaineisBV.AMARELAMENTO,JSlider.HORIZONTAL, 0, 10, 4);
		sliderYellow.setName("Amarelo");

		this.catarata = catarata;
		
		catarata.setBlurValue(sliderBlur.getValue());
		catarata.setYellowValue(sliderYellow.getValue());
		
		painelSlider.add(sliderBlur);
		painelSlider.add(sliderYellow);

		sliderBlur.addChangeListener(this);
		sliderBlur.setMajorTickSpacing(5);
		sliderBlur.setMinorTickSpacing(1);
		sliderBlur.setPaintTicks(true);
		sliderBlur.setPaintLabels(true);

		sliderYellow.addChangeListener(this);
		sliderYellow.setMajorTickSpacing(5);
		sliderYellow.setMinorTickSpacing(1);
		sliderYellow.setPaintTicks(true);
		sliderYellow.setPaintLabels(true);

		this.add(lblNome,BorderLayout.PAGE_START);
		this.add(painelSlider,BorderLayout.CENTER);
		this.setVisible(true);
	}
	public PanelCatarata(Catarata catarata) {
		super(new GridLayout(2,1));
		//System.out.print("PanelHipermetropia(Hipermetropia hipermetropia)()\n");

		sliderBlur = new SlidebarLabel(TradPaineisBV.FOCO,JSlider.HORIZONTAL, 0, 20, 2);
		sliderBlur.setName("Blur");
		sliderYellow = new SlidebarLabel(TradPaineisBV.AMARELAMENTO,JSlider.HORIZONTAL, 0, 10, 4);
		sliderYellow.setName("Amarelo");

		this.catarata = catarata;
		
		catarata.setBlurValue(sliderBlur.getValue());
		catarata.setYellowValue(sliderYellow.getValue());
		
		this.add(sliderBlur);
		this.add(sliderYellow);

		sliderBlur.addChangeListener(this);
		sliderBlur.setMajorTickSpacing(5);
		sliderBlur.setMinorTickSpacing(1);
		sliderBlur.setPaintTicks(true);
		sliderBlur.setPaintLabels(true);

		sliderYellow.addChangeListener(this);
		sliderYellow.setMajorTickSpacing(5);
		sliderYellow.setMinorTickSpacing(1);
		sliderYellow.setPaintTicks(true);
		sliderYellow.setPaintLabels(true);

		this.setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {
		//System.out.print("stateChanged()\n");
		JSlider source = (JSlider) e.getSource();
		//System.out.print("source.getName()=" + source.getName() + "\n");

		if (!source.getValueIsAdjusting()) {
			int valor = (int) source.getValue();
			//System.out.print("stateChanged=" + valor + "\n");
			if (source.getName().equals("Blur")) {
				catarata.setBlurValue(valor);				
			}else{
				catarata.setYellowValue(valor);
			}
		}
	}
}
