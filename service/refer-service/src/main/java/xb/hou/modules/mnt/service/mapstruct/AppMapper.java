package xb.hou.modules.mnt.service.mapstruct;

import xb.hou.base.BaseMapper;
import xb.hou.modules.mnt.domain.App;
import xb.hou.modules.mnt.service.dto.AppDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @date 2019-08-24
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppMapper extends BaseMapper<AppDto, App> {

}
