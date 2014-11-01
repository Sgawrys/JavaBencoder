package com.sgawrys.bencoder.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sgawrys.bencoder.application.Decoder;
import com.sgawrys.bencoder.exceptions.DecoderException;

public class DecoderTest {

	@Test
	public void testIntegerDecode() {
		String bencodedInteger = "i42e";
		
		Integer decodedInteger =  Decoder.getInstance().decodeInt(bencodedInteger);
		Integer testInteger = new Integer(42);
		
		assertEquals(testInteger, decodedInteger);
		
		String negativeBencodedInteger = "i-42e";
		
		Integer negativeDecoded = Decoder.getInstance().decodeInt(negativeBencodedInteger);
		Integer testInteger2 = new Integer(-42);
		
		assertEquals(testInteger2, negativeDecoded);
	}
	
	@Test
	public void testStringDecode() throws DecoderException {
		String test = "4:test";
		String result = Decoder.getInstance().decodeString(test);
		
		assertEquals("test", result);
	}
	
	@Test(expected = DecoderException.class)
	public void testStringDecode_2() throws DecoderException {
		String test = "3435353";
		String result = Decoder.getInstance().decodeString(test);
	}

}
