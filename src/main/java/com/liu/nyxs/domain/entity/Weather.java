package com.liu.nyxs.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Weather {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private Timestamp ts;
    private Float temperature;
    private Float humidity;
    private String location;
    private int groupId;

    public Weather() {
    }

    public Weather(Timestamp ts, float temperature, float humidity) {
        this.ts = ts;
        this.temperature = temperature;
        this.humidity = humidity;
    }

}
