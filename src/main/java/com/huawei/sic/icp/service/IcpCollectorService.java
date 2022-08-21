package com.huawei.sic.icp.service;

/**
 * @since 2022-08-20
 */
public interface IcpCollectorService {
    /**
     * 采集host对应的icp备案信息
     *
     * @param host 主机名称
     */
    void excute(String host);
}
