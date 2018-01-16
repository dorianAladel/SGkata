package com.sgbank.ui;

import com.sgbank.bl.manager.AccountManager;
import com.sgbank.da.pojo.AccountPojo;
import com.sgbank.da.pojo.OperationPojo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DemoLauncher {

    Scanner reader;

    public void launchDemo() {

        /* Login menu */
        loginMenu();
    }

    /**
     * Menu allowing user to connect
     * @return user login
     * */
    private String loginMenu() {
        System.out.println("------------------------------------------------");
        System.out.println("            Application bancaire.");
        System.out.println("------------------------------------------------");
        System.out.println("Login : ");
        reader = new Scanner(System.in);
        String login = reader.nextLine();
        System.out.println("Bienvenue "+login+" ! ");

        selectAccountsMenu(login);

        return login;
    }

    /**
     * If user has several accounts, he can choose one
     * @param login
     * */
    private void selectAccountsMenu(String login) {
        int bankAccountNb = AccountManager.countAccounts(login);
        if (bankAccountNb == 0)
        {
            System.out.println("Vous ne possédez aucun compte bancaire.");
            loginMenu();
        }
        else {
            ArrayList<AccountPojo> accountPojos = AccountManager.selectAccounts(login);
            if (bankAccountNb == 1)
            {
                System.out.println("Vous possédez "+AccountManager.countAccounts(login)+" compte bancaire");
                System.out.println("Compte n°"+accountPojos.get(0).getIdentifier());
                mainAccountMenu(accountPojos.get(0).getIdentifier());
            }
            else {
                System.out.println("Vous possédez "+AccountManager.countAccounts(login)+" comptes bancaires");
                System.out.println("Veuillez sélectionner un compte : ");
                for (int a = 0; a < accountPojos.size(); a++)
                {
                    System.out.println("Compte n°"+accountPojos.get(a).getIdentifier());
                }
                /*We get the bank account identifier from user input*/
                reader = new Scanner(System.in);
                try {
                    long account_identifier = reader.nextLong();

                    boolean rightAccountId = false;
                    for (int a = 0; a < accountPojos.size(); a++)
                    {
                        if (accountPojos.get(a).getIdentifier() == account_identifier)
                            rightAccountId = true;
                    }

                    /*We redirect to Account menu*/
                    if (rightAccountId)
                        mainAccountMenu(account_identifier);
                    else
                    {
                        System.out.println("Vous avez saisi un mauvais numéro de compte");
                        selectAccountsMenu(login);
                    }
                } catch (InputMismatchException ime) {
                    System.out.println("Erreur : vous devez entrer le numero de votre compte");
                    selectAccountsMenu(login);
                }
            }
        }
    }

    /**
     * Menu allowing user to do some operations as deposit, withdrawal, historic
     * @param account_identifier
     * */
    private void mainAccountMenu(long account_identifier) {
        AccountPojo accountPojo = AccountManager.selectAccountById(account_identifier);

        System.out.println("Le solde de votre compte est de : "+accountPojo.getBalance());
        System.out.println("Découvert autorisé : "+accountPojo.getAuthorizedOverdraft());

        System.out.println("\n Que voulez-vous faire ?");
        System.out.println("1 - Faire un dépôt");
        System.out.println("2 - Faire un retrait");
        System.out.println("3 - Consulter historique opérations");
        System.out.println("4 - Imprimer relevé");
        System.out.println("5 - Annuler");

        reader = new Scanner(System.in);
        int n = reader.nextInt();

        switch (n)
        {
            case 1 :
                depositMenu(account_identifier);
                break;

            case 2 :
                withDrawalMenu(account_identifier);
                break;

            case 3 :
                historicMenu(account_identifier);
                break;

            case 4 :
                generatePdfMenu(account_identifier);
                break;

            case 5 :
                selectAccountsMenu(accountPojo.getFk_user_login());
                break;

            default:
                System.out.println("Mauvaise sélection, veuillez entrer un autre choix");
                mainAccountMenu(account_identifier);
        }
    }

    /**
     * Deposit menu
     * @param account_identifier
     * */
    private void depositMenu(long account_identifier)
    {
        System.out.println("-------------------DEPOT-------------------");
        System.out.println("Pour Annuler et revenir au menu principal taper 0");
        System.out.println("Sinon, entrez le montant de votre dépôt ? ");
        reader = new Scanner(System.in);
        double amountChoice = reader.nextDouble();

        if (amountChoice == 0)
        {
            mainAccountMenu(account_identifier);
        }
        else {

            System.out.println("Entrez un intitulé ? ");
            reader = new Scanner(System.in);
            String entitled = reader.nextLine();

            AccountManager.doDeposit(amountChoice, entitled, account_identifier);
            mainAccountMenu(account_identifier);
        }
    }

    /**
     * Withdrawal menu
     * @param account_identifier
     * */
    private void withDrawalMenu(long account_identifier)
    {
        System.out.println("-------------------Retrait-------------------");
        System.out.println("Pour Annuler et revenir au menu principal taper 0");
        System.out.println("Sinon, entrez le montant de votre retrait ? ");
        reader = new Scanner(System.in);
        double amountChoice = reader.nextDouble();

        if (amountChoice == 0)
        {
            mainAccountMenu(account_identifier);
        }
        else
        {
            System.out.println("Entrez un intitulé ? ");
            reader = new Scanner(System.in);
            String entitled = reader.nextLine();

            AccountManager.doWithDrawal(amountChoice, entitled, account_identifier);
            mainAccountMenu(account_identifier);
        }
    }

    /**
     * Historic menu
     * @param account_identifier
     * */
    private void historicMenu(long account_identifier) {
        System.out.println("-------------------Historique-------------------");

        AccountPojo accountPojo = AccountManager.selectAccountById(account_identifier);

        ArrayList<OperationPojo> operationPojos = AccountManager.seeHistoric(accountPojo.getIdentifier());

        for (int a = 0; a < operationPojos.size(); a ++)
        {
            System.out.println(operationPojos.get(a).toString());
        }
        mainAccountMenu(account_identifier);
    }

    /**
     * Generate a PDF in order to print it later
     * @param account_identifer
     * */
    private void generatePdfMenu(long account_identifer)
    {
        AccountManager.generateHistoricPdf(account_identifer);
        mainAccountMenu(account_identifer);
    }
}