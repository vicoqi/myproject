/*
 * Copyright 2017 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.vic.chain;


import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;

@Data
public class AgvResponse extends CustomResult<Boolean>implements Serializable {

    private static final long serialVersionUID = -2839023454203029127L;

    public static final AgvResponse SUCCESS = new AgvResponse();

    private  String ab;
    @Override
    public String toString() {
        return super.getMessage() + "(" + super.getCode() + ")";
    }

}