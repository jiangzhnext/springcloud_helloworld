package com.next.springcloud.jiangzh;

import feign.Response;
import feign.codec.ErrorDecoder;

public class MyErrorDecode implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {

		System.out.println("now in ErrorDecode methodKey=="+methodKey+" , response==="+response);

		return new Exception();
	}

}
