/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package testcontador;

import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author F
 */
public class Ventana extends javax.swing.JFrame implements Runnable {

    /**
     * Creates new form Ventana
     */
    int tope_min_minutes = 0;
    int tope_min_segundos = 0;
    int tope_min = 0;
    int tope_max = 0;
    int tope_max_segundos = 0;
    int tope_max_minutes = 0;
    int velocidad = 4;
    final int valorDieccion = 1;
    int direccion = valorDieccion;
    Thread hilo;
    boolean cronometroActivo;

    public Ventana() {
        initComponents();
    }

    public void setTopes() {
        int contador_max = 0;
        int contador_min = 0;
        try {
            int topeMax = Integer.parseInt(tfMax.getText());
            int topeMin = Integer.parseInt(tfMin.getText());
            if (topeMax > topeMin) {
                while (topeMax - 60 >= 0) {
                    tope_max_segundos = 0;
                    contador_max++;
                    tope_max_minutes = contador_max;
                    topeMax -= 60;
                }
                tope_max_segundos = topeMax;
                while (topeMin - 60 >= 0) {
                    tope_min_segundos = 0;
                    contador_min++;
                    tope_min_minutes = contador_min;
                    topeMin -= 60;
                }
                tope_min_segundos = topeMin;
            } else {
                JOptionPane.showMessageDialog(Ventana.this, "El numero maximo tiene que ser mas grande que el numero minimo", "Fallo del sistema", JOptionPane.OK_OPTION);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(Ventana.this, "Introduce numeros validos","Fallo", JOptionPane.OK_OPTION);
        }
    }

    public void direccionamiento(boolean dir) {
        if (dir == true) {
            direccion = valorDieccion;
        } else {
            direccion = valorDieccion*-1;
        }
    }

    public void run() {
        Integer minutos = 0, segundos = 0, milesimas = 0;
        //min es minutos, seg es segundos y mil es milesimas de segundo
        String min = "", seg = "", mil = "";
        try {
            //cuando es uno el cronometro va hacia delante
            //cuando no es uno el cronometro va hacia atras
            if (direccion == 1) {
                //si va hacia delante empieza desde el tope minimo
                minutos = tope_min_minutes;
                segundos = tope_min_segundos;
                milesimas = 0;
            } else {
                //si va hacia atras empieza desde el tope maximo
                minutos = tope_max_minutes;
                segundos = tope_max_segundos;
                milesimas = 0;
            }
            //Mientras cronometroActivo sea verdadero entonces seguira
            //aumentando el tiempo
            while (cronometroActivo) {
                if (direccion == valorDieccion) {
                    if (segundos != tope_max_segundos || minutos != tope_max_minutes) {
                        Thread.sleep(velocidad);
                        //Incrementamos  milesimas de segundo
                        milesimas += direccion;

                        //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
                        //y las milesimas de segundo de nuevo a 0
                        if (milesimas == 1000) {
                            milesimas = 0;
                            segundos += 1;
                            //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                            //y los segundos vuelven a 0
                            if (segundos == 60) {
                                segundos = 0;
                                minutos++;
                            }
                        }

                        //Esto solamente es estetica para que siempre este en formato
                        //00:00:000
                        if (minutos < 10) {
                            min = "0" + minutos;
                        } else {
                            min = minutos.toString();
                        }
                        if (segundos < 10) {
                            seg = "0" + segundos;
                        } else {
                            seg = segundos.toString();
                        }

                        if (milesimas < 10) {
                            mil = "00" + milesimas;
                        } else if (milesimas < 100) {
                            mil = "0" + milesimas;
                        } else {
                            mil = milesimas.toString();
                        }
                        //Colocamos en la etiqueta la informacion
                        lbContador.setText(min + ":" + seg + ":" + mil);
                    } else {
                        pararCronometro();
                    }
                } else {
                    if (segundos != tope_min_segundos  || milesimas.intValue() != 0) {
                        Thread.sleep(velocidad);
                        //Incrementamos 4 o -4 milesimas de segundo
                        milesimas += direccion;
                        //esto es para que las milesimas cambien despues del 0
                        if (milesimas==-1) {
                            milesimas = 999;
                            segundos -= 1;
                            //Si los segundos llegan a 60 entonces aumenta 1 los minutos
                            //y los segundos vuelven a 0
                            if (segundos == 0) {
                                segundos = 60;
                                minutos--;
                            }
                        }

                        //Esto solamente es estetica para que siempre este en formato
                        //00:00:000
                        if (minutos < 10) {
                            min = "0" + minutos;
                        } else {
                            min = minutos.toString();
                        }
                        if (segundos < 10) {
                            seg = "0" + segundos;
                        } else {
                            seg = segundos.toString();
                        }

                        if (milesimas < 10) {
                            mil = "00" + milesimas;
                        } else if (milesimas < 100) {
                            mil = "0" + milesimas;
                        } else {
                            mil = milesimas.toString();
                        }
                        //Colocamos en la etiqueta la informacion
                        lbContador.setText(min + ":" + seg + ":" + mil);
                    } else {
                        pararCronometro();
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(Ventana.this, "Fallo en la ejecucion","Evento en menu", JOptionPane.OK_OPTION);
        }
    }

    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread(this);
        hilo.start();
    }

    public void pararCronometro() {
        cronometroActivo = false;
//        hilo.stop();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTMax = new javax.swing.JLabel();
        lbTMin = new javax.swing.JLabel();
        tfMin = new javax.swing.JTextField();
        tfMax = new javax.swing.JTextField();
        btEmpezar = new javax.swing.JButton();
        btParar = new javax.swing.JButton();
        btDesc = new javax.swing.JButton();
        bdAcel = new javax.swing.JButton();
        btRalen = new javax.swing.JButton();
        btAsc = new javax.swing.JButton();
        lbContador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbTMax.setText("Tope maximo");

        lbTMin.setText("Tope minimo");

        btEmpezar.setText("Empezar");
        btEmpezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmpezarActionPerformed(evt);
            }
        });

        btParar.setText("Parar");
        btParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPararActionPerformed(evt);
            }
        });

        btDesc.setText("Descendente");
        btDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDescActionPerformed(evt);
            }
        });

        bdAcel.setText("Acelerar");
        bdAcel.setSelected(true);
        bdAcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdAcelActionPerformed(evt);
            }
        });

        btRalen.setText("Ralentizar");
        btRalen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRalenActionPerformed(evt);
            }
        });

        btAsc.setText("Ascendente");
        btAsc.setSelected(true);
        btAsc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAscActionPerformed(evt);
            }
        });

        lbContador.setFont(new java.awt.Font("Segoe UI", 0, 100)); // NOI18N
        lbContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbContador.setText("00:00:000");
        lbContador.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfMax, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbContador, javax.swing.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btAsc)
                    .addComponent(btRalen)
                    .addComponent(btDesc)
                    .addComponent(bdAcel)
                    .addComponent(btEmpezar)
                    .addComponent(btParar))
                .addGap(84, 84, 84))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTMax, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTMin, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btEmpezar))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btParar)
                        .addGap(50, 50, 50)
                        .addComponent(btAsc)
                        .addGap(18, 18, 18)
                        .addComponent(btDesc)
                        .addGap(44, 44, 44)
                        .addComponent(bdAcel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lbContador, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btRalen)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAscActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAscActionPerformed
        direccionamiento(true);
    }//GEN-LAST:event_btAscActionPerformed

    private void btEmpezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEmpezarActionPerformed
        pararCronometro();
        setTopes();
        run();
        iniciarCronometro();

    }//GEN-LAST:event_btEmpezarActionPerformed

    private void btPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPararActionPerformed
        pararCronometro();
    }//GEN-LAST:event_btPararActionPerformed

    private void bdAcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bdAcelActionPerformed
        if (velocidad - 25 > 0) {
            velocidad -= 25;
        } else {
            JOptionPane.showMessageDialog(null, "Ya no se puede acelerar mas");
        }
    }//GEN-LAST:event_bdAcelActionPerformed

    private void btRalenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRalenActionPerformed
        if (velocidad + 25 < 999999) {
            velocidad += 25;
        } else {
            JOptionPane.showMessageDialog(null, "Ya no se puede ralentizar mas");
        }
    }//GEN-LAST:event_btRalenActionPerformed

    private void btDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDescActionPerformed
        direccionamiento(false);
    }//GEN-LAST:event_btDescActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bdAcel;
    private javax.swing.JButton btAsc;
    private javax.swing.JButton btDesc;
    private javax.swing.JButton btEmpezar;
    private javax.swing.JButton btParar;
    private javax.swing.JButton btRalen;
    private javax.swing.JLabel lbContador;
    private javax.swing.JLabel lbTMax;
    private javax.swing.JLabel lbTMin;
    private javax.swing.JTextField tfMax;
    private javax.swing.JTextField tfMin;
    // End of variables declaration//GEN-END:variables
}
