/**
 * 
 */
package fr.hoc.dap.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * //TODO brs by Djer |JavaDoc| Il manque la description (de la classe), première ligne de la JavaDoc
 * @author house
 */
@Controller
public class HelloControler {

    @RequestMapping("/hello2")
    public String kik(ModelMap model) {
        model.addAttribute("mavar", "toto");
        List<String> bestioles = new ArrayList<>();
        bestioles.add("cheval");
        bestioles.add("tortue");
        bestioles.add("clébard");
        bestioles.add("murène");

        model.addAttribute("bebetes", bestioles);

        return "hello";
    }
}
