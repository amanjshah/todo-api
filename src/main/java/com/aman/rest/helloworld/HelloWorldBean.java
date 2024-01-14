package com.aman.rest.helloworld;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HelloWorldBean {

	private String message;

	@Override
	public String toString() {
		return "HelloWorldBean [message=" + message + "]";
	}
}
