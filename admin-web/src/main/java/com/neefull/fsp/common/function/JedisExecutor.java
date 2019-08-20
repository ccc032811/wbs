package com.neefull.fsp.common.function;

import com.neefull.fsp.common.exception.RedisConnectException;

/**
 * @author pei.wang
 */
@FunctionalInterface
public interface JedisExecutor<T, R> {
    R excute(T t) throws RedisConnectException;
}
