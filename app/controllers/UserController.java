package controllers;

import model.User;
import model.UserRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;


public class UserController  extends Controller {
    private final FormFactory formFactory;
    private final UserRepository userRepository;
    private final HttpExecutionContext ec;

    @Inject
    public UserController(FormFactory formFactory, UserRepository userRepository, HttpExecutionContext ec){
        this.formFactory = formFactory;
        this.userRepository = userRepository;
        this.ec = ec;
    }
    public Result index(final Http.Request request) {
        //ok(views.html.index.render(request));
        return ok();
    }

    public CompletionStage<Result> addUser(final Http.Request request) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>");
        User user = formFactory.form(User.class).bindFromRequest(request).get();
        return userRepository.save(user)
                .thenApplyAsync(p -> ok(), ec.current());
    }

    public CompletionStage<Result> getUsers() {
        return userRepository.list()
                .thenApplyAsync(userStream -> ok(toJson(userStream.collect(Collectors.toList()))), ec.current());
    }
}
