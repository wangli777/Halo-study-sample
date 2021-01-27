package com.leenow.demo.util;

import cn.hutool.core.lang.UUID;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/24 19:59
 * @description:
 */
public class UUIDUtils {
    public static String randomUUIDWithoutDash() {
        return StringUtils.remove(UUID.randomUUID().toString(), '-');
    }
}
