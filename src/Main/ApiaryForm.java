package Main;

import Apiary.Apiary;
import Apiary.BeeMediator;
import Apiary.Beehive;
import Apiary.Room;
import Bees.Bee;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Glen
 */
public class ApiaryForm extends javax.swing.JFrame
{

    Apiary apiary;
    BeeMediator mediator;
    int xoff;
    int yoff;
    Graphics g;

    /**
     * Creates new form ApiaryForm
     */
    public ApiaryForm()
    {
        initComponents();
        apiary = Apiary.getApiary();
        mediator = BeeMediator.getMediator();
        xoff = -100;
        yoff = -100;
        g = jPanel1.getGraphics();
    }

    List<Color> colors;

    private Color getColor(int id)
    {
        if (colors == null)
        {
            colors = new ArrayList<Color>();
        }
        switch (id)
        {
            case 0:
                return Color.BLUE;
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.CYAN;
            default:
                Random random = new Random();
                while (colors.size() <= id)
                {
                    colors.add(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
                }
                return colors.get(id);
        }
    }

    private void draw()
    {
        Graphics g2 = jPanel2.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, jPanel2.getWidth(), jPanel2.getHeight());
        jTextArea1.setText("");
        int index = 0;
        for (Beehive hive : apiary.getHives())
        {

            int population = 0;
            List<Bee> bees = BeeMediator.getMediator().getBees();
            for (Bee bee : bees)
            {
                if (bee.getOwner().getID() == hive.getID())
                {
                    population++;
                }
            }
            if (population > 0)
            {
                jTextArea1.append("Hive " + hive.getID() + "\t Pop: " + population + "\t Food: " + hive.getFood() + "\tSpecies: " + hive.getSpecies() + "\n");

                g2.setColor(Color.BLACK);
                g2.drawString("Hive " + hive.getID(), 20, 15 + index * 20);
                g2.setColor(getColor(hive.getID()));
                g2.fillRect(0, index * 20 + 1, 18, 18);
                index++;
            }
        }

        g.setColor(Color.white);
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.black);
        for (int x = 0; x <= 10; x++)
        {
            g.drawLine(x * 40, 0, x * 40, 400);
        }
        for (int y = 0; y <= 10; y++)
        {
            g.drawLine(0, y * 40, 400, y * 40);
        }

        for (Beehive hive : apiary.getHives())
        {
            List<Room> rooms = hive.getRooms();
            for (Room room : rooms)
            {
                if (room.isBuilt())
                {
                    g.setColor(getColor(hive.getID()));
                    g.fillRect((room.getX() + xoff) * 40 + 1, (room.getY() + yoff) * 40 + 1, 38, 38);
                    g.setColor(Color.BLACK);
                    g.drawRect((room.getX() + xoff) * 40 + 1, (room.getY() + yoff) * 40 + 1, 38, 38);
                    g.drawRect((room.getX() + xoff) * 40 + 2, (room.getY() + yoff) * 40 + 2, 36, 36);
                } else
                {
                    float f = room.percentageDone();
                    g.setColor(getColor(hive.getID()));
                    g.fillRect((room.getX() + xoff) * 40 + 1 + 20 - (int) (20 * f), (room.getY() + yoff) * 40 + 1 + 20 - (int) (20 * f), (int) (38 * f), (int) (38 * f));
                }
            }
        }

        for (Point2D flower : apiary.getFlowers())
        {
            g.setColor(Color.blue);
            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 2; y++)
                {
                    g.fillOval((int) (flower.getX() + xoff) * 40 + x * 19 + 1, (int) (flower.getY() + yoff) * 40 + y * 19 + 1, 18, 18);
                }
            }
            g.setColor(Color.yellow);
            g.fillOval((int) (flower.getX() + xoff) * 40 + 10, (int) (flower.getY() + yoff) * 40 + 10, 20, 20);
        }

        List<Bee> bees = BeeMediator.getMediator().getBees();
        for (Bee bee : bees)
        {
            g.setColor(getColor(bee.getOwner().getID()));
            g.fillOval((int) ((bee.getX() + xoff) * 40) - 1, (int) ((bee.getY() + yoff) * 40) - 1, 3, 3);
            g.setColor(Color.BLACK);
            g.drawOval((int) ((bee.getX() + xoff) * 40) - 1, (int) ((bee.getY() + yoff) * 40) - 1, 3, 3);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setPreferredSize(new java.awt.Dimension(401, 401));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        jTextField1.setText("command");
        jTextField1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("run");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Tick");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("^");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">");
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("v");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("<");
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Tick x10");
        jButton7.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Tick x100");
        jButton8.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton8ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 126, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setText("Key");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jButton6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jButton4)))
                                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton5)
                                            .addComponent(jButton3)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(jButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        mediator.sendTick();
        draw();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton7ActionPerformed
    {//GEN-HEADEREND:event_jButton7ActionPerformed
        for (int i = 0; i < 10; i++)
        {
            mediator.sendTick();
            draw();
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton8ActionPerformed
    {//GEN-HEADEREND:event_jButton8ActionPerformed
        for (int i = 0; i < 100; i++)
        {
            mediator.sendTick();
            draw();
            try
            {
                Thread.sleep(50);
            } catch (InterruptedException ex)
            {
            }
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        yoff++;
        draw();// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
        yoff--;
        draw(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
        xoff++;
        draw(); // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
        xoff--;
        draw();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        Interface.command(jTextField1.getText());

        g.setColor(Color.white);
        g.fillRect(0, 0, 400, 400);
        g.setColor(Color.black);
        for (int x = 0; x <= 10; x++)
        {
            g.drawLine(x * 40, 0, x * 40, 400);
        }
        for (int y = 0; y <= 10; y++)
        {
            g.drawLine(0, y * 40, 400, y * 40);
        }

        for (Beehive hive : apiary.getHives())
        {
            List<Room> rooms = hive.getRooms();
            for (Room room : rooms)
            {
                if (room.isBuilt())
                {
                    g.setColor(getColor(hive.getID()));
                    g.fillRect((room.getX() + xoff) * 40 + 1, (room.getY() + yoff) * 40 + 1, 38, 38);
                    g.setColor(Color.BLACK);
                    g.drawRect((room.getX() + xoff) * 40 + 1, (room.getY() + yoff) * 40 + 1, 38, 38);
                    g.drawRect((room.getX() + xoff) * 40 + 2, (room.getY() + yoff) * 40 + 2, 36, 36);
                } else
                {
                    float f = room.percentageDone();
                    g.setColor(getColor(hive.getID()));
                    g.fillRect((room.getX() + xoff) * 40 + 1 + 20 - (int) (20 * f), (room.getY() + yoff) * 40 + 1 + 20 - (int) (20 * f), (int) (38 * f), (int) (38 * f));
                }
            }
        }

        for (Point2D flower : apiary.getFlowers())
        {
            g.setColor(Color.blue);
            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 2; y++)
                {
                    g.fillOval((int) (flower.getX() + xoff) * 40 + x * 19 + 1, (int) (flower.getY() + yoff) * 40 + y * 19 + 1, 18, 18);
                }
            }
            g.setColor(Color.yellow);
            g.fillOval((int) (flower.getX() + xoff) * 40 + 10, (int) (flower.getY() + yoff) * 40 + 10, 20, 20);
        }

        List<Bee> bees = BeeMediator.getMediator().getBees();
        for (Bee bee : bees)
        {
            g.setColor(getColor(bee.getOwner().getID()));
            g.fillOval((int) ((bee.getX() + xoff) * 40) - 1, (int) ((bee.getY() + yoff) * 40) - 1, 3, 3);
            g.setColor(Color.BLACK);
            g.drawOval((int) ((bee.getX() + xoff) * 40) - 1, (int) ((bee.getY() + yoff) * 40) - 1, 3, 3);
        }// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextField1ActionPerformed
    {//GEN-HEADEREND:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
