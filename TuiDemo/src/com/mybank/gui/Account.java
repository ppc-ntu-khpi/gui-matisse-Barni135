package com.mybank.gui;

abstract class Account {
    protected double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - Balance: " + balance;
    }
}

