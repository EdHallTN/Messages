import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static User user;
    static ArrayList<Message> messages = new ArrayList<>();

    public static void main(String[] args) {

        Spark.init();

        Spark.get("/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "index.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messages", messages);
                        return new ModelAndView(m, "messages.html");
                    }
                }),
                new MustacheTemplateEngine()
        );

        Spark.post("/create-user",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    user = new User(name);
                    response.redirect("/");

                    return "";
                })
        );

        Spark.post("/create-message",
                ((request, response) -> {
                    String message = request.queryParams("message");
                    messages.add(new Message(message));
                    response.redirect("/");
                    return "";
                })
        );
    }
}