package com.liuyu.user.web.dto;

import com.liuyu.user.web.domain.Resource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ClassName: SaveRoleResourceDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-3 下午3:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class SaveXResourceDTO {
    private List<Resource> saveResource;
    private List<Resource> delResource;
}
