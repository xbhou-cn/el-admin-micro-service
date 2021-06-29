package xb.hou.modules.mnt.service.mapstruct;

import xb.hou.base.BaseMapper;
import xb.hou.modules.mnt.domain.Database;
import xb.hou.modules.mnt.service.dto.DatabaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @date 2019-08-24
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DatabaseMapper extends BaseMapper<DatabaseDto, Database> {

}
