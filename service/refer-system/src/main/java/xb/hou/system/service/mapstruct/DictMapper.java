package xb.hou.system.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xb.hou.base.BaseMapper;
import xb.hou.system.domain.Dict;
import xb.hou.system.service.dto.DictDto;

/**
* @date 2019-04-10
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DictMapper extends BaseMapper<DictDto, Dict> {

}