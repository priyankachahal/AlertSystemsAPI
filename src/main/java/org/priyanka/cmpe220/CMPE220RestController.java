package org.priyanka.cmpe220;

import org.priyanka.cmpe220.dataobj.NewsDo;
import org.priyanka.cmpe220.dataobj.UserProfileDo;
import org.priyanka.cmpe220.exceptions.DataSourceException;
import org.priyanka.cmpe220.exceptions.InvalidNewsException;
import org.priyanka.cmpe220.exceptions.UnsupportedHexFormatException;
import org.priyanka.cmpe220.request.AuthenticateUserRequest;
import org.priyanka.cmpe220.request.CreateNewsRequest;
import org.priyanka.cmpe220.request.CreateUserProfileRequest;
import org.priyanka.cmpe220.response.*;
import org.priyanka.cmpe220.service.NewsFeedService;
import org.priyanka.cmpe220.service.UserAuthenticationService;
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
@RequestMapping(value = "/cmp220/project")
public class CMPE220RestController {

    private static final String INVALID_NEWS_ID = "Invalid News Id";
    private static final String USER_EMAIL_ALREADY_EXISTS = "User with requested email already exists";

    private UserAuthenticationService userAuthenticationService = new UserAuthenticationService() {{
        postConstruct();
    }};

    private NewsFeedService newsFeedService = new NewsFeedService() {{
        postConstruct();
    }};

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CMPE220RestController.class, args);
    }

    @RequestMapping(value = "/news_feed/news/{newsId}", method = RequestMethod.GET, produces = "application/json")
    public GetNewsResponse getNewsById(@PathVariable String newsId) {
        try {
            NewsDo newsDo = newsFeedService.getNewsById(newsId);
            GetNewsResponse getNewsResponse = new GetNewsResponse();
            getNewsResponse.setNews(newsDo);
            return getNewsResponse;
        } catch (DataSourceException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/news_feed/news", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public CreateNewsResponse postNews(@RequestBody @Valid CreateNewsRequest createNewsRequest) {
        try {
            String newsId = newsFeedService.save(createNewsRequest.getNews());
            if (newsId != null && newsId.length() > 0) {
                CreateNewsResponse createNewsResponse = new CreateNewsResponse();
                createNewsResponse.setNewsId(newsId);
                return createNewsResponse;
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataSourceException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/news_feed/news/{newsId}", method = RequestMethod.DELETE, produces = "application/json")
    public DeleteNewsResponse deleteNews(@PathVariable String newsId) {
        try {
            // if no news ID exists in the delete request
            if (newsId == null || newsId.length() == 0) {
                throw new InvalidNewsException(INVALID_NEWS_ID);
            }
            // try deleting by request id
            boolean isSuccess = newsFeedService.deleteNewsById(newsId);

            // if success then we are done
            if (isSuccess) {
                DeleteNewsResponse deleteNewsResponse = new DeleteNewsResponse();
                deleteNewsResponse.setNewsId(newsId);
                return deleteNewsResponse;
            } else {
                // else the Id doesn't exists in the data base
                throw new InvalidNewsException(INVALID_NEWS_ID);
            }
        } catch (DataSourceException exception) {
            // couldn't connect to mongo DB
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnsupportedHexFormatException exception) {
            throw new InvalidNewsException(INVALID_NEWS_ID);
        }
    }

    @RequestMapping(value = "/news_feed/news/category/{category}", method = RequestMethod.GET, produces = "application/json")
    public CategoryNewsResponse CategorizeNews(@PathVariable String category,
                                               @RequestParam("start") int start,
                                               @RequestParam("limit") int limit) {
        try {
            List<NewsDo> newsDo = newsFeedService.getNewsbyCategory(category, start, limit);
            CategoryNewsResponse categoryNewsResponse = new CategoryNewsResponse();
            categoryNewsResponse.setNews(newsDo);
            return categoryNewsResponse;
        } catch (DataSourceException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/user", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public UserRegistrationResponse registerUser(@RequestBody @Valid CreateUserProfileRequest createUserProfileRequest) {
        try {
            String userId = userAuthenticationService.save(createUserProfileRequest.getUserProfile());
            if (userId != null && userId.length() > 0) {
                UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
                userRegistrationResponse.setId(userId);
                return userRegistrationResponse;
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (DataSourceException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public AuthenticateUserResponse authenticateUser(@RequestBody @Valid AuthenticateUserRequest authenticateUserRequest) {
        try {
            UserProfileDo userProfileDo = userAuthenticationService.getAuthenticatedUser(authenticateUserRequest.getEmail(), authenticateUserRequest.getPassword());
            AuthenticateUserResponse authenticateUserResponse = new AuthenticateUserResponse();
            if (userProfileDo != null) {
                authenticateUserResponse.setSuccess(true);
                authenticateUserResponse.setName(userProfileDo.getName());
            } else {
                authenticateUserResponse.setSuccess(false);
            }
            return authenticateUserResponse;
        } catch (DataSourceException exception) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}