/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apl_kasir_abdillah;

import com.sun.glass.events.KeyEvent;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class formDetail2 extends javax.swing.JFrame {
DefaultTableModel table = new DefaultTableModel();
    /**
     * Creates new form formDetail2
     */
  String Tanggal;
    private DefaultTableModel model;
    
    public void totalBiaya(){
        int jumlahBaris = tabel.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tabel.getValueAt(i, 5).toString());
            hargaBarang = Integer.parseInt(tabel.getValueAt(i, 6).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }
        total.setText(String.valueOf(totalBiaya));
        totalbadag.setText("Rp "+ totalBiaya +",00");
    }
    
    private void autonumber(){
        try {
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM detailpenjualan ORDER BY id_detail DESC";
            ResultSet r = s.executeQuery(sql);
            if (r.next()) {
                String NoFaktur = r.getString("id_detail").substring(2);
                String TR = "" +(Integer.parseInt(NoFaktur)+1);
                String Nol = "";
                
                if(TR.length()==1)
                {Nol = "000";}
                else if(TR.length()==2)
                {Nol = "00";}
                else if(TR.length()==3)
                {Nol = "0";}
                else if(TR.length()==4)
                {Nol = "";}
                iddetail.setText("1" + Nol + TR);
                idpenjualan.setText("1" + Nol + TR);
            } else {
                iddetail.setText("10001");
                idpenjualan.setText("10001");
            }
            r.close();
            s.close();
        } catch (Exception e) {
            System.out.println("autonumber error");
        }
    }
    
    public void loadData(){
        DefaultTableModel model = (DefaultTableModel) tabel.getModel();
        model.addRow(new Object[]{
            iddetail.getText(),
            idpenjualan.getText(),
            idproduk.getSelectedItem().toString(),
            namaproduk.getSelectedItem().toString(),
            namapelanggan.getText(),
            jumlah.getText(),
            harga.getText(),
            total.getText()
        });
    }
    
    public void kosong(){
        DefaultTableModel model = (DefaultTableModel) tabel.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void utama(){
        iddetail.setText("");
        idproduk.setSelectedItem(null);
        namaproduk.setSelectedItem(null);
        harga.setText("");
        jumlah.setText("");
        autonumber();
    }
    
    public void clear(){
        namapelanggan.setText("");
        total.setText("0");
        bayar.setText("0");
        kembalian.setText("0");
        totalbadag.setText("0");
    }
    
    public void clear2(){
        idproduk.setSelectedItem(null);
        namaproduk.setSelectedItem(null);
        harga.setText("");
        jumlah.setText("");
    }
    
    public void tambahTransaksi(){
        int jumlahh, hharga, ttotal;
        
        jumlahh = Integer.valueOf(jumlah.getText());
        hharga = Integer.valueOf(harga.getText());
        ttotal = jumlahh * hharga;
        
        total.setText(String.valueOf(ttotal));
        
        loadData();
        totalBiaya();
        clear2();
        harga.requestFocus();
    }
    
  private void ambilData(){
    try {
            
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM produk";
            ResultSet r = s.executeQuery(sql);
            
            while (r.next()) {
            idproduk.addItem(r.getString("id_produk"));
            namaproduk.addItem(r.getString("nama_produk"));
            }
        }catch (Exception e) {
            
        }
}
  
    public formDetail2() {
        initComponents();
        ambilData();
        koneksi.getKoneksi();
        model = new DefaultTableModel();
        
        tabel.setModel(model);
        model.addColumn("ID Detail");
        model.addColumn("ID Penjualan");
        model.addColumn("ID Produk");
        model.addColumn("Nama Produk");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Jumlah");
        model.addColumn("Harga");
        model.addColumn("Total Harga");
        
        
        utama();
        Date date = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        
        tanggal.setText(s.format(date));
        total.setText("0");
        bayar.setText("0");
        kembalian.setText("0");
        namapelanggan.requestFocus();
    }
    
    private void tambahData(){
    DefaultTableModel model = (DefaultTableModel) tabel.getModel();
        
        try {
            Connection c = koneksi.getKoneksi();
            int baris = tabel.getRowCount();
            
            for (int i = 0; i < baris; i++) {
                String sql = "INSERT INTO detailpenjualan(`tanggal_transaksi`, `id_detail`, `id_produk`, `id_penjualan`, `nama_pelanggan`, `nama_produk`, `jml_produk`,`subtotal`) VALUES('"
                        + tanggal.getText() + "','" + tabel.getValueAt(i, 0) +"','"+ tabel.getValueAt(i, 2) +"','"+ tabel.getValueAt(i, 1) 
                        +"','"+ tabel.getValueAt(i, 4) +"','"+ tabel.getValueAt(i, 3) +"','"+ tabel.getValueAt(i, 5) 
                        +"', '"+ tabel.getValueAt(i, 7) +"')";
                PreparedStatement p = c.prepareStatement(sql);
                p.executeUpdate();
                p.close();
            }
        }catch(SQLException | HeadlessException e){
            System.out.println(e);
                    }
        clear();
        utama();
        autonumber();
        kosong();
        totalbadag.setText("Rp. 0");    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bayar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        kembalian = new javax.swing.JTextField();
        namapelanggan = new javax.swing.JTextField();
        iddetail = new javax.swing.JTextField();
        idpenjualan = new javax.swing.JTextField();
        idproduk = new javax.swing.JComboBox<>();
        namaproduk = new javax.swing.JComboBox<>();
        harga = new javax.swing.JTextField();
        jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        tanggal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        totalbadag = new javax.swing.JTextField();
        cari = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setEnabled(false);
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("ID DETAIL");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 32, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("PRODUK");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 116, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("ID PENJUALAN");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 76, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("NAMA PELANGGAN");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 163, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("JUMLAH PRODUK");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 234, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("HARGA");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 201, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("TOTAL BAYAR");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 273, -1, -1));

        total.setEnabled(false);
        jPanel1.add(total, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 294, 169, 27));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("BAYAR");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 339, -1, -1));

        bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarActionPerformed(evt);
            }
        });
        jPanel1.add(bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 365, 169, 27));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("KEMBALIAN");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 410, -1, -1));

        kembalian.setEnabled(false);
        jPanel1.add(kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 431, 169, 27));
        jPanel1.add(namapelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 161, 159, -1));

        iddetail.setEnabled(false);
        jPanel1.add(iddetail, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 30, 59, -1));

        idpenjualan.setEnabled(false);
        jPanel1.add(idpenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 74, 59, -1));

        idproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idprodukActionPerformed(evt);
            }
        });
        jPanel1.add(idproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 114, 61, -1));

        namaproduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaprodukActionPerformed(evt);
            }
        });
        jPanel1.add(namaproduk, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 114, 118, -1));

        harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hargaKeyTyped(evt);
            }
        });
        jPanel1.add(harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 199, 100, -1));

        jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jumlahActionPerformed(evt);
            }
        });
        jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jumlahKeyTyped(evt);
            }
        });
        jPanel1.add(jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 232, 46, -1));

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabel);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 161, 611, 287));

        tanggal.setEnabled(false);
        tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tanggalActionPerformed(evt);
            }
        });
        jPanel1.add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(463, 114, 88, -1));

        jLabel11.setText("TANGGAL");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 117, -1, -1));

        jButton1.setText("TAMBAH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 105, -1, 38));

        jButton2.setText("HAPUS");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 105, -1, 38));

        jButton3.setText("BAYAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(58, 497, -1, 42));

        totalbadag.setBackground(new java.awt.Color(102, 204, 255));
        totalbadag.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        totalbadag.setText("Rp. 0");
        totalbadag.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        totalbadag.setEnabled(false);
        totalbadag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalbadagActionPerformed(evt);
            }
        });
        jPanel1.add(totalbadag, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 482, 452, 57));

        cari.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(472, 63, 528, 25));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton4.setText("CARI");
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(399, 63, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel1.setText("TRANSAKSI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(451, 451, 451)
                .addComponent(jLabel1)
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 610));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tanggalActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tabel.getModel();
        int row = tabel.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        bayar.setText("0");
        kembalian.setText("0");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void totalbadagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalbadagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalbadagActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahTransaksi();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarActionPerformed
        // TODO add your handling code here:
        int Total, Bayar, Kembalian;
        
        Total = Integer.valueOf(total.getText());
        Bayar = Integer.valueOf(bayar.getText());
        
        if (Total > Bayar) {
            JOptionPane.showMessageDialog(null, "Uang tidak cukup untuk melakukan pembayaran");
        } else {
            Kembalian = Bayar - Total;
            kembalian.setText(String.valueOf(Kembalian));
        }
    }//GEN-LAST:event_bayarActionPerformed

    private void idprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idprodukActionPerformed
        // TODO add your handling code here:
        try{
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT id_produk FROM produk";
        }catch(Exception e){}
    }//GEN-LAST:event_idprodukActionPerformed

    private void namaprodukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaprodukActionPerformed
        // TODO add your handling code here:
        try{
            Connection c = koneksi.getKoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT nama_produk FROM produk";
        }catch(Exception e){}
    }//GEN-LAST:event_namaprodukActionPerformed

    private void jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jumlahActionPerformed

    private void jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jumlahKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if(!(((karakter >=  '0') && (karakter <= '9' || (karakter == KeyEvent.VK_BACKSPACE) || (karakter == KeyEvent.VK_DELETE))))){
            getToolkit().beep();
            evt.consume();
        }
        if ( jumlah.getText().length() == 11 ) {
        evt.consume();
        }
    }//GEN-LAST:event_jumlahKeyTyped

    private void hargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hargaKeyTyped
        // TODO add your handling code here:
        char karakter = evt.getKeyChar();
        if(!(((karakter >=  '0') && (karakter <= '9' || (karakter == KeyEvent.VK_BACKSPACE) || (karakter == KeyEvent.VK_DELETE))))){
            getToolkit().beep();
            evt.consume();
        }
        if ( harga.getText().length() == 10 ) {
        evt.consume();
        }
    }//GEN-LAST:event_hargaKeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(formDetail2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formDetail2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formDetail2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formDetail2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formDetail2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bayar;
    private javax.swing.JTextField cari;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField iddetail;
    private javax.swing.JTextField idpenjualan;
    private javax.swing.JComboBox<String> idproduk;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jumlah;
    private javax.swing.JTextField kembalian;
    private javax.swing.JTextField namapelanggan;
    private javax.swing.JComboBox<String> namaproduk;
    private javax.swing.JTable tabel;
    private javax.swing.JTextField tanggal;
    private javax.swing.JTextField total;
    private javax.swing.JTextField totalbadag;
    // End of variables declaration//GEN-END:variables
}
