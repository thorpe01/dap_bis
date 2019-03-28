/**
 *
 */
package fr.hoc.dap.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author house
 *
 */
// RestController : traite les requêtes via http avec du xml ou du json
// et va renvoyer du xml ou du json
@RestController
public class CalendarController {

    /** . */
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private Config myConfig;

    @Autowired
    private CalendarService event;

    // Capable de recevoir une requête d'un navigateur. Si le navigateur est sur
    // event/next
    // alors il exécute la méthode index
    /**
     * @RequestMapping .
     * @return .
     * @throws Exception .
     */
    @RequestMapping("/event/next")
    public String index(@RequestParam("userkey") String userKey) throws Exception {
        LOG.info(myConfig.getApplicationName());

        // °°Structure méthode°° : renvoie des données
        // °°Structure méthode°° : récupération des données
        return event.nextEvents(userKey);
        // °°Structure méthode°° : (absence) traitement des données
    }

}
