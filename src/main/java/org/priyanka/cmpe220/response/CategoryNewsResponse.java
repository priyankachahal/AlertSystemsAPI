package org.priyanka.cmpe220.response;

import org.priyanka.cmpe220.dataobj.NewsDo;

import java.util.List;

public class CategoryNewsResponse {


    public List<NewsDo> getNews() {
        return news;
    }

    public void setNews(List<NewsDo> news) {
        this.news = news;
    }

    private List<NewsDo> news;

}
