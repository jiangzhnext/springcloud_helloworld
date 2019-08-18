package com.next.springcloud.jiangzh;
import static java.lang.String.format;

import java.io.IOException;
import java.lang.reflect.Type;

import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
public class MyDecode implements Decoder {

	  @Override
	  public Object decode(Response response, Type type) throws IOException {
		  System.out.println("now in decode");
		Response.Body body = response.body();
	    if (body == null) {
	      return null;
	    }
	    if (String.class.equals(type)) {
	      return "Decode result : " + Util.toString(body.asReader());
	    }
	    throw new DecodeException(format("%s is not a type supported by this decoder.", type));
	  }
	}