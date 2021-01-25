package com.leenow.demo.cache;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: LeeNow WangLi
 * @date: 2021/1/25 22:29
 * @description:
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CacheWrapper<V> implements Serializable {
    /**
     * Cache data.
     */
    private V data;
    /**
     * Expired time.
     */
    private Date expireAt;
    /**
     * Create time.
     */
    private Date createAt;

}
