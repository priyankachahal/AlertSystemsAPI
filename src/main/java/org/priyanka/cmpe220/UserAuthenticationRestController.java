package org.priyanka.cmpe220;

import org.priyanka.cmpe220.service.NewsFeedService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

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
