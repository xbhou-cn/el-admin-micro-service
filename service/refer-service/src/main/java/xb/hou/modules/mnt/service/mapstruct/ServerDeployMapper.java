package xb.hou.modules.mnt.service.mapstruct;

import xb.hou.base.BaseMapper;
import xb.hou.modules.mnt.domain.ServerDeploy;
import xb.hou.modules.mnt.service.dto.ServerDeployDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @date 2019-08-24
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerDeployMapper extends BaseMapper<ServerDeployDto, ServerDeploy> {

}
