package service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dataaccess.GameDAO;
import dataaccess.SQLAuthDAO;
import dataaccess.UserDAO;

public class SQLServiceTests {
    private SQLAuthDAO auto = new SQLAuthDAO();
    @Test
    public void clearTest() throws Exception{

        String auth1=auto.createAuth("username");
        String auth2=auto.createAuth("username2");
        auto.clearAuth();
        //now check
        Assertions.assertNull(auto.getAuth(auth1));
        Assertions.assertNull(auto.getAuth(auth2));

    }
}
