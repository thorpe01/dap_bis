/**
 * 
 */
package fr.hoc.dap.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author house
 * 
 */
@Entity
public class DapUser {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String userKey;

    private String loginName;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the userkey.
     */
    public String getUserkey() {
        return userKey;
    }

    /**
     * @param userkey the userkey to set.
     */
    public void setUserkey(String userkey) {
        userKey = userkey;
    }

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

}
