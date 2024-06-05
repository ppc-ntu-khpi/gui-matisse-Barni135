package com.mybank.gui;

import java.util.ArrayList;

class Client {
    private String firstName;
    private String lastName;
    private ArrayList<Account> accounts;

    public Client(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
}
