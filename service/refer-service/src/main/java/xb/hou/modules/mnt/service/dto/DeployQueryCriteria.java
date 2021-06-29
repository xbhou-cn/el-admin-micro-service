package xb.hou.modules.mnt.service.dto;

import lombok.Data;
import xb.hou.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
* @date 2019-08-24
*/
@Data
public class DeployQueryCriteria{

	/**
	 * 模糊
	 */
    @Query(type = Query.Type.INNER_LIKE, propName = "name", joinName = "app")
    private String appName;

	@Query(type = Query.Type.BETWEEN)
	private List<Timestamp> createTime;

}
