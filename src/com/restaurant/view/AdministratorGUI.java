package com.restaurant.view;

import com.restaurant.bll.BaseProduct;
import com.restaurant.bll.CompositeProduct;
import com.restaurant.bll.MenuItem;
import com.restaurant.bll.Restaurant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class AdministratorGUI extends JFrame {
    private JPanel administrator;
    private JTextField textField1;
    private JTextField textField3;
    private JButton createButton;
    private JButton createCompositeButton;
    private JButton deleteButton;
    private JTable table1;
    private JButton editCompositeButton;
    private JButton backButton;
    private MenuItemTableModel model;
    static int []selectedRows = null;

    public AdministratorGUI() {
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.administrator);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        model = new MenuItemTableModel(Restaurant.getInstance().getItems(), false, true);
        Restaurant.getInstance().addObserver(model);
        table1.setAutoCreateColumnsFromModel(true);
        table1.setModel(model);
        table1.getTableHeader().setReorderingAllowed(false);

        ListSelectionModel selectionModel = table1.getSelectionModel();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionModel.clearSelection();
                BaseProduct data = new BaseProduct(textField1.getText(), Double.parseDouble(textField3.getText()));
                Restaurant.getInstance().addItem(data);
            }
        });

        table1.addMouseListener(new MouseAdapter() {
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table1.getSelectedRows();
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(null, "You must select atleast an item!", "Error on Delete", JOptionPane.ERROR_MESSAGE);
                }

                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    model.removeItem(selectedRows[i]);
                }
                selectionModel.clearSelection();
            }
        });



        createCompositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompositeProduct product = new CompositeProduct(textField1.getText());
                selectedRows = table1.getSelectedRows();
                for (MenuItem item : model.getItems(selectedRows)) {
                    product.addProduct(item);
                }
                Restaurant.getInstance().addItem(product);
                selectionModel.clearSelection();
            }
        });
        editCompositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionModel.clearSelection();
                createButton.setEnabled(false);
                createCompositeButton.setEnabled(false);
                editCompositeButton.setEnabled(false);
                backButton.setEnabled(true);

                CompositeProduct compositeProduct = (CompositeProduct) model.getItem(table1.getSelectedRow());

                model = new MenuItemTableModel(compositeProduct.getProducts(), true, true);
                Restaurant.getInstance().addObserver(model);

                table1.setModel(model);

            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionModel.clearSelection();
                createButton.setEnabled(true);
                createCompositeButton.setEnabled(true);
                editCompositeButton.setEnabled(true);
                backButton.setEnabled(false);

                model = new MenuItemTableModel(Restaurant.getInstance().getItems(), false, true);
                Restaurant.getInstance().addObserver(model);

                table1.setModel(model);
            }
        });
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                int[] selectedRows;
                if(AdministratorGUI.selectedRows != null){
                    selectedRows = AdministratorGUI.selectedRows;
                }else{
                    selectedRows = table1.getSelectedRows();
                }

                System.out.println(selectedRows.length);
                switch (selectedRows.length){
                    case 0:
                        deleteButton.setEnabled(false);
                        createCompositeButton.setEnabled(false);
                        editCompositeButton.setEnabled(false);
                        break;
                    case 1:
                        deleteButton.setEnabled(true);
                        createCompositeButton.setEnabled(false);
                        editCompositeButton.setEnabled(model.getItem(selectedRows[0]) instanceof CompositeProduct);
                        break;
                    default:
                        deleteButton.setEnabled(true);
                        editCompositeButton.setEnabled(false);

                        int hasComposite = 0;
                        for(int selectedRow : selectedRows){
                            if(model.getItem(selectedRow) instanceof CompositeProduct){
                                hasComposite++;
                            }
                        }
                        createCompositeButton.setEnabled(true);
                        createCompositeButton.setText("Create Composite");
                        if(hasComposite == 1){
                            createCompositeButton.setText("Add to Composite");
                        }else if(hasComposite > 1){
                            createCompositeButton.setEnabled(false);
                        }

                }
            }
        });
    }

    public void showBug(JTable jt) {
        int[] rows = jt.getSelectedRows();
        System.out.println("These rows are selected:");
        for (int i : rows)
            System.out.print(i + " ");
        System.out.println();
        if (rows.length > 0)
            jt.getModel().getValueAt(rows[rows.length - 1], 0);

    }

    private void createUIComponents() {

    }
}
