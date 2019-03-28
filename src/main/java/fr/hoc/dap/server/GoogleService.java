package fr.hoc.dap.server;

/**
 * @author house
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

/**
 * Classe qui regroupe les autorisations pour accès à Google.
 */
@Service
public class GoogleService {
    /** . */
    private static final Logger LOG = LogManager.getLogger();

    /** Make an instance of Json Factory. */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Users Port selected for GOOGLE. */
    /** Port local pour la réponse au consentement de l'utilisateur. */
    //private static final Integer PORT = 8080;

    /**
     * Global instance of the scopes required by this quickstart. If modifying these
     * scopes, delete your previously saved tokens/ folder. Create a list of scopes.
     */
    private static ArrayList<String> scopes; // changé pour augmenter le scope

    /** Attribute of actual Config. */
    @Autowired
    private Config laConf;

    public GoogleService() {
        // Build a new authorized API client service.
        scopes = new ArrayList<String>();
        scopes.add(GmailScopes.GMAIL_READONLY); // chgmt pour exo 3
        scopes.add(GmailScopes.GMAIL_LABELS); // pour exo 1 et 2²
        scopes.add(CalendarScopes.CALENDAR_READONLY); // pour exo 4
    }

    /**
     * Creates an authorized Credential object.
     * 
     * @param httpTransport The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException              If the credentials.json file cannot be
     *                                  found.
     * @throws GeneralSecurityException
     */
    protected Credential getCredentials(String userId) throws IOException, GeneralSecurityException {
        LOG.info("Recherche du credential :" + userId);
        AuthorizationCodeFlow flow = getFlow();

        return flow.loadCredential(userId);
    }

    public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // Load client secrets.
        // -> MAil envoie un message a CLass "getResourceAsStream"
        // -> Acces envoie une message "getCLientSecretFile" à
        // une instance de Config
        final InputStream in = GoogleService.class.getResourceAsStream(laConf.getClientSecretFile());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                clientSecrets, scopes)
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(laConf.getCredentialFolder())))
                        .setAccessType("offline").build();

        return flow;
    }

    /**
     * @return the laConf
     */
    protected Config getLaConf() {
        return laConf;
    }

    /**
     * @param newLaConf the laConf to set
     */
    public void setLaConf(final Config newLaConf) {
        this.laConf = newLaConf;
    }

}
