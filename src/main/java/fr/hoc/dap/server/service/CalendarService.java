
package fr.hoc.dap.server.service;

/**
 * @author house
 *
 */

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * //TODO brs by Djer |JavaDoc| Il manque la description (de la classe), première ligne de la JavaDoc
 * @author house
 */
@Service
public final class CalendarService extends GoogleService {
    /** . */
    private static final Logger LOG = LogManager.getLogger("fr.hoc.dap.server.Launcher");

    /**
     * La présence d'un constructeur privé supprime le constructeur public par
     * défaut.
     */
    // private CalendarService() {
    // }

    /**
     * //TODO brs by Djer |JavaDoc| Il manque la description (de la methode), première ligne de la JavaDoc
     * @throws IOException              = google.
     * @throws GeneralSecurityException = google.
     * @return .
     */
  //TODO brs by Djer |POO| Les noms de méthode ont un général un verbe "retreiveNextEvents" serait mieux
    public String nextEvents(String userKey) throws IOException, GeneralSecurityException {
        LOG.info("Affichage du prochain évènement ");

        
      //TODO brs by Djer |POO| Tu peux extraire les 4 lignes suivantes dans une méthode (privée) "buildService(....)" pour clarifer et éviter un commentaire
        // Build a new authorized API client service.
        // °°Structure méthode°° : préparation des services
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getLaConf().getApplicationName()).build();

        // List the next 10 events from the primary calendar.
        // °°Structure méthode°° : récupération des données via les variables now et
        // events
        // °° appel au service après le égal : résultat mis dans "now"
        DateTime now = new DateTime(System.currentTimeMillis()); // préparation de données d'entrée

        // °° appel au service après le égal : résultat mis dans "events"
        Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();

        List<Event> items = events.getItems();
        String rdv;

        // °°Structure méthode°° : traitement "métier" des données
        if (items.isEmpty()) {
            rdv = "No upcoming event found !";
        } else {
            rdv = "Next event";
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                rdv = rdv + " " + event.getSummary() + "(" + start + "), ";
              //TODO brs by Djer |POO| Pas de sysOut sur un serveur. Utilise une Log (en debug à priori) si nécéssaire
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }

        LOG.info("Fin de la methode nextEvents");
        // °°Structure méthode°° : Renvoie des données
        // ATTENTION : il ne faut qu'un retour de données = bonne pratique
        // (donc pas dans la boucle, ni dans le if)
        // Sinon plusieurs return dans le traitement "métiers" = plusieurs sorties, donc
        // dans la suite du code, c'est piégieux.
        return rdv;
    }
}
