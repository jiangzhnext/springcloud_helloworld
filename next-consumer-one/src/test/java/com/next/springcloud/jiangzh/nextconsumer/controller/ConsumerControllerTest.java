package com.next.springcloud.jiangzh.nextconsumer.controller;

import com.next.springcloud.jiangzh.nextconsumer.common.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ConsumerControllerTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void sayHello() throws Exception{
        String uri = "/consumer/sayHello";

        for(int i=0;i<2;i++){
            String mvcResult=mockMvc.perform(MockMvcRequestBuilders.get(uri)
                    .param("message","eurekaClient")
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn().getResponse().getContentAsString();

            System.out.println("mvcResult="+mvcResult);
        }

    }

}
