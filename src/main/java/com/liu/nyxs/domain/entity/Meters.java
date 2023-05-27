package com.liu.nyxs.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author lium
 * @Date 2023/4/4
 * @Description
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("meters")
public class Meters {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @TableField(value = "ts",fill = FieldFill.INSERT)
    private Timestamp ts;

    private BigDecimal current;

    private BigDecimal voltage;

    private BigDecimal phase;

    private Integer groupid;

    private String location;
}
