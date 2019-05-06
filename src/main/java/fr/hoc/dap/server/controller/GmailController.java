package fr.hoc.dap.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.hoc.dap.server.service.GMailService;

/**
 * //TODO brs by Djer |JavaDoc| Il manque la description (de la classe), première ligne de la JavaDoc
 * @author house
 */
// RestController : traite les requêtes via http avec du xml ou du json
// et va renvoyer du xml ou du json
@RestController
public class GmailController {
    /** . */
    private static final Logger LOG = LogManager.getLogger();

    /**
     * //TODO brs by Djer |JavaDoc| Il manque la description (de la méthode), première ligne de la JavaDoc
     * @RequestMapping .
     * @return .
     * @throws Exception .
     */
    @Autowired
    private GMailService mesmails;

    // Capable de recevoir une requête d'un navigateur. Si le navigateur est sur
    // email/nbunread
    // alors il exécute la méthode index

    //TODO brs by Djer |JavaDoc| Pour que la JavaDoc fonctionne bien il faut UN seul bloc de commentaire par élément a commenter (regroupe to thors et ton return dans le même bloc)
    /**
     * @return .
     */

    /**
     * @throws Exception .
     */
    @RequestMapping("/email/nbunread")
    //TODO 2a- Demander le loginName au lieu du UserKey
    public Integer index(@RequestParam("loginName") String loginName) throws Exception {
        //TODO brs by Djer |Log4J| Contextualise tes messages "Récupération du nombre d'email pour l'utilisateur " + userKey
        LOG.info("Récupération ud nom d'email");
        //TODO 2b- ???? Vérifier que le loginName existe ?

        //TODO 2c- Récupére la liste des userKey pour le loginName
        //TODO 2d- Demander le nb EMail **poru chaque** userKey lié au loginName
        //TODO 2e- Faire la **somme** des nombre d'emails

        return mesmails.unreadMail(loginName, "me", "is:unread in:inbox");
    }
}
