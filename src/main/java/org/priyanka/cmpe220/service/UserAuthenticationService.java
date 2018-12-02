package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.priyanka.cmpe220.dataobj.UserProfileDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.priyanka.cmpe220.service.Constants.DB_HOST;
import static org.priyanka.cmpe220.service.Constants.DB_NAME;
import static org.priyanka.cmpe220.service.Constants.MORPHIA_PACKAGE;

@Component("UserAuthenticationService")
public class UserAuthenticationService {

    private UserAuthenticationDAO userAuthenticationDAO;

    @PostConstruct
    public void postConstruct() {
        String dbName = new String(DB_NAME);
        MongoClient mongo = new MongoClient(DB_HOST);
        Morphia morphia = new Morphia();
        morphia.createDatastore(mongo, dbName);
        morphia.mapPackage(MORPHIA_PACKAGE);
        userAuthenticationDAO = new UserAuthenticationDAO(UserProfileDo.class, mongo, morphia, dbName);
    }

    public String save(UserProfileDo userProfileDo) throws DataSourceException {
        return userAuthenticationDAO.saveUserProfile(userProfileDo);
    }

    public UserProfileDo getAuthenticatedUser(String email, String password) throws DataSourceException {
        return userAuthenticationDAO.getAuthenticatedUser(email, password);
    }

    public List<UserProfileDo> getUsers(int start, int limit) throws DataSourceException {
        return userAuthenticationDAO.getUsers(start, limit);
    }

}