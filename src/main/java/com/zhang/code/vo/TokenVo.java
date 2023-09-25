package com.zhang.code.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 保存token信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVo {
    // 过期时间
    private Long expireTime;

    // token
    private String token;
}
