package xb.hou.modules.mnt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xb.hou.modules.mnt.domain.Database;

/**
* @author zhanghouying
* @date 2019-08-24
*/
public interface DatabaseRepository extends JpaRepository<Database, String>, JpaSpecificationExecutor<Database> {
}
