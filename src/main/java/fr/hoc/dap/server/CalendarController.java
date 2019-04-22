package fr.hoc.dap.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * //TODO brs by Djer |JavaDoc| Il manque la description (de la classe), première ligne de la JavaDoc
 * @author house
 */
// RestController : traite les requêtes via http avec du xml ou du json
// et va renvoyer du xml ou du json
@RestController
public class CalendarController {

    /** . */
    private static final Logger LOG = LogManager.getLogger();

    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    @Autowired
    private Config myConfig;

    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    @Autowired
    private CalendarService event;

    /**
     * //TODO brs by Djer |JavaDoc| Il manque la description (de la methode), première ligne de la JavaDoc
     * @RequestMapping .
     * @return .
     * @throws Exception .
     */
    // Capable de recevoir une requête d'un navigateur. Si le navigateur est sur
    // event/next
    // alors il exécute la méthode index
    @RequestMapping("/event/next")
    public String index(@RequestParam("userkey") String userKey) throws Exception {
        LOG.info(myConfig.getApplicationName());

        // °°Structure méthode°° : renvoie des données
        // °°Structure méthode°° : récupération des données
        return event.nextEvents(userKey);
        // °°Structure méthode°° : (absence) traitement des données
    }

}
