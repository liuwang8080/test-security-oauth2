package com.macro.cloud.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Created by macro on 2019/8/29.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
public class User {
    @ApiModelProperty("用户ID")
    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
