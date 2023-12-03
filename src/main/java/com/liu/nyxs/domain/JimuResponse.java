package com.liu.nyxs.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lium
 * @Date 2023/7/18
 * @Description
 */
@Data
public class JimuResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> data;

    private Long total;

    private Integer count;


}
