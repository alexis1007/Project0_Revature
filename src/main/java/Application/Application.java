package Application;
import io.javalin.Javalin;

public class Application {
        public static void main(String[] args) {
            var app = Javalin.create(/*config*/)
                    .get("/", ctx -> ctx.result("Hello World"))
                    .start(7070);


            System.out.println("q");
        }

}
