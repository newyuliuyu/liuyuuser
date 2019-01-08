package com.liuyu.bs.business;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: SysConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-1-7 下午5:16 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SysConfig {
    private String uploadFileDir;
}
