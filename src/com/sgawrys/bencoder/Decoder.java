package com.sgawrys.bencoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgawrys.bencoder.exceptions.DecoderException;

/**
 * Decoder for bencoded strings.
 * 
 * @author Stefan
 *
 */
public class Decoder {

	private static final Decoder DECODER = new Decoder();
	
	private static final String START_TOKEN = "i";
	private static final String START_DICT_TOKEN = "d";
	private static final String START_LIST_TOKEN = "l";
	
	private static final String END_TOKEN = "e";
	private static final String COLON_TOKEN = ":";
	
	private Decoder() {
		
	}
	
	public static Decoder getInstance() {
		return DECODER;
	}
	
	public Object decode(String bencodedString) {
		return determineType(bencodedString);
	}
	
	public Object determineType(String bencodedString) {
		if(bencodedString.startsWith(START_LIST_TOKEN)) {
			return decodeList(bencodedString);
		}
		
		if(bencodedString.startsWith(START_DICT_TOKEN)) {
			return decodeDict(bencodedString);
		}
		
		if(bencodedString.startsWith(START_TOKEN)) {
			return decodeInt(bencodedString);
		}
		
		return null;
	}
	
	public List<?> decodeList(String encodedList) {
		List<Object> decodedList = new ArrayList<Object>();
		
		return decodedList;
	}
	
	public Map<?,?> decodeDict(String encodedMap) {
		Map<Object, Object> decodedMap = new HashMap<Object, Object>();
		
		return decodedMap;
	}
	
	public Integer decodeInt(String encodedInt) {
		Integer decodedInt = Integer.parseInt(encodedInt.substring(1, encodedInt.length() - 1));
		return decodedInt;
	}
	
	public String decodeString(String encodedString) throws DecoderException {
		int colonTokenIndex = encodedString.indexOf(COLON_TOKEN);
		
		if(colonTokenIndex == -1)
			throw new DecoderException();
		
		String extractedString = encodedString.substring(colonTokenIndex + 1);
		
		return extractedString;
	}
	
}
