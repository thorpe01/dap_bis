package fr.hoc.dap.server;

//TODO brs by Djer |Audit Code| Configure PMD et CHeckStyle.
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.hoc.dap.server.service.AdminService;

/**
 * //TODO brs by Djer |JavaDoc| Il manque la description (de la classe), première ligne de la JavaDoc
 * @author house
 */
@Controller
public class AdminController {

    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    @Autowired
    private AdminService gAdminService;

    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    @RequestMapping("/admin")
    public String table(ModelMap model) throws GeneralSecurityException, IOException {
        model.addAttribute("mavar", "à vous ");

        DataStore<StoredCredential> myUsers = gAdminService.getUsers();

        Map<String, StoredCredential> userMap = new HashMap<String, StoredCredential>();

        //TODO 4a- Récupéer le(s) loginName
        Set<String> allkeys = myUsers.keySet();
        for (String key : allkeys) {
            StoredCredential value = myUsers.get(key);

            userMap.put(key, value);
        }

        model.addAttribute("users", userMap);

        return "admin";
        /*model.addAttribute("users", userMap);
        
        return "table";*/
    }
}
