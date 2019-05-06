/**
 * 
 */
package fr.hoc.dap.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.hoc.dap.server.data.DapUser;
import fr.hoc.dap.server.data.DapUserRepository;

/**
 * 
 * @author house
 */

@RestController
public class TestJpacontroller {
    @Autowired
    private DapUserRepository dapUserRepository;

    @RequestMapping("/test/createDapuser")
    public DapUser creatDapUser(@RequestParam String userKey, String loginName) {
        DapUser monUser = new DapUser();
        monUser.setLoginName(loginName);
        monUser.setUserkey(userKey);
        DapUser savedUser = dapUserRepository.save(monUser);
        return savedUser;
    }

    @RequestMapping("/test/loadDapUser")
    public DapUser loadDapUser(@RequestParam String userKey) {
        DapUser loadUser = dapUserRepository.findByUserKey(userKey);
        return loadUser;
    }
}
