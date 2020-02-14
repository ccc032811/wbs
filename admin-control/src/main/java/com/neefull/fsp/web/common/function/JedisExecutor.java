package com.neefull.fsp.web.common.function;

import com.neefull.fsp.web.common.exception.RedisConnectException;

/**
 * @author pei.wang
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
