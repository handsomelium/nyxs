package com.liu.nyxs.domain;

import lombok.Data;

import java.util.List;

/**
 * @Author lium
 * @Date 2023/7/18
 * @Description
 */
@Data
public class JimuResponse<T> {

    private List<T> data;

    private Integer total;

    private Integer count;


}
