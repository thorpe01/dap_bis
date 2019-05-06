package fr.hoc.dap.server;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

//TODO brs by Djer |JavaDoc| Il manque la JavaDoc
@Service
public class AdminService extends GoogleService {

    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    public DataStore<StoredCredential> getUsers() throws GeneralSecurityException, IOException {
        AuthorizationCodeFlow flow = super.getFlow();
        return flow.getCredentialDataStore();
    }

}
