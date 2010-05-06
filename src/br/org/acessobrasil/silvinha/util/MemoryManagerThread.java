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

package br.org.acessobrasil.silvinha.util;

import br.org.acessobrasil.silvinha.excessoes.ExceptionDialog;
import br.org.acessobrasil.silvinha.negocio.Gerente;
import br.org.acessobrasil.silvinha.util.lang.TokenLang;
import br.org.acessobrasil.silvinha.vista.panels.PainelAvaliacao;
import br.org.acessobrasil.silvinha2.util.G_Log;
/**
 * Verifica o uso de mem�ria  
 *
 */
public class MemoryManagerThread implements Runnable {
	
	private static G_Log log = new G_Log("silvinha-heap.log");
    private static final Runtime s_runtime = Runtime.getRuntime();
	private static boolean stop = false;
	
    private static void runGC () throws Exception
    {
        // It helps to call Runtime.gc()
        // using several method calls:
        for (int r = 0; r < 4; ++ r) _runGC ();
    }
//
    private static void _runGC () throws Exception
    {
    	
    	log.debug("Total Memory usage: " + (usedMemory()/1000000) + "Mb. Running garbage collection.");
        long usedMem1 = usedMemory(), usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++ i)
        {
            s_runtime.runFinalization();
            //s_runtime.gc();
            System.gc();
            Thread.sleep(25);
            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }
    
    private static int checkMemoryStatus()
    {
    	if (usedMemory() > Math.round((80 * s_runtime.totalMemory()) / 100) )
    	{
    		//log.debug("Heap usage: " + usedMemory() + " bytes.");
    		return 1;
    	}
    	else 
    	{
    		return 0;
    	}
    }

    private static long usedMemory()
    {
    	//s_runtime.
          	return s_runtime.totalMemory() - s_runtime.freeMemory();
          
    }
    
	public void run() {
		statConstMem();
		
		while (!stop)
		{
			if (checkMemoryStatus() == 1) 
			{
				try 
				{
					runGC();
				}
				catch (Exception e) 
				{
					log.debug(e.getMessage());
				}
				if (checkMemoryStatus() == 1) //Mem�ria Continua Baixa mesmo depois do Garbage Collection
				{
					stop = true;
					
					Gerente.setEstouroMemoria(true);
					PainelAvaliacao.pararExecucao(); //Parar a avalia��o
					ExceptionDialog.showExceptionDialog(TokenLang.ERRO_17);
				}
			}
			try 
			{
				Thread.sleep(500);
			}
			catch (Exception e) 
			{
				log.debug(e.getMessage());
			}
		}
	}
	private void statConstMem(){
		
		new Thread(new Runnable(){

			public void run() {
				while(true){
					try {
						Thread.sleep(2000);
						//System.out.println("Total Memory usage: " + (int)(usedMemory()/(Math.pow(2,10))) + "Kb, "+ (int)(usedMemory()/(Math.pow(2,20)))+"Mb.");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
    		
    	}).start();
	}
	public static void setStop(boolean stop)
	{
		MemoryManagerThread.stop = stop;
	}


}
