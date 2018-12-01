package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.priyanka.cmpe220.dataobj.UserProfileDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;

public class UserAuthenticationDAO extends BasicDAO<UserProfileDo, String> {

    public UserAuthenticationDAO(Class<UserProfileDo> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    public String saveUserProfile(UserProfileDo userProfileDo) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<UserProfileDo> query = getDatastore().createQuery(UserProfileDo.class);
        query.and(query.criteria("email").equal(userProfileDo.getEmail().trim()));
        QueryResults queryResults = super.find(query);
        if (queryResults != null && queryResults.count() > 0) {
            UserProfileDo existingUserProfileDo = (UserProfileDo) queryResults.get();
            Query<UserProfileDo> updateQuery = getDatastore().createQuery(UserProfileDo.class).field("email").equal(userProfileDo.getEmail().trim());
            UpdateOperations<UserProfileDo> ops = getDatastore().createUpdateOperations(UserProfileDo.class)
                    .set("city", userProfileDo.getCity())
                    .set("name", userProfileDo.getName())
                    .set("password", userProfileDo.getPassword())
                    .set("phone", userProfileDo.getPhone())
                    .set("street", userProfileDo.getStreet())
                    .set("zipcode", userProfileDo.getZipcode());
            UpdateResults updateResults = getDatastore().update(updateQuery, ops);
            if (updateResults.getUpdatedCount() > 0) {
                return String.valueOf(existingUserProfileDo.getId());
            }
            throw new DataSourceException();
        } else {
            Key<UserProfileDo> userProfileDoKey = save(userProfileDo);
            return String.valueOf(userProfileDoKey.getId());
        }
    }

    public UserProfileDo getAuthenticatedUser(String email, String password) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<UserProfileDo> query = getDatastore().createQuery(UserProfileDo.class);
        query.and(query.criteria("email").equal(email));
        query.and(query.criteria("password").equal(password));
        QueryResults queryResults = super.find(query);
        if (queryResults != null && queryResults.count() > 0) {
            return (UserProfileDo) queryResults.get();
        }
        return null;
    }

}