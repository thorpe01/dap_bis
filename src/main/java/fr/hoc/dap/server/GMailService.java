package fr.hoc.dap.server;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

/** ***************************   DEBUT SINGLETON. */
//TODO brs by Djer |JavaDoc| Il manque la JavaDoc
@Service
public final class GMailService extends GoogleService {

    /**
     * //TODO brs by Djer |JavaDoc| Il manque la description (de la méthode), première ligne de la JavaDoc
     * build a Gmail Service to access Google Data.
     * @return a service instance
     * @throws GeneralSecurityException = ça va venir + tard.
     * @throws IOException = Un technicien se prend les pieds dans un câble.
     */
    // °°Structure méthode°° : préparation des services
    public Gmail buildService(String userId) throws GeneralSecurityException, IOException {
        // °°Structure méthode°° : préparation des services
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(getLaConf().getApplicationName()).build();
        return service;
    }

    /**
    *
    * @param userId = google UserId, "me" by Default.
    * @param query = filtre les messages.
    * @throws IOException = google.
    * @throws GeneralSecurityException = google.
    * @return manquante.
    */
    public Integer unreadMail(String userKey, final String userId, final String query)
            throws IOException, GeneralSecurityException {
        //TODO brs by Djer |Log4J| Une petite Log (en Info) ?
        // °°Structure méthode°° : appel service
        Gmail service = buildService(userKey); // °° construit le service
        // °°Structure méthode°° : récupération des données via la variable response
        // °° appel au service après le égal : résultat mis dans "response"
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

        // °°Structure méthode°° : traitement "métier" des données
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                // °° appel au service après le égal (exceptionnel dans un traitement "métier") :
                // °° résultat mis dans "response" (ancienne valeur écrasée)
                response = service.users().messages().list(userId).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        Integer nbMessage = messages.size();
        // °°Structure méthode°° : renvoie des données
        return nbMessage;
    }
} // fin de la classe
