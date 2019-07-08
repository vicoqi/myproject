package com.vic.mychain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by wang on 2019/7/2.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FirstMessage extends IMessage{
    private String type;
}
