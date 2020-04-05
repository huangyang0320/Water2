package com.wapwag.woss.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		List<String> aa = new ArrayList<String>();
		aa.add("1");
		aa.add("2");
		aa.add("3");
		aa.add("4");
		aa.add("5");
		aa.add("6");
		aa.remove(2);
		System.out.println(aa.size());
	}
}
