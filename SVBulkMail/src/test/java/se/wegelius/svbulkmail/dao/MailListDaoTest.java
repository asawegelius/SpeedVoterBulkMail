/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.svbulkmail.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import se.wegelius.svbulkmail.model.Mail;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author asawe
 */
public class MailListDaoTest {

    public MailListDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
        MailListDao dao = new MailListDao();
        Set<Mail> testMails = dao.getAll();
        for (Mail b : testMails) {
            if (b.getEmail().equals("asa.wegelius@gamil.com")) {
                dao.delete(b);
            }
        }
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     *
     */
    @Test
    public void testAddMailList() {
        MailListDao dao = new MailListDao();
        // create a test branch...
        int sum = dao.count();
        Mail mail = new Mail();
        mail.setEmail("asa.wegelius@gamil.com");
        mail.setMessage("test mail");
        dao.saveOrUpdate(mail);
        int newSum = dao.count();
        System.out.println("newSum = " + newSum + " sum = " + sum);
        assertTrue(sum == (newSum - 1));
    }

    /**
     *
     */
    @Test
    public void testGetAll() {
        MailListDao dao = new MailListDao();
        int sum = dao.count();
        Set<Mail> testList = dao.getAll();
        assertTrue(testList.size() == sum);
    }

    /**
     *
     */
    @Test
    public void testFindById() {
        MailListDao dao = new MailListDao();
        // get a random randMail from all mails
        List<Mail> list = new ArrayList<>(dao.getAll());
        int size = list.size();
        int item = new Random().nextInt(size);
        Mail randMail = list.get(item);
        Mail testFind = dao.findByID(randMail.getEmailsId());
        assertTrue(testFind.getEmail().equals(randMail.getEmail()));
        assertTrue(testFind.getMessage().equals(randMail.getMessage()));

    }


}
