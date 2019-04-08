/**
 * 
 */
package fr.hoc.dap.server;

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

/**
 * @author house
 *
 */
@Controller
public class AdminController {

    @Autowired
    private AdminService gAdminService;

    @RequestMapping("/admin")
    public String table(ModelMap model) throws GeneralSecurityException, IOException {
        model.addAttribute("mavar", "à vous ");

        DataStore<StoredCredential> myUsers = gAdminService.getUsers();

        Map<String, StoredCredential> userMap = new HashMap<String, StoredCredential>();

        //TODO recupérer le adminService.getUsers()
        Set<String> allkeys = myUsers.keySet();

        for (String key : allkeys) {
            StoredCredential value = myUsers.get(key);
            userMap.put(key, value);
        }

        model.addAttribute("users", userMap);

        return "admin";

        //TODO 2-transformer en MAP

        /*model.addAttribute("users", userMap);
        
        return "table";*/
    }

}
