package com.sgawrys.bencoder.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sgawrys.bencoder.exceptions.DecoderException;
import com.sgawrys.bencoder.tokens.BencodingToken;

/**
 * Decoder for bencoded strings.
 * 
 * @author Stefan
 *
 */
public class Decoder {

	private static final Decoder DECODER = new Decoder();
	
	private Decoder() {
		
	}
	
	public static Decoder getInstance() {
		return DECODER;
	}
	
	public Object decode(String bencodedString) {
		
		return null;
	}
	
}
