package xb.hou.modules.mnt.service.dto;

import lombok.Data;
import xb.hou.annotation.Query;
import java.sql.Timestamp;
import java.util.List;

/**
* @date 2019-08-24
*/
@Data
public class DatabaseQueryCriteria{

	/**
	 * 模糊
	 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

	/**
	 * 精确
	 */
    @Query
    private String jdbcUrl;

	@Query(type = Query.Type.BETWEEN)
	private List<Timestamp> createTime;
}
