package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.priyanka.cmpe220.dataobj.UserProfileDo;
import org.priyanka.cmpe220.exceptions.AlreadyRegisteredUserException;
import org.priyanka.cmpe220.exceptions.DataSourceException;

public class UserAuthenticationDAO extends BasicDAO<UserProfileDo, String> {

    public UserAuthenticationDAO(Class<UserProfileDo> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    public Key<UserProfileDo> saveUserProfile(UserProfileDo userProfileDo) throws DataSourceException, AlreadyRegisteredUserException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<UserProfileDo> query = getDatastore().createQuery(UserProfileDo.class);
        query.and(query.criteria("email").equal(userProfileDo.getEmail()));
        QueryResults queryResults = super.find(query);
        if (queryResults != null && queryResults.count() > 0) {
            throw new AlreadyRegisteredUserException();
        }
        return save(userProfileDo);
    }

    public boolean isUserAuthenticated(String email, String password) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<UserProfileDo> query = getDatastore().createQuery(UserProfileDo.class);
        query.and(query.criteria("email").equal(email));
        query.and(query.criteria("password").equal(password));
        QueryResults queryResults = super.find(query);
        if (queryResults != null && queryResults.count() > 0) {
            return true;
        }
        return false;
    }

}