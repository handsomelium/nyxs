package com.liu.nyxs.domain.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试用
 * </p>
 *
 * @author lium
 * @since 2023-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("test")
public class TestDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("age")
    private Integer age;

    @TableField("weight")
    private BigDecimal weight;

    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private List<TestDO> list;


}
