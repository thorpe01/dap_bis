/**
 * 
 */
package fr.hoc.dap.server.data;

import org.springframework.data.repository.CrudRepository;

/**
 * @author house
 *
 */
public interface DapUserRepository extends CrudRepository<DapUser, Long> {

    DapUser findByUserKey(String userKey);

}
