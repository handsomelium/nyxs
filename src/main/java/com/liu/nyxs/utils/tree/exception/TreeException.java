package com.liu.nyxs.utils.tree.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：haoshen
 * @date ：2023-12-12
 * @description : 自定义树异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TreeException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String message;

    public TreeException(String message) {
        super();
        this.message = message;
    }

}
