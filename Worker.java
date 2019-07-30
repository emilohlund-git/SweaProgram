/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dbprogrammet;

import javax.swing.SwingWorker;

/**
 *
 * @author Emilohlund
 */
public class Worker extends SwingWorker<Object, Object> {

    @Override
    protected Object doInBackground() throws Exception {
       for(int index = 0; index < 1000; index++) {
           int progress = Math.round(((float) index / 1000f) * 100f);
           setProgress(progress);
           Thread.sleep(25);
       }
       return null;
    }
    
}
