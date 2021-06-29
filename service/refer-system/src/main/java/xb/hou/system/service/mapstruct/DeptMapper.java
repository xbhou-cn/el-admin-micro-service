package xb.hou.system.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xb.hou.base.BaseMapper;
import xb.hou.system.domain.Dept;
import xb.hou.system.service.dto.DeptDto;

/**
* @date 2019-03-25
*/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptDto, Dept> {
}