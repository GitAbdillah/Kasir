/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apl_kasir_abdillah;

import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author acer
 */
public class loginUser extends javax.swing.JFrame {
    Connection con;
    
    public void connect(){
        con=null;
        try{
        Class.forName("com.mysql.jdbc.Driver");        
        con=DriverManager.getConnection("jdbc:mysql://127.0.0.1/db_abdillah","root","");
        }
        catch (Exception e){
        System.out.print("EROR KUERI KE DATABASE:\n" + e + "\n\n");
           }
    }
    /**
     * Creates new form loginUser
     */
    public loginUser() {
        initComponents();
    }
    public void a(){
            connect();
        try{
            PreparedStatement ps=con.prepareStatement("SELECT level FROM user WHERE username=? AND password=?");
            ps.setString(1, nama.getText());
            ps.setString(2, pw.getText());
            ResultSet r=ps.executeQuery();
            if(r.next()){
                if("Admin".equals(r.getString("level"))){
                JOptionPane.showMessageDialog(this,"Selamat Datang, Anda Login Sebagai ADMIN");
                menuUtama m = new menuUtama();
                m.show();
                this.dispose();
                }else if("Kasir".equals(r.getString("level"))){
                JOptionPane.showMessageDialog(this, "Selamat Datang, Anda Login Sebagai KASIR!");
                menuUtama2 m = new menuUtama2();
                this.dispose();
                m.show();
                
                this.dispose();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Username atau password salah!");
                nama.setText("");
                pw.setText("");
                nama.requestFocus();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } 
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new java.awt.Panel();
        nama = new javax.swing.JTextField();
        pw = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                namaKeyTyped(evt);
            }
        });
        panel1.add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 160, 30));

        pw.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        pw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pwKeyTyped(evt);
            }
        });
        panel1.add(pw, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 160, 30));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("LOGIN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("USERNAME");
        panel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("PASSWORD");
        panel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, -1, -1));

        getContentPane().add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 530, 380));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        a();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void namaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namaKeyTyped
        // TODO add your handling code here:
        if ( nama.getText().length() == 110 ) {
        evt.consume();
        }
    }//GEN-LAST:event_namaKeyTyped

    private void pwKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwKeyTyped
        // TODO add your handling code here:
        if ( pw.getText().length() == 150) {
        evt.consume();
        }
    }//GEN-LAST:event_pwKeyTyped

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
            java.util.logging.Logger.getLogger(loginUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField nama;
    private java.awt.Panel panel1;
    private javax.swing.JPasswordField pw;
    // End of variables declaration//GEN-END:variables
}
