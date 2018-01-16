package com.sgbank.bl.manager;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.sgbank.bl.PdfTools;
import com.sgbank.conf.Properties;
import com.sgbank.da.dao.AccountDao;
import com.sgbank.da.pojo.AccountPojo;
import com.sgbank.da.pojo.OperationPojo;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Dorian ALADEL
 * @since 09/01/2018
 */
public class AccountManager {

    /**
     * Get user bank account by id
     * @param identifier of account
     * @return account
     */
    public static AccountPojo selectAccountById(long identifier)
    {
        AccountDao accountDao= new AccountDao();
        AccountPojo accountPojo = accountDao.find(identifier);

        return accountPojo;
    }

    /**
     * Do a deposit
     * @param amount
     * @param entitled
     * @param account_identifier
     */
    public static void doDeposit(double amount, String entitled, long account_identifier)
    {
        /*We save a new record for operation */
        OperationPojo operationPojo = new OperationPojo();
        operationPojo.setType("depot");
        operationPojo.setEntitled(entitled);
        operationPojo.setAmount(amount);
        operationPojo.setFkAccountIdentifier(account_identifier);
        LocalDateTime now = LocalDateTime.now();
        operationPojo.setDate(now);

        OperationManager.addOperation(operationPojo);

        /*Then we modify the balance of the account */
        AccountPojo accountPojo = AccountManager.selectAccountById(account_identifier);
        accountPojo.setBalance(accountPojo.getBalance() + amount);
        updateAccount(accountPojo);
    }

    /**
     * Do a withdrawal
     * @param amount
     * @param entitled
     * @param account_identifier
     */
    public static void doWithDrawal(double amount, String entitled, long account_identifier)
    {
        AccountPojo accountPojo = AccountManager.selectAccountById(account_identifier);

        /*We check if there is enough money*/
        if ((accountPojo.getBalance() - amount) >= (- accountPojo.getAuthorizedOverdraft()))
        {
            /*We save a new record for operation */
            OperationPojo operationPojo = new OperationPojo();
            operationPojo.setType("retrait");
            operationPojo.setEntitled(entitled);
            operationPojo.setAmount(amount);
            operationPojo.setFkAccountIdentifier(account_identifier);
            LocalDateTime now = LocalDateTime.now();
            operationPojo.setDate(now);

            OperationManager.addOperation(operationPojo);

            /*Then we modify the balance of the account */
            accountPojo.setBalance(accountPojo.getBalance()- amount);
            AccountManager.updateAccount(accountPojo);
        }
        else {
            System.out.println("Vous ne possédez pas suffisament de provision pour effectuer cette opération");
        }
    }

    /**
     * See the historic
     * @param identifier of the bank account
     * @return operation list
     */
    public static ArrayList<OperationPojo> seeHistoric(long identifier)
    {
       ArrayList<OperationPojo> operationPojos = new ArrayList<OperationPojo>();
       operationPojos = OperationManager.selectOperations(identifier);

       return operationPojos;
    }

    /**
     * Generate a pdf containing operation historic in order to print it after
     * @param identifier of the bank account
     */
    public static void generateHistoricPdf(long identifier)
    {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(Properties.PDF_DIRECTORY + "/Historic.pdf"));
            document.open();
            PdfTools pdfTools = new PdfTools(document, identifier);
            System.out.println("Pdf généré ! Vous pouvez maintenant l'ouvrir et l'imprimer");
        } catch (Exception e) {
            System.out.println("Erreur dans la génération du PDF");
            e.printStackTrace();
        }
    }


    /**
     * Account update
     * @param accountPojo
     */
    public static void updateAccount(AccountPojo accountPojo)
    {
        AccountDao accountDao= new AccountDao();
        accountDao.update(accountPojo);
    }

    /**
     * Get user bank accounts
     * @param login of user
     * @return List of user accounts
     */
    public static ArrayList<AccountPojo> selectAccounts(String login)
    {
        AccountDao accountDao= new AccountDao();
        ArrayList<AccountPojo> accountPojos;

        accountPojos = accountDao.selectAccounts(login);

        return accountPojos;
    }

    /**
     * Count bank accounts
     * @param login, from user
     * @return account number
     * */
    public static int countAccounts(String login)
    {
        int count_found_accounts=0;
        AccountDao accountDao = new AccountDao();
        count_found_accounts = accountDao.countAccounts(login);
        return count_found_accounts;
    }
}
