
package pf.gui;

import pf.item.ItemCustomTableModel;
import pf.item.Item;
import pf.planogram.PlanogramCustomTabelModel;
import pf.Processor;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import pf.Launcher;
import pf.UserSettings;

/**
 *
 * @author      Kieran Skvortsov
 * employee#    72141
 */
public class Main extends javax.swing.JFrame {
    
    private static PipedInputStream inputStream = new PipedInputStream();
    private static Processor processor = new Processor(inputStream);
    
    //custom models for tabels and how they display information
    private static ItemCustomTableModel itemCTM = new ItemCustomTableModel();
    private static PlanogramCustomTabelModel planogramCTM = new PlanogramCustomTabelModel();
    
    //instance variable to reference settings to make sure only one Settings
    //  window is open at any time
    private static Settings settings;
    
    public Main() {
        initComponents();
        setLocationRelativeTo(null);

        //various initialization for the Item table
        //TODO: move this somewhere else i.e. define it in a custom class
        table_items.setModel(itemCTM);
        table_items.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table_items.getColumnModel().getColumn(0).setPreferredWidth(60);
        table_items.getColumnModel().getColumn(1).setPreferredWidth(80);
        table_items.getColumnModel().getColumn(2).setPreferredWidth(110);
        table_items.getColumnModel().getColumn(3).setPreferredWidth(320);
        table_items.getColumnModel().getColumn(5).setPreferredWidth(153);
        
        for(int i = 0; i < table_items.getColumnCount(); ++i)
            table_items.getColumnModel().getColumn(i).setResizable(false);
        
        table_planograms.setModel(planogramCTM);
        table_planograms.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table_planograms.getColumnModel().getColumn(0).setPreferredWidth(60);
        table_planograms.getColumnModel().getColumn(1).setPreferredWidth(390);
        
        this.setTitle(Launcher.APP_ARTIFACTID);
        this.pack();
        
        //start the input/output piped connection for System::out
        startPipeThread();
        
        //if startup settings say to upload local planograms
        if(Boolean.parseBoolean(UserSettings.getProperty("startup.upload")))
            menuItem_reuploadActionPerformed(null);
        
        //if startup settings say to download from database
        if(Boolean.parseBoolean(UserSettings.getProperty("startup.download")))
            menuItem_pullFromDatabaseActionPerformed(null);
        
        //hide the developer panel by default
        panel_developer.setVisible(false);
        
        //enable the developer console for view if the program was run with the
        //  dev system property
        checkBoxMenuItem_developer.setEnabled(Boolean.parseBoolean(System.getProperty("dev")));
        
        //repack the UI so the JFrame updates the developer console is no longer
        //  there and makes adjustments accordingly
        pack();
    }
    
    /**
     * Starts a thread to maintain a piped connection with the processor's
     * output stream
     */
    private void startPipeThread() {
        Thread streamPipeReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //run indefinitely
                while(true) {
                    //create a new input stream if null
                    if(inputStream != null) {
                        try {
                            int data;

                            while((data = inputStream.read()) != -1) {
                                textArea_output.append(Character.toString((char)data));
                                textArea_output.setCaretPosition(textArea_output.getText().length());
                            }
                        } catch (IOException ex) { System.err.println(ex); }
                    }
                    
                    try {
                        //close and discard the inputstream
                        if(inputStream != null) {
                            inputStream.close();
                            inputStream = null;
                        }
                        
                        //create a new input stream
                        inputStream = new PipedInputStream();
                        
                        //bind the new input stream to the processor
                        processor.bindPipe(inputStream);
                    } catch (IOException ex) { System.err.println(ex); }
                }
            }
        });
        
        streamPipeReaderThread.start();
    }
    
    /**
     * Directly sets the progress of the progress bar
     * 
     * @param progress Progress of an execution from 0-100
     */
    public static void setProgress(int progress) {
        progressBar.setValue(progress);
    }
    
    /**
     * Calculates and updates the progress of the progress bar
     * 
     * @param value The current value of progress
     * @param maximum The maximum value progress can reach
     */
    public static void updateProgress(int value, int maximum) {
        float progress = ((float)value/maximum)*100f;
        progressBar.setValue((int)progress);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_main = new javax.swing.JPanel();
        scrollPaneItemTable = new javax.swing.JScrollPane();
        table_items = new javax.swing.JTable();
        panel_developer = new javax.swing.JPanel();
        scrollPane_textArea_output = new javax.swing.JScrollPane();
        textArea_output = new javax.swing.JTextArea();
        button_publish = new javax.swing.JButton();
        button_reset = new javax.swing.JButton();
        panelGroupBottom = new javax.swing.JPanel();
        textField_input = new javax.swing.JTextField();
        comboBox_searchType = new javax.swing.JComboBox<>();
        button_search = new javax.swing.JButton();
        button_clear = new javax.swing.JButton();
        scrollPane_table_planograms = new javax.swing.JScrollPane();
        table_planograms = new javax.swing.JTable();
        progressBar = new javax.swing.JProgressBar();
        button_upload = new javax.swing.JButton();
        button_print = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        menu_file = new javax.swing.JMenu();
        menuItem_upload = new javax.swing.JMenuItem();
        menuItem_reupload = new javax.swing.JMenuItem();
        menuItem_pullFromDatabase = new javax.swing.JMenuItem();
        menuItem_print = new javax.swing.JMenuItem();
        separator01 = new javax.swing.JPopupMenu.Separator();
        menuItem_checkForUpdates = new javax.swing.JMenuItem();
        menuItem_github = new javax.swing.JMenuItem();
        separator02 = new javax.swing.JPopupMenu.Separator();
        menuItem_about = new javax.swing.JMenuItem();
        menuItem_settings = new javax.swing.JMenuItem();
        menuItem_exit = new javax.swing.JMenuItem();
        menu_view = new javax.swing.JMenu();
        checkBoxMenuItem_developer = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PlanogramFinder");
        setName("frameMain"); // NOI18N
        setResizable(false);

        scrollPaneItemTable.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        table_items.setFont(new java.awt.Font("Monospaced", 1, 12)); // NOI18N
        table_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Print", "UPC", "SKU", "Description", "Fixture", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_items.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table_items.setGridColor(new java.awt.Color(51, 51, 51));
        table_items.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table_items.setShowGrid(true);
        table_items.setShowVerticalLines(false);
        scrollPaneItemTable.setViewportView(table_items);

        panel_developer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        textArea_output.setEditable(false);
        textArea_output.setBackground(new java.awt.Color(51, 51, 51));
        textArea_output.setColumns(20);
        textArea_output.setFont(new java.awt.Font("Cascadia Code", 0, 12)); // NOI18N
        textArea_output.setRows(5);
        scrollPane_textArea_output.setViewportView(textArea_output);

        button_publish.setBackground(new java.awt.Color(153, 153, 153));
        button_publish.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button_publish.setForeground(new java.awt.Color(51, 51, 51));
        button_publish.setText("PUBLISH");
        button_publish.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_publish.setMaximumSize(new java.awt.Dimension(26, 26));
        button_publish.setMinimumSize(new java.awt.Dimension(26, 26));
        button_publish.setPreferredSize(new java.awt.Dimension(26, 26));
        button_publish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_publishActionPerformed(evt);
            }
        });

        button_reset.setBackground(new java.awt.Color(115, 72, 72));
        button_reset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button_reset.setForeground(new java.awt.Color(204, 204, 204));
        button_reset.setText("RESET");
        button_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_reset.setMaximumSize(new java.awt.Dimension(26, 26));
        button_reset.setMinimumSize(new java.awt.Dimension(26, 26));
        button_reset.setPreferredSize(new java.awt.Dimension(26, 26));
        button_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_developerLayout = new javax.swing.GroupLayout(panel_developer);
        panel_developer.setLayout(panel_developerLayout);
        panel_developerLayout.setHorizontalGroup(
            panel_developerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_developerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane_textArea_output)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_developerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(button_publish, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .addComponent(button_reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panel_developerLayout.setVerticalGroup(
            panel_developerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_developerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_developerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_developerLayout.createSequentialGroup()
                        .addComponent(button_publish, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(59, 59, 59))
                    .addGroup(panel_developerLayout.createSequentialGroup()
                        .addGroup(panel_developerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(scrollPane_textArea_output, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(button_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelGroupBottom.setBackground(new java.awt.Color(51, 51, 51));
        panelGroupBottom.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        textField_input.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textField_input.setMargin(new java.awt.Insets(3, 3, 3, 3));
        textField_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField_inputActionPerformed(evt);
            }
        });

        comboBox_searchType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SKU", "UPC", "WORD" }));
        comboBox_searchType.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        comboBox_searchType.setMinimumSize(new java.awt.Dimension(72, 34));

        button_search.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button_search.setText("SEARCH");
        button_search.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_searchActionPerformed(evt);
            }
        });

        button_clear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        button_clear.setText("CLEAR");
        button_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelGroupBottomLayout = new javax.swing.GroupLayout(panelGroupBottom);
        panelGroupBottom.setLayout(panelGroupBottomLayout);
        panelGroupBottomLayout.setHorizontalGroup(
            panelGroupBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGroupBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelGroupBottomLayout.createSequentialGroup()
                        .addComponent(textField_input, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBox_searchType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(button_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(button_clear, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelGroupBottomLayout.setVerticalGroup(
            panelGroupBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGroupBottomLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGroupBottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField_input, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBox_searchType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        table_planograms.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Include", "Planogram"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrollPane_table_planograms.setViewportView(table_planograms);

        button_upload.setBackground(new java.awt.Color(153, 153, 153));
        button_upload.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button_upload.setForeground(new java.awt.Color(51, 51, 51));
        button_upload.setText("UPLOAD");
        button_upload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_upload.setMaximumSize(new java.awt.Dimension(26, 26));
        button_upload.setMinimumSize(new java.awt.Dimension(26, 26));
        button_upload.setPreferredSize(new java.awt.Dimension(26, 26));
        button_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_uploadActionPerformed(evt);
            }
        });

        button_print.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        button_print.setText("PRINT");
        button_print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button_print.setMaximumSize(new java.awt.Dimension(26, 26));
        button_print.setMinimumSize(new java.awt.Dimension(26, 26));
        button_print.setPreferredSize(new java.awt.Dimension(26, 26));
        button_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_mainLayout = new javax.swing.GroupLayout(panel_main);
        panel_main.setLayout(panel_mainLayout);
        panel_mainLayout.setHorizontalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panel_mainLayout.createSequentialGroup()
                                .addComponent(panelGroupBottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(scrollPane_table_planograms, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(button_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_mainLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(button_upload, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(panel_mainLayout.createSequentialGroup()
                                .addComponent(scrollPaneItemTable, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(6, 6, 6))
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addComponent(panel_developer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        panel_mainLayout.setVerticalGroup(
            panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneItemTable, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_developer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panel_mainLayout.createSequentialGroup()
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_upload, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button_print, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelGroupBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane_table_planograms, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        menu_file.setText("File");

        menuItem_upload.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_upload.setText("Upload Planogram");
        menuItem_upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_uploadActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_upload);

        menuItem_reupload.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_reupload.setText("Reupload Planograms");
        menuItem_reupload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_reuploadActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_reupload);

        menuItem_pullFromDatabase.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_pullFromDatabase.setText("Pull from Database");
        menuItem_pullFromDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_pullFromDatabaseActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_pullFromDatabase);

        menuItem_print.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_print.setText("Print Selection");
        menu_file.add(menuItem_print);
        menu_file.add(separator01);

        menuItem_checkForUpdates.setText("Check for Updates");
        menuItem_checkForUpdates.setEnabled(false);
        menu_file.add(menuItem_checkForUpdates);

        menuItem_github.setText("See on GitHub");
        menuItem_github.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_githubActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_github);
        menu_file.add(separator02);

        menuItem_about.setText("About");
        menuItem_about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_aboutActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_about);

        menuItem_settings.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_COMMA, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_settings.setText("Settings");
        menuItem_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_settingsActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_settings);

        menuItem_exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menuItem_exit.setText("Exit");
        menuItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItem_exitActionPerformed(evt);
            }
        });
        menu_file.add(menuItem_exit);

        menuBar.add(menu_file);

        menu_view.setText("View");

        checkBoxMenuItem_developer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F12, 0));
        checkBoxMenuItem_developer.setText("Developer Console");
        checkBoxMenuItem_developer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBoxMenuItem_developerActionPerformed(evt);
            }
        });
        menu_view.add(checkBoxMenuItem_developer);

        menuBar.add(menu_view);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_main, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panel_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Generates a simple internal and temporary JTextPane with item information
     * and attempts to print it
     * 
     * @param evt 
     */
    private void button_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_printActionPerformed
        itemCTM.clearTable(true);

        if(itemCTM.getItemsKept().isEmpty()) {
            System.out.println("Nothing selected to print");
            return;
        }

        JTextPane printPane = new JTextPane();
        printPane.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
        printPane.setForeground(Color.black);
        printPane.setBackground(Color.white);

        printPane.setText(processor.getPrintableSheet(itemCTM.getItemsKept()));

        try {
            if(printPane.print())
                System.out.println("File printed successfully");
        } catch (PrinterException ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_button_printActionPerformed

    /**
     * Adds a File to the planogram list model and parses it
     * 
     * @param f The File to add/parse
     */
    private void addFileToPlanogramList(File f) {
        System.out.println("Attempting to parse " + f.getAbsolutePath());
        processor.startParsing(f, () -> {
            button_searchActionPerformed(null);
        });
        
        planogramCTM.addPlanogram(f);
        textField_input.selectAll();
        textField_input.requestFocus();
    }
    
    /**
     * Opens a file chooser window allowing for selection of pdf files
     * 
     * @param evt 
     */
    private void button_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_uploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "PDF Files", "pdf");
        fileChooser.setFileFilter(filter);
        int returnVal = fileChooser.showOpenDialog(null);

        File f = null;
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
            addFileToPlanogramList(f);
        }
    }//GEN-LAST:event_button_uploadActionPerformed

    private void button_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_clearActionPerformed
        itemCTM.clearTable(false);
    }//GEN-LAST:event_button_clearActionPerformed

    private void textField_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField_inputActionPerformed
        button_searchActionPerformed(null);
        textField_input.selectAll();
    }//GEN-LAST:event_textField_inputActionPerformed

    /**
     * Queries the backend processor for results and updates the view accordingly
     * 
     * @param evt 
     */
    private void button_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_searchActionPerformed
        int selectionIndex = comboBox_searchType.getSelectedIndex();
        String selection = comboBox_searchType.getItemAt(selectionIndex);

        Processor.SearchType type = null;

        if(selection.equals("UPC"))         type = Processor.SearchType.UPC;
        else if (selection.equals("SKU"))   type = Processor.SearchType.SKU;
        else if (selection.equals("WORD"))  type = Processor.SearchType.WORD;

        String digits = textField_input.getText();
        Item[] itemsFound = processor.search(digits, type);
        
        if(itemsFound == null) return;

        //clear the table, keeping selected items
        itemCTM.clearTable(true);

        //add all items found to the table
        for(Item i : itemsFound)
            itemCTM.addItem(i);

        //update the table (force redraw)
        itemCTM.updateTable();

        textField_input.selectAll();
        textField_input.requestFocus();
    }//GEN-LAST:event_button_searchActionPerformed

    private void menuItem_uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_uploadActionPerformed
        button_uploadActionPerformed(null);
    }//GEN-LAST:event_menuItem_uploadActionPerformed

    /**
     * Exits the program cleanly
     * 
     * @param evt 
     */
    private void menuItem_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_exitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItem_exitActionPerformed

    /**
     * Opens the about window
     * 
     * @param evt 
     */
    private void menuItem_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_aboutActionPerformed
        StringBuilder sb = new StringBuilder();
        sb.append(Launcher.APP_ARTIFACTID);
        sb.append(" v");
        sb.append(Launcher.APP_VERSION);
        sb.append("\n");
        sb.append("Copyright © 2022 Kieran Skvortsov\n\n");
        sb.append("Developed with \u2665 for lost and\n");
        sb.append("confused KinneyDrugs® employees");
        
        JOptionPane.showOptionDialog(this, sb.toString(), "About", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, 
                null, new Object[]{}, null);
    }//GEN-LAST:event_menuItem_aboutActionPerformed

    /**
     * Opens the default browser and navigates to this GitHub repository
     * 
     * @param evt 
     */
    private void menuItem_githubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_githubActionPerformed
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/ShermanZero/PlanogramHelper"));
        } catch (IOException | URISyntaxException ex) {
            System.err.println(ex);
        }
    }//GEN-LAST:event_menuItem_githubActionPerformed

    /**
     * Shows/hides the developer console and resets the view (via packing)
     * 
     * @param evt 
     */
    private void checkBoxMenuItem_developerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxMenuItem_developerActionPerformed
        panel_developer.setVisible(!panel_developer.isVisible());
        this.pack();
    }//GEN-LAST:event_checkBoxMenuItem_developerActionPerformed

    /**
     * Opens the settings UI
     * 
     * @param evt 
     */
    private void menuItem_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_settingsActionPerformed
        JFrame parentWindow = this;
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                if(settings != null) {
                    settings.dispose();
                }
                
                settings = new Settings(parentWindow);
                settings.setVisible(true);
            }
        });
    }//GEN-LAST:event_menuItem_settingsActionPerformed

    /**
     * Clears/resets the view model and backend-data, and re-processes all defined
     * planograms
     * 
     * @param evt 
     */
    private void menuItem_reuploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_reuploadActionPerformed
        resetUI();
        
        //TODO: Fix breaking changes
        String planogramString = null;
        if(planogramString == null || planogramString.isEmpty()) return;
        
        String[] planograms = planogramString.split(",");
        
        File f = null;
        for(String p : planograms) {
            f = new File(p);
            
            if(f.exists())
                addFileToPlanogramList(f);
        }
    }//GEN-LAST:event_menuItem_reuploadActionPerformed

    private void button_publishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_publishActionPerformed
        processor.uploadItemsToMongoDB();
    }//GEN-LAST:event_button_publishActionPerformed

    private void menuItem_pullFromDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItem_pullFromDatabaseActionPerformed
        resetUI();
        
        processor.pullFromMongoDB().forEach(i -> {
            itemCTM.addItem(i);
        });
        itemCTM.updateTable();
        
        planogramCTM.addPlanogram("Master Database");
        planogramCTM.updateTable();
    }//GEN-LAST:event_menuItem_pullFromDatabaseActionPerformed

    private void button_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_resetActionPerformed
        processor.resetMongoDB();
    }//GEN-LAST:event_button_resetActionPerformed

    
    private void resetUI() {
        processor.reset();
        itemCTM.clearTable(false);
        planogramCTM.setRowCount(0);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_clear;
    private javax.swing.JButton button_print;
    private javax.swing.JButton button_publish;
    private javax.swing.JButton button_reset;
    private javax.swing.JButton button_search;
    private javax.swing.JButton button_upload;
    private javax.swing.JCheckBoxMenuItem checkBoxMenuItem_developer;
    private javax.swing.JComboBox<String> comboBox_searchType;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItem_about;
    private javax.swing.JMenuItem menuItem_checkForUpdates;
    private javax.swing.JMenuItem menuItem_exit;
    private javax.swing.JMenuItem menuItem_github;
    private javax.swing.JMenuItem menuItem_print;
    private javax.swing.JMenuItem menuItem_pullFromDatabase;
    private javax.swing.JMenuItem menuItem_reupload;
    private javax.swing.JMenuItem menuItem_settings;
    private javax.swing.JMenuItem menuItem_upload;
    private javax.swing.JMenu menu_file;
    private javax.swing.JMenu menu_view;
    private javax.swing.JPanel panelGroupBottom;
    private javax.swing.JPanel panel_developer;
    private javax.swing.JPanel panel_main;
    private static javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane scrollPaneItemTable;
    private javax.swing.JScrollPane scrollPane_table_planograms;
    private javax.swing.JScrollPane scrollPane_textArea_output;
    private javax.swing.JPopupMenu.Separator separator01;
    private javax.swing.JPopupMenu.Separator separator02;
    private javax.swing.JTable table_items;
    private javax.swing.JTable table_planograms;
    private javax.swing.JTextArea textArea_output;
    private javax.swing.JTextField textField_input;
    // End of variables declaration//GEN-END:variables
}
