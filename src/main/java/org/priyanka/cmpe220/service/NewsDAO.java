package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.*;
import org.priyanka.cmpe220.dataobj.NewsDo;
import org.priyanka.cmpe220.dataobj.UserProfileDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.priyanka.cmpe220.exceptions.UnsupportedHexFormatException;

import java.util.*;

public class NewsDAO extends BasicDAO<NewsDo, String> {

    static final long ONE_MINUTE_IN_MILLIS = 60000; //millisecs

    public NewsDAO(Class<NewsDo> entityClass, MongoClient mongoClient, Morphia morphia, String dbName) {
        super(entityClass, mongoClient, morphia, dbName);
    }

    public NewsDo getNewsById(String newsId) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        ObjectId objectId = new ObjectId(newsId);
        NewsDo newsDo = getDatastore().get(NewsDo.class, objectId);
        return newsDo;
    }

    public Key<NewsDo> saveNews(NewsDo newsDo) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        newsDo.setCreationDate(new Date(t));
        return save(newsDo);
    }

    public String updateNewsById(String newsId, String description) throws DataSourceException, UnsupportedHexFormatException {
        try {
            ObjectId objectId = new ObjectId(newsId);
            if (getDatastore() == null) {
                throw new DataSourceException();
            }
            NewsDo existingNewsDo = getDatastore().get(NewsDo.class, objectId);
            if (existingNewsDo == null) {
                return null;
            } else {
                Calendar date = Calendar.getInstance();
                long t = date.getTimeInMillis();
                Query<NewsDo> updateQuery = getDatastore().createQuery(NewsDo.class).field("_id").equal(objectId);
                UpdateOperations<NewsDo> ops = getDatastore().createUpdateOperations(NewsDo.class)
                        .set("description", description)
                        .set("creationDate", new Date(t));
                UpdateResults updateResults = getDatastore().update(updateQuery, ops);
                if (updateResults.getUpdatedCount() > 0) {
                    return String.valueOf(existingNewsDo.getId());
                }
                throw new DataSourceException();
            }
        } catch (IllegalArgumentException exception) {
            throw new UnsupportedHexFormatException();
        }
    }

    public List<NewsDo> getNewsByCategory(String categoryName, Integer filterTime, String sort,
                                          int start, int limit) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<NewsDo> query = getDatastore().createQuery(NewsDo.class);
        if (categoryName != null) {
            query.and(query.criteria("category").equal(categoryName));
        }
        // filter by last minutes
        if (filterTime != null) {
            Calendar date = Calendar.getInstance();
            long t = date.getTimeInMillis();
            Date filteredDate = new Date(t - (filterTime * ONE_MINUTE_IN_MILLIS));
            query.and(query.criteria("creationDate").greaterThan(filteredDate));
        }

        // sort by creation date
        if (sort != null) {
            query.order(sort);
        }

        FindOptions findOptions = new FindOptions();
        findOptions.skip(start);
        findOptions.limit(limit);
        MorphiaIterator<NewsDo, NewsDo> morphiaIterator = super.find(query).fetch(findOptions);
        Iterator<NewsDo> newsDoIterator = morphiaIterator.iterator();
        List<NewsDo> newsDoList = new ArrayList<>();
        while (newsDoIterator.hasNext()) {
            newsDoList.add(newsDoIterator.next());
        }
        return newsDoList;
    }
}
