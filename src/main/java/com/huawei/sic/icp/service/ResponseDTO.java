package com.huawei.sic.icp.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @since 2022-08-21
 */
@Setter
@Getter
public class ResponseDTO {
    private int code;

    private String msg;

    private String data;
}
