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
package br.org.acessobrasil.silvinha2.bv.filtros;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Glaucoma  implements FiltroDeImagem{

	private Blur blur = new Blur();
	
	private int blurVal = 0;
	
	private int size = 100;
	
	public BufferedImage aplicaFiltro(BufferedImage bi) {
		//BufferedImage image = ImageIO.read(new File("c:/images.jpg"));
		BufferedImage image2;
		try {
			blur.setValor(blurVal);
			bi=blur.aplicaFiltro(bi);
			image2 = ImageIO.read(new File("imagens/glaucoma.png"));
			
			int w = (image2.getWidth()/100) * size;
			int h = (image2.getHeight()/100) * size;
			
			//calcular o centro
			int x = (bi.getWidth()/2) - (w/2);
			int y = (bi.getHeight()/2) - (h/2);
			Graphics2D graphics = bi.createGraphics();
			graphics.drawImage(image2, x, y, w, h, null);
			graphics.dispose();
			return bi;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

	public void setBlurValue(int blurVal) {
		this.blurVal = blurVal;
	}

	public void setSize(int value) {
		value=100-value;
		size = value*2+100;
	}

}
