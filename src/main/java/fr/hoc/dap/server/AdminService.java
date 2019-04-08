package fr.hoc.dap.server;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

@Service
public class AdminService extends GoogleService {

    public DataStore<StoredCredential> getUsers() throws GeneralSecurityException, IOException {
        AuthorizationCodeFlow flow = super.getFlow();
        return flow.getCredentialDataStore();
    }

}
