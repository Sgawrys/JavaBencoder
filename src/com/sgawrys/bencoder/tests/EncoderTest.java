package com.sgawrys.bencoder.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.sgawrys.bencoder.Encoder;

public class EncoderTest {

	@Test
	public void testEncoding() {
		String encoded = Encoder.getInstance().encode("test");
		
		assertEquals("4:test", encoded);
	}
	
	
	@Test
	public void testIntEncoding() {
		int testInt = -8;
		
		String encodedInt = Encoder.getInstance().encodeInteger(testInt);
		
		assertEquals("i-8e", encodedInt);
	}
	
	@Test
	public void testDictEncoding() {
		Map<String, Integer> testMap = new HashMap<String, Integer>();
		
		testMap.put("Test", 45);
		testMap.put("Another", 34);
		
		String encodedMap = Encoder.getInstance().encodeDictionary(testMap);
		
		assertEquals("d4:Testi45e7:Anotheri34ee", encodedMap);
	}
	
	@Test
	public void testListEncoding() {
		List<Object> testList = new ArrayList<Object>();
		Map<String, Integer> testMap = new HashMap<String, Integer>();
		
		testMap.put("test", 4);
		
		testList.add(42);
		testList.add("String");
		testList.add(testMap);
		
		String encodedList = Encoder.getInstance().encodeList(testList);
		
		System.out.println(encodedList);
		assertEquals("li42e6:Stringd4:testi4eee", encodedList);
	}

}
