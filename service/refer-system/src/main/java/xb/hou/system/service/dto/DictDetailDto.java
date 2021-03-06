package xb.hou.system.service.dto;

import lombok.Getter;
import lombok.Setter;
import xb.hou.base.BaseDTO;

import java.io.Serializable;

/**
* @date 2019-04-10
*/
@Getter
@Setter
public class DictDetailDto extends BaseDTO implements Serializable {

    private Long id;

    private DictSmallDto dict;

    private String label;

    private String value;

    private Integer dictSort;
}