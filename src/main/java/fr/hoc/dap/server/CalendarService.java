
package fr.hoc.dap.server;

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
 *
 * @author house
 *
 */
@Service
public final class CalendarService extends GoogleService {
    /** . */
    private static final Logger LOG = LogManager.getLogger("fr.hoc.dap.server.Launcher");

    /**
     *
     * @throws IOException              = google.
     * @throws GeneralSecurityException = google.
     * @return .
     */
    public String nextEvents(String userKey) throws IOException, GeneralSecurityException {
        LOG.info("Affichage du prochain évènement ");

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
                rdv = rdv + " BOB " + event.getSummary() + "(" + start + "), ";
            }
        }

        LOG.info("Fin de la methode nextEvents");
        // °°Structure méthode°° : Renvoie des données
        // ATTENTION : il ne faut qu'un retour de données = bonne pratique
        // (donc pas dans la boucle, ni dans le if)
        // Sinon plusieurs return dans le traitement "métiers" = plusieurs sorties, donc
        // dans la suite
        // du code, c'est piégieux.
        return rdv;
    }

}
