/**
 * <p>Title: WebSocketConfig.java<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) AKE 2019<／p>
 * <p>Company: AKE<／p>
 * @author GuoJM
 * @date 2019年1月18日
 * @version 1.0
 */
package com.liu.nyxs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author GuoJM
 *
 */
@Configuration
public class WebSocketConfig {  
	
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();  
    }  
  
} 
