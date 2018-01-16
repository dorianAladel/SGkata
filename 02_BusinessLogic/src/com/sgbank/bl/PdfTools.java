package com.sgbank.bl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.sgbank.bl.manager.AccountManager;
import com.sgbank.bl.manager.OperationManager;
import com.sgbank.bl.manager.UserManager;
import com.sgbank.da.pojo.AccountPojo;
import com.sgbank.da.pojo.OperationPojo;
import com.sgbank.da.pojo.UserPojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Dorian ALADEL
 * @since 10/01/2018
 */
public class PdfTools {

    public PdfTools() {
    }

    public PdfTools(Document document, long account_identifier)
    {
        addMetaData(document);
        try {
            addTitlePage(document, account_identifier);
            addContent(document, account_identifier);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    /**
     * iText allows to add metadata to the PDF which can be viewed in your Adobe
     * @param document
     */
    private static void addMetaData(Document document) {
        document.addTitle("Historique");
        document.addSubject("Reporting des opérations");
        document.addAuthor("Société Générale");
        document.addCreator("Société Générale");
    }

    /**
     * Add a title to the Pdf
     * @param document
     * @param account_identifier
     * @throws DocumentException
     */
    private static void addTitlePage(Document document, long account_identifier) throws DocumentException {

        AccountPojo accountPojo = AccountManager.selectAccountById(account_identifier);
        UserPojo userPojo = UserManager.FindUserByLogin(accountPojo.getFk_user_login());

        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);

        /*Set up the title */
        preface.add(new Paragraph("Historique des opérations du compte : " + account_identifier, catFont));
        preface.add(new Paragraph("Propriétaire du compte : " + userPojo.getLastName() + " "+userPojo.getFirstName(), catFont));

        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Rapport généré  " + formatterDate(LocalDateTime.now()), smallBold));

        addEmptyLine(preface, 2);

        document.add(preface);
    }

    /**
     * Create and add operation  to the pdf
     * @param document
     * @param account_identifier
     * @throws DocumentException
     * */
    private static void addContent(Document document, long account_identifier) throws DocumentException {

        ArrayList<OperationPojo> operationPojos = OperationManager.selectOperations(account_identifier);

        Paragraph subPara = new Paragraph("");

        /* Operation table*/
        createTable(subPara, operationPojos);

        /* add operation table to the document*/
        document.add(subPara);
    }

    /**
     * Create operation table
     * @param paragraph
     * @param operationPojos
     * */
    private static void createTable(Paragraph paragraph, ArrayList<OperationPojo> operationPojos) {

        /*We create a table of 4 columns*/
        PdfPTable table = new PdfPTable(4);

        /*We create header columns*/
        PdfPCell c1 = new PdfPCell(new Phrase("Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Intitulé"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Montant"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        /*We add entries to table*/
        for (int a = 0; a < operationPojos.size(); a ++)
        {
            table.addCell(String.valueOf(operationPojos.get(a).getType()));
            table.addCell(String.valueOf(formatterDate(operationPojos.get(a).getDate())));
            table.addCell(String.valueOf(operationPojos.get(a).getEntitled()));
            table.addCell(String.valueOf(operationPojos.get(a).getAmount()));
        }
        paragraph.add(table);
    }

    /**
     * Format date
     * @param localDateTime the date to format
     * @return a formatted date
     * */
    private static String formatterDate(LocalDateTime localDateTime) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format3 = new SimpleDateFormat("hh:mm");
        String newDate = "";
        try {
            Date dateNew = format1.parse(localDateTime.toString());
            String formatedDate = format2.format(dateNew);
            String formatedTime = format3.format(dateNew);

            newDate = "Le " + formatedDate + " à " + formatedTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /**
     * Skip a line
     * @param paragraph
     * @param number of lines to skip
     * */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
