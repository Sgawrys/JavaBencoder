package com.sgawrys.bencoder.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.sgawrys.bencoder.application.Decoder;
import com.sgawrys.bencoder.exceptions.DecoderException;

public class DecoderTest {

	@Test
	public void testIntegerDecode() {
		String bencodedInteger = "i42e";
		
		Integer decodedInteger = (Integer)Decoder.getInstance().decode(bencodedInteger);
		Integer testInteger = new Integer(42);
		
		assertEquals(testInteger, decodedInteger);
		
		String negativeBencodedInteger = "i-42e";
		
		Integer negativeDecoded = (Integer)Decoder.getInstance().decode(negativeBencodedInteger);
		Integer testInteger2 = new Integer(-42);
		
		assertEquals(testInteger2, negativeDecoded);
	}
	
	@Test
	public void testStringDecode() throws DecoderException {
		String test = "4:test";
		String result = (String)Decoder.getInstance().decode(test);
		
		assertEquals("test", result);
	}
	
	@Test
	public void testListIntDecode() throws DecoderException {
		String listTest = "li42ei99ei42ee";
		
		List<?> result = (List<?>)Decoder.getInstance().decode(listTest);
		
		List<Integer> testlist = new ArrayList<Integer>();
		testlist.add(42);
		testlist.add(99);
		testlist.add(42);
		
		assertEquals(testlist.get(0), result.get(0));
	}
	
	@Test
	public void testListMultiDecode() throws DecoderException {
		String listTest = "li42e2:cae";
		
		List<?> result = (List<?>)Decoder.getInstance().decode(listTest);
		
		List<Object> testList = new ArrayList<Object>();
		testList.add(42);
		
		testList.add("ca");
		
		assertEquals(testList.get(0), result.get(0));
		assertEquals(testList.get(1), result.get(1));
	}
	
	@Test
	public void testMultiListDecode() throws DecoderException {
		String listlistTest = "lli43eee";
		
		List<?> result = (List<?>)Decoder.getInstance().decode(listlistTest);
		
		List<Object> testList = new ArrayList<Object>();
		List<Object> testTestList = new ArrayList<Object>();
		testTestList.add(43);
		
		testList.add(testTestList);
		
		assertEquals(testList, result);
 	}
	
	@Test
	public void testDictDecode() throws DecoderException {
		String dictTest = "d4:testi60ee";
		
		Map<String, Object> testDictionary = new HashMap<String, Object>();
		testDictionary.put("test", 60);
		
		Map<?, ?> result = (Map<?, ?>)Decoder.getInstance().decode(dictTest);
		
		assertEquals(testDictionary, result);
	}
	
	@Test
	public void testMultiDictDcode() throws DecoderException {
		String dictDictTest = "d4:testd3:keyi43eee";
		
		Map<String, Object> testDictionary = new HashMap<String, Object>();
		Map<String, Object> secondTestDict = new HashMap<String, Object>();
		secondTestDict.put("key", 43);
		testDictionary.put("test", secondTestDict);
		
		Map<?, ?> result = (Map<?, ?>)Decoder.getInstance().decode(dictDictTest);
		
		assertEquals(testDictionary, result);
	}

}
