/*
AUTHOR: GRANT ROBERT SAYLOR
ASSIGNMENT: ASSIGNMENT 1 - PHONE BOOK
DATE: 7/2/2020
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class phoneBook extends JFrame {

    private DefaultTableModel model;
    private final JTabbedPane centerPane = new JTabbedPane();
    private final JTextField nameTxtFld = new JTextField(40);
    private final JLabel nameLbl = new JLabel("Name");
    private final JTextField surnameTxtFld = new JTextField(40);
    private final JLabel surnameLbl = new JLabel("   Surname");
    private final JTextField phoneTxtFld = new JTextField(40);
    private final JLabel phoneLbl = new JLabel("Phone");
    private final JPanel addPanel = new JPanel();
    private final JPanel listPanel = new JPanel();
    private final JPanel bottomPane = new JPanel();
    private final JButton addButton = new JButton("Add");
    private final JButton searchButton = new JButton("Search");
    private final JButton closeButton = new JButton("Close");
    private final JTable listTable = new JTable();
    private final JScrollPane scroll = new JScrollPane(listTable);
    private final JTextField textFilter = new JTextField(20);
    private final JPanel searchPanel = new JPanel(new BorderLayout());

    private void initGUIComponents(Container content) {

        //Title within the GUI
        JLabel topLabel = new JLabel("Phone Book Directory");
        //Adds the label to the top of the frame
        content.add(topLabel, BorderLayout.PAGE_START);

        //Center aligns the text
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Adds the pane to the frame
        content.add(centerPane, BorderLayout.CENTER);

        //Adding the text fields to the "Add" tab and adding them to the searchPanel
        addPanel.add(nameTxtFld);
        addPanel.add(nameLbl);
        addPanel.add(surnameTxtFld);
        addPanel.add(surnameLbl);
        addPanel.add(phoneTxtFld);
        addPanel.add(phoneLbl);

        //Tooltips for the tabs
        centerPane.addTab("Add", null, addPanel, "Adds a user to the directory");
        centerPane.addTab("List", null, listPanel, "Lists users in the directory");
        content.add(centerPane);

        //Creates a bottom pane for the buttons
        bottomPane.setSize(100, 50);
        content.add(bottomPane, BorderLayout.SOUTH);

        //Creates the add, search and close buttons
        addButton.setToolTipText("Add the user to the address book");
        searchButton.setToolTipText("Search for a user in the address book");
        closeButton.setToolTipText("Exit the program");

        //Position the panes
        bottomPane.add(addButton, BorderLayout.SOUTH);
        listPanel.add(searchButton, BorderLayout.SOUTH);
        bottomPane.add(closeButton, BorderLayout.EAST);

        //Create the JTable
        model = new DefaultTableModel();
        listTable.setModel(model);

        //Add the columns to the table
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Phone Number");

        //Add the user to the list when you invoke the add button
        addButton.addActionListener(evt -> {
            String name = nameTxtFld.getText().trim();
            String lastname = surnameTxtFld.getText().trim();
            String phone = phoneTxtFld.getText().trim();

            if (name.isEmpty() || lastname.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(addButton, "Please fill out all fields", "Failed Entry", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(addButton, "The Contact has been Added to the Address Book", "Successful Entry", JOptionPane.INFORMATION_MESSAGE);
                String[] st = {name, lastname, phone};
                model.addRow(st);
                listPanel.add(scroll);
            }
        });

        //Search for the user using TableRowSorter
        TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(listTable.getModel());
        listTable.setRowSorter(rowSorter);

        //Search Entry Panel
        searchPanel.add(new JLabel("Enter a first or last name or phone to search"), BorderLayout.WEST);
        searchPanel.add(textFilter, BorderLayout.CENTER);
        listPanel.add(searchPanel, BorderLayout.SOUTH);

        //If else for if the contact is found or not.
        searchButton.addActionListener(e -> {
            String text = (textFilter.getText());

            if (text.trim().length() != 0) {
                rowSorter.setRowFilter(RowFilter.regexFilter(text));

            }if(!rowSorter.equals(text.trim())){
                JOptionPane.showMessageDialog(addButton, "Contacts Have Been Sorted", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //Closes the program
        closeButton.addActionListener(evt -> System.exit(0));
    }

    //Builds the GUI of the main frame
    public void GUI() {
        JFrame frame = new JFrame("Phone Book");
        frame.setPreferredSize(new Dimension(500, 550));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGUIComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);
        //Sets the icon of the frame
        try {
            frame.setIconImage(ImageIO.read(new File("icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Main Method
    public static void main(String[] args) {
        phoneBook p = new phoneBook();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(p::GUI);
    }
}