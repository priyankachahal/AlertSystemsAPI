package org.priyanka.cmpe220;

import org.priyaka.cmpe220.service.NewsFeedService;
import org.priyanka.cmpe220.dataobj.NewsDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.priyanka.cmpe220.exceptions.InvalidNewsException;
import org.priyanka.cmpe220.exceptions.UnsupportedHexFormatException;
import org.priyanka.cmpe220.request.CreateNewsRequest;
import org.priyanka.cmpe220.response.CategoryNewsResponse;
import org.priyanka.cmpe220.response.CreateNewsResponse;
import org.priyanka.cmpe220.response.DeleteNewsResponse;
import org.priyanka.cmpe220.response.GetNewsResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

@ComponentScan({"org.priyaka.cmpe220.service"})
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/cmp220/project/user")
public class UserAuthenticationRestController {

    private static final String INVALID_NEWS_ID = "Invalid News Id";

    private NewsFeedService newsFeedService = new NewsFeedService() {{
        postConstruct();
    }};

    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserAuthenticationRestController.class, args);
    }



}
