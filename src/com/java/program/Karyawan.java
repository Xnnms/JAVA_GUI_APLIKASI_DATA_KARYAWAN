package com.java.program;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Karyawan {
    private JPanel Main;
    private JTextField txtnik;
    private JTextField txtname;
    private JTextField txtjabatan;
    private JTextField txtalamat;
    private JTextField txtno_telp;
    private JButton tambahButton;
    private JButton refreshButton;
    private JButton updateButton;
    private JButton hapusButton;
    private JTable table_1;
    private JButton searchButton;
    private JTextField txtsearch;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Karyawan");
        frame.setContentPane(new Karyawan().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javakaryawan", "root", "");
            System.out.println("Koneksi Berhasil");

//            stmt = con.createStatement();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void table_load(){
        try
        {
            pst = con.prepareStatement("select * from karyawan");
            ResultSet rs = pst.executeQuery();
            table_1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }




    public Karyawan() {
        connect();
        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nik,nama,jabatan,alamat,no_telp;

                nik = txtnik.getText();
                nama = txtname.getText();
                jabatan = txtjabatan.getText();
                alamat = txtalamat.getText();
                no_telp = txtno_telp.getText();

                try {
                    pst = con.prepareStatement("insert into karyawan(nik,nama,jabatan,alamat,no_telp)values(?,?,?,?,?,?)");
                    pst.setString(1, nik);
                    pst.setString(2, nama);
                    pst.setString(3, jabatan);
                    pst.setString(4, alamat);
                    pst.setString(5, no_telp);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    table_load();
                    txtnik.setText("");
                    txtname.setText("");
                    txtjabatan.setText("");
                    txtalamat.setText("");
                    txtno_telp.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_load();
            }
        });


//

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String id = txtsearch.getText();

                    pst = con.prepareStatement("select nik,nama,jabatan,alamat,no_telp from karyawan where id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String nik = rs.getString(1);
                        String nama = rs.getString(2);
                        String jabatan = rs.getString(3);
                        String alamat = rs.getString(4);
                        String no_telp = rs.getString(5);

                        txtnik.setText(nik);
                        txtname.setText(nama);
                        txtjabatan.setText(jabatan);
                        txtalamat.setText(alamat);
                        txtno_telp.setText(no_telp);

                    }
                    else
                    {
                        txtnik.setText("");
                        txtname.setText("");
                        txtjabatan.setText("");
                        txtalamat.setText("");
                        txtno_telp.setText("");
                        JOptionPane.showMessageDialog(null,"Tidak ditemukan");

                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nik,nama,jabatan,alamat,no_telp,id;
                nik = txtnik.getText();
                nama = txtname.getText();
                jabatan = txtjabatan.getText();
                alamat = txtalamat.getText();
                no_telp = txtno_telp.getText();
                id = txtsearch.getText();

                try {
                    pst = con.prepareStatement("update karyawan set nik = ?,nama = ?,jabatan = ?,alamat=?,no_telp = ? where id = ?");

                    pst.setString(1, nik);
                    pst.setString(2, nama);
                    pst.setString(3, jabatan);
                    pst.setString(4, alamat);
                    pst.setString(5, no_telp);
                    pst.setString(6, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data telah berhasil diupdate");
                    table_load();
                    txtnik.setText("");
                    txtname.setText("");
                    txtjabatan.setText("");
                    txtalamat.setText("");
                    txtno_telp.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }


            }
        });
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id;
                id = txtsearch.getText();

                try {
                    pst = con.prepareStatement("delete from karyawan  where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
                    table_load();
                    txtnik.setText("");
                    txtname.setText("");
                    txtjabatan.setText("");
                    txtalamat.setText("");
                    txtno_telp.setText("");
                    txtname.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }


            }
        });
    }
}
