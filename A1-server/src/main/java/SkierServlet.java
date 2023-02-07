import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.beans.JavaBean;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "SkierServlet", value = "/SkierServlet")
public class SkierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write("It works!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String jsonString = ReadBigStringIn(req.getReader());
        JsonObject convertedObject = new Gson().fromJson(jsonString, JsonObject.class);
        res.setContentType("text/plain");
        res.getWriter().write(validation(convertedObject));
        if (!(validation(convertedObject)).equals("true")) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            res.setStatus(400);
        }
    }


    public String validation(JsonObject json) {
        try {
            String swipe = json.get("swipe").getAsString();
            if (!swipe.equals("Left") && !swipe.equals("Right")) {
                return "swipe";
            }
            if (json.get("swiper").getAsInt() < 1 || json.get("swiper").getAsInt() > 5000) {
                return "swiper";
            }
            if (json.get("swipee").getAsInt() < 1 || json.get("swipee").getAsInt() > 1000000) {
                return "swipee";
            }
            String comment = json.get("comment").getAsString();
            if (comment.length() != 256) {
                return "comment";
            }
            for(int i = 0; i < comment.length(); i++) {
                if (!Character.isLetter(comment.charAt(i))) {
                    return "comment";
                }
            }

        } catch (Exception e) {
            return "O";
        }



        return "true";
    }
    public String ReadBigStringIn(BufferedReader buffIn) throws IOException {
        StringBuilder everything = new StringBuilder();
        String line;
        while( (line = buffIn.readLine()) != null) {
            everything.append(line);
        }
        return everything.toString();
    }
}
