package com.mybank.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JFrame extends javax.swing.JFrame {
    private JComboBox<String> comboBox1;
    private JButton showButton;
    private JButton reportButton;
    private JButton aboutButton;
    private JTextArea textArea;

    private HashMap<String, Client> clients = new HashMap<>();

    public JFrame() {
        // Ініціалізація компонентів
        comboBox1 = new JComboBox<>();
        showButton = new JButton("Show");
        aboutButton = new JButton("About");
        reportButton = new JButton("Report");
        textArea = new JTextArea();

        // Встановлення макету
        setLayout(new java.awt.BorderLayout());

        // Додавання компонентів до вікна
        JPanel topPanel = new JPanel();
        topPanel.add(comboBox1);
        topPanel.add(showButton);
        topPanel.add(aboutButton);
        topPanel.add(reportButton);
        add(topPanel, java.awt.BorderLayout.NORTH);
        add(new JScrollPane(textArea), java.awt.BorderLayout.CENTER);

        // Завантаження даних з файлу
        loadData();

        // Додавання обробників подій
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClientInfo();
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutDialog();
            }
        });

        // Налаштування вікна
        setTitle("Bank Client Info");
        setSize(600, 400);
        setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("D:\\TuiDemo\\src\\com\\mybank\\gui\\test.dat"))) {
            String line = br.readLine();
            int numClients = Integer.parseInt(line.trim());

            for (int i = 0; i < numClients; i++) {
                String clientInfo = br.readLine().trim();
                String[] clientData = clientInfo.split("\\s+"); // Розділити рядок за будь-якою кількістю пробілів

                if (clientData.length >= 3) { // Перевірка на кількість елементів у масиві
                    String firstName = clientData[0];
                    String lastName = clientData[1];
                    int numAccounts = Integer.parseInt(clientData[2]);

                    Client client = new Client(firstName, lastName);
                    for (int j = 0; j < numAccounts; j++) {
                        line = br.readLine().trim();
                        String[] accountData = line.split("\\s+"); // Розділити рядок за будь-якою кількістю пробілів

                        if (accountData.length >= 3) { // Перевірка на кількість елементів у масиві
                            String type = accountData[0];
                            double balance = Double.parseDouble(accountData[1]);
                            double additionalInfo = Double.parseDouble(accountData[2]);

                            if (type.equals("S")) {
                                client.addAccount(new SavingsAccount(balance, additionalInfo));
                            } else if (type.equals("C")) {
                                client.addAccount(new CheckingAccount(balance, additionalInfo));
                            }
                        }
                    }
                    clients.put(firstName + " " + lastName, client);
                    comboBox1.addItem(firstName + " " + lastName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error parsing data file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void showClientInfo() {
        String selectedClientName = (String) comboBox1.getSelectedItem();
        if (selectedClientName != null) {
            Client client = clients.get(selectedClientName);

            StringBuilder info = new StringBuilder();
            info.append("Client: ").append(selectedClientName).append("\n");
            for (Account account : client.getAccounts()) {
                info.append(account.toString()).append("\n");
            }

            textArea.setText(info.toString());
        }
    }

    private void generateReport() {
        StringBuilder report = new StringBuilder("Bank Clients Report:\n");
        for (String clientName : clients.keySet()) {
            Client client = clients.get(clientName);
            report.append("Client: ").append(clientName).append("\n");
            for (Account account : client.getAccounts()) {
                report.append("\t").append(account.toString()).append("\n");
            }
            report.append("\n");
        }
        textArea.setText(report.toString());
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Bank Client Info\nDeveloped by Odinochenko Anton", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new JFrame();
    }
}





