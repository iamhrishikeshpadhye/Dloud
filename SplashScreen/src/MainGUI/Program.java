/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainGUI;

import CreateConnections.ConnectServers;
import CreateConnections.ConnectionEntry;
import SendReceiveFiles.CleanPartFiles;
import SendReceiveFiles.ReceiveParts;
import SendReceiveFiles.SendParts;
import SplitMergeFiles.MergeFiles;
import SplitMergeFiles.SplitFile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author bhushan
 */
public class Program extends javax.swing.JFrame {

    /**
     * Creates new form Program
     */
    public Program() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        upload = new javax.swing.JButton();
        download = new javax.swing.JButton();
        uploadfile = new javax.swing.JLabel();
        downloadfile = new javax.swing.JLabel();
        filelist = new java.awt.ScrollPane();
        message = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        upload.setBackground(new java.awt.Color(62, 40, 221));
        upload.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        upload.setText("Upload File");
        upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadActionPerformed(evt);
            }
        });

        download.setBackground(new java.awt.Color(62, 40, 221));
        download.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        download.setText("Download File");
        download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadActionPerformed(evt);
            }
        });

        filelist.setBackground(new java.awt.Color(249, 247, 247));

        message.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(upload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(download, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(downloadfile, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(uploadfile, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(filelist, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addGap(134, 134, 134)
                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(upload, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(uploadfile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(173, 173, 173)
                        .addComponent(download, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(downloadfile, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(filelist, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34)
                .addComponent(message, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                .addGap(29, 29, 29))
        );

        setSize(new java.awt.Dimension(908, 793));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadActionPerformed
        // TODO add your handling code here:
        this.uploadfile.setText("");
        this.message.setText("");
        String filepath;
        String filename;
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int r = j.showOpenDialog(null);
        if (r == JFileChooser.APPROVE_OPTION) {
            filepath = j.getSelectedFile().getAbsolutePath();
            filename = j.getSelectedFile().getName();
            // set the label to the path of the selected file
            uploadfile.setText("<html>Request to Upload: " + filename + "</html>");

            try {
                int numParts = 5;
                // Splitting in files
                numParts = SplitFile.splitFile(filepath, filename, numParts);
                // Send Split parts to sub-servers
                SendParts sendParts = new SendParts(filename, ipListConnected, numParts);
                sendParts.sendData();
                // Clean the part files
                CleanPartFiles cleanPartFiles = new CleanPartFiles(filename);
                cleanPartFiles.cleanup();
                System.out.println("Uploading Complete");

                // Add the filename to database
                this.dos.writeUTF("Add File");
                this.dos.writeUTF(filename);
                this.dos.writeUTF(this.loggedUser);
                this.update_list();
                this.message.setText("File successfully Uploaded");
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // if the user cancelled the operation 
        else {
            System.out.println("User cancelled operation");
        }
    }//GEN-LAST:event_uploadActionPerformed

    private void downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadActionPerformed
        // TODO add your handling code here:
        this.uploadfile.setText("");
        this.message.setText("");
        String selectedFile = (String) this.list.getSelectedValue();
        downloadfile.setText("<html>Request to Download: " + selectedFile + "</html>");

        // Receive all the parts from the all other servers
        ReceiveParts receiveParts = new ReceiveParts(this.ipListConnected);
        receiveParts.receiveParts(selectedFile);

        System.out.println("Done downloading");

        // Get the parts count from DB
        
        // Merge the parts
        MergeFiles mergeFiles = new MergeFiles();
        mergeFiles.mergeFile(selectedFile, 6);
        System.out.println("Merging completed");
        
        // Clean Up the parts
        CleanPartFiles cleanPartFiles = new CleanPartFiles(selectedFile);
        cleanPartFiles.cleanup();
        this.update_list();
        this.message.setText("File successfully Downloaded in ~/Downloads/");
    }//GEN-LAST:event_downloadActionPerformed

    public void update_list() {
        try {
            this.dos.writeUTF("Get Files List");
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Get the file list from server
        int size = 0;
        try {
            size = Integer.parseInt(this.dis.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            try {
                fileList.add((String) this.dis.readUTF());
            } catch (IOException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Set the list of files
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        fileList.stream().forEach((file) -> {
            listModel.addElement(file);
        });

        //Create the list and put it in a scroll pane.
        this.list = new JList(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.list.setSelectedIndex(0);
        this.list.setVisibleRowCount(25);
        this.filelist.add(this.list);
    }

    public void show_program(Socket client, String loggedUser) {
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
            java.util.logging.Logger.getLogger(Program.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Program.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            Program p = new Program();

            @Override
            public void run() {
                p.setVisible(true);
                p.client = client;
                p.connectServers = new ConnectServers();
                p.loggedUser = loggedUser;

                if (p.is == null) {
                    try {
                        p.is = p.client.getInputStream();
                        p.os = p.client.getOutputStream();
                        p.dis = new DataInputStream(p.is);
                        p.dos = new DataOutputStream(p.os);

                        // get the IPList of connected devices
                        p.ipListConnected = p.connectServers.connectSideServers(p.dos, p.dis);
                    } catch (IOException ex) {
                        Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    p.update_list();
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton download;
    private javax.swing.JLabel downloadfile;
    private java.awt.ScrollPane filelist;
    private javax.swing.JLabel message;
    private javax.swing.JButton upload;
    private javax.swing.JLabel uploadfile;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JList list;
    private Socket client;
    private InputStream is;
    private OutputStream os;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ConnectServers connectServers;
    private String loggedUser;
    private List<ConnectionEntry> ipListConnected;
}