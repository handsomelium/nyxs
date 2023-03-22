package com.liu.nyxs.netty.bo;

import lombok.Data;

@Data
public class ResultBO {
    private String msg;
    private boolean result;
    private Object data;
}