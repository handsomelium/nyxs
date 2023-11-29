package com.liu.nyxs.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WeatherScraper {
    public static void main(String[] args) {
        String city = "Shanghai"; // 要查询的城市名称

        String weatherUrl = "http://www.weather.com.cn/weather1d/101010100.shtml";
        String weatherData = getWeatherData(weatherUrl);

        if (weatherData != null) {
            parseWeatherData(weatherData);
        } else {
            System.out.println("无法获取天气数据，请检查城市名称或网络连接。");
        }
    }

    // 发送HTTP GET请求获取天气数据
    private static String getWeatherData(String url) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解析天气数据
    private static void parseWeatherData(String html) {
        Document doc = Jsoup.parse(html);
        Element weatherElem = doc.selectFirst(".today .wea");
        Element temperatureElem = doc.selectFirst(".today .tem i");

        String weather = weatherElem != null ? weatherElem.text() : "未知";
        String temperature = temperatureElem != null ? temperatureElem.text() : "未知";

        System.out.println("城市：北京");
        System.out.println("天气：" + weather);
        System.out.println("温度：" + temperature);
    }
}