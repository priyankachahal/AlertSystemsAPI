package org.priyanka.cmpe220.response;

import org.priyanka.cmpe220.dataobj.NewsDo;

public class GetNewsResponse {

    private NewsDo news;

    public NewsDo getNews() {
        return news;
    }

    public void setNews(NewsDo news) {
        this.news = news;
    }

}
