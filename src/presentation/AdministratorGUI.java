package presentation;

import bll.BaseProduct;
import bll.CompositeProduct;
import bll.MenuItem;
import bll.Restaurant;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class AdministratorGUI extends JFrame implements Observer {
    private JPanel administrator;
    private JTextField textField1;
    private JTextField textField3;
    private JButton createButton;
    private JButton createCompositeButton;
    private JButton deleteButton;
    private JTable table;
    private JButton editCompositeButton;
    private JButton backButton;
    private JButton exportRestaurantDataButton;

    private ListSelectionModel selectionModel;
    private MenuItemTableModel model;
    static int[] selectedRows = null;

    public AdministratorGUI() {
        this.setTitle(this.getClass().getSimpleName());
        this.setContentPane(this.administrator);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        model = new MenuItemTableModel(Restaurant.getInstance().getItems(), true, null);
        table.setAutoCreateColumnsFromModel(true);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        selectionModel = table.getSelectionModel();

        addListeners();
    }

    private void addListeners() {
        addExportButtonListener();
        addBackButtonListener();
        addEditCompositeButtonListener();
        addCreateCompositeButtonListener();
        addDeleteCompositeButtonListener();
        addCreateButtonListener();
        addSelectListener();
    }

    private void addCreateButtonListener() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectionModel.clearSelection();
                String name = textField1.getText();
                if (name.length() < 1) {
                    JOptionPane.showMessageDialog(null, "Wrong Name", "Error in Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double price = 0D;
                try{
                    price = Double.parseDouble(textField3.getText());
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Wrong price", "Error in Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                BaseProduct data = new BaseProduct(name, price);
                Restaurant.getInstance().addItem(data);
            }
        });
    }

    private void addDeleteCompositeButtonListener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedRows.length == 0) {
                    JOptionPane.showMessageDialog(null, "You must select atleast an item!", "Error on Delete", JOptionPane.ERROR_MESSAGE);
                }

                int[] rowsToDelete = selectedRows;
                for (int i = rowsToDelete.length - 1; i >= 0; i--) {
                    model.removeItem(rowsToDelete[i]);
                }
            }
        });
    }

    private void addCreateCompositeButtonListener() {
        createCompositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() < 1) {
                    JOptionPane.showMessageDialog(null, "Wrong Name", "Error in Inputs", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int[] selectedProducts = selectedRows;
                CompositeProduct product = new CompositeProduct(textField1.getText());
                for (MenuItem item : model.getItems(selectedProducts)) {
                    product.addProduct(item);
                }
                Restaurant.getInstance().addItem(product);
            }
        });
    }

    private void addEditCompositeButtonListener() {
        editCompositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButton.setEnabled(false);
                createCompositeButton.setEnabled(false);
                editCompositeButton.setEnabled(false);
                backButton.setEnabled(true);

                CompositeProduct compositeProduct = (CompositeProduct) model.getItem(selectedRows[0]);

                model = new MenuItemTableModel(compositeProduct.getProducts(), true, compositeProduct);

                table.setModel(model);

            }
        });
    }

    private void addBackButtonListener() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createButton.setEnabled(true);
                createCompositeButton.setEnabled(true);
                editCompositeButton.setEnabled(true);
                backButton.setEnabled(false);

                model = new MenuItemTableModel(Restaurant.getInstance().getItems(), true, null);

                table.setModel(model);
            }
        });
    }

    private void addSelectListener() {
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                AdministratorGUI.selectedRows = table.getSelectedRows();

                switch (selectedRows.length) {
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

                        boolean hasComposite = false;
                        for (int selectedRow : selectedRows) {
                            if (selectedRow >= model.getRowCount()) {
                                break;
                            }
                            if (model.getItem(selectedRow) instanceof CompositeProduct) {
                                hasComposite = true;
                                break;
                            }
                        }
                        createCompositeButton.setEnabled(!hasComposite);

                }
            }
        });
    }

    private void addExportButtonListener() {
        exportRestaurantDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Restaurant.getInstance().serializeAndSet()) {
                    JOptionPane.showMessageDialog(null, "Restaurant exported with every data.", "Successfully exported", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "There has been an error while exporting restaurant data", "Error occurred", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        model.update(o, arg);
    }
}
