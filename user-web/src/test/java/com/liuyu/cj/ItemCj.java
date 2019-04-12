package com.liuyu.cj;

import lombok.*;

/**
 * ClassName: ItemCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-7 下午5:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCj {
    private String itemName;
    private double score;
    private String selectItem;
}
