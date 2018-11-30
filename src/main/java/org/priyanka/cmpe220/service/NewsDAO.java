package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.MorphiaIterator;
import org.mongodb.morphia.query.Query;
import org.priyanka.cmpe220.dataobj.NewsDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.priyanka.cmpe220.exceptions.UnsupportedHexFormatException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NewsDAO extends BasicDAO<NewsDo, String> {

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
        return save(newsDo);
    }

    public boolean deleteNewsById(String newsId) throws DataSourceException, UnsupportedHexFormatException {
        try {
            ObjectId objectId = new ObjectId(newsId);
            if (getDatastore() == null) {
                throw new DataSourceException();
            }
            NewsDo newsDo = getDatastore().get(NewsDo.class, objectId);
            if (newsDo == null) {
                return false;
            } else {
                getDatastore().delete(NewsDo.class, objectId);
            }
            return true;
        } catch (IllegalArgumentException exception) {
            throw new UnsupportedHexFormatException();
        }
    }

    public List<NewsDo> getNewsByCategory(String categoryName, int start, int limit) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<NewsDo> query = getDatastore().createQuery(NewsDo.class);
        query.and(query.criteria("category").equal(categoryName));
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

/*
public List<NewsDo> getNewsByPincode(String pincodeNo, int start, int limit) throws DataSourceException {
        if (getDatastore() == null) {
            throw new DataSourceException();
        }
        Query<NewsDo> query = getDatastore().createQuery(NewsDo.class);
        query.and(query.criteria("pincode").equal(pincodeNo));
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
 */
}
