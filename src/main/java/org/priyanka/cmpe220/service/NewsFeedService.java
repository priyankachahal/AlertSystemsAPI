package org.priyanka.cmpe220.service;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.priyanka.cmpe220.dataobj.NewsDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.priyanka.cmpe220.exceptions.UnsupportedHexFormatException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.priyanka.cmpe220.service.Constants.DB_HOST;
import static org.priyanka.cmpe220.service.Constants.DB_NAME;
import static org.priyanka.cmpe220.service.Constants.MORPHIA_PACKAGE;

@Component("newsFeedService")
public class NewsFeedService {

    private NewsDAO newsDAO;

    @PostConstruct
    public void postConstruct() {
        String dbName = new String(DB_NAME);
        MongoClient mongo = new MongoClient(DB_HOST);
        Morphia morphia = new Morphia();
        morphia.createDatastore(mongo, dbName);
        morphia.mapPackage(MORPHIA_PACKAGE);
        newsDAO = new NewsDAO(NewsDo.class, mongo, morphia, dbName);
    }

    public NewsDo getNewsById(String newsId) throws DataSourceException {
        return newsDAO.getNewsById(newsId);
    }


    public String save(NewsDo newsDo) throws DataSourceException {
        Key<NewsDo> newsDoKey = newsDAO.saveNews(newsDo);
        return String.valueOf(newsDoKey.getId());
    }

    public List<NewsDo> getNewsbyCategory(String categoryName, int start, int limit) throws DataSourceException {
        return newsDAO.getNewsByCategory(categoryName, start, limit);
    }

    public boolean deleteNewsById(String newsId) throws DataSourceException, UnsupportedHexFormatException {
        return newsDAO.deleteNewsById(newsId);
    }

    /*
     public List<NewsDo> getNewsbyPincode(String pincodeNo, int start, int limit) throws DataSourceException {
         return newsDAO.getNewsByPincode(pincodeNo, start, limit);
     }
 */

}