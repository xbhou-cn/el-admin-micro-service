package xb.hou.service.mapstruct;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import xb.hou.base.BaseMapper;
import xb.hou.domain.Log;
import xb.hou.service.dto.LogErrorDTO;

/**
 * @author Zheng Jie
 * @date 2019-5-22
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDTO, Log> {

}