package com.liuyu.user.web.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: UploadFileDTO <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 下午5:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class UploadFileDTO {
    private String oldName;
    private String newName;
}
