package com.sgawrys.bencoder.application;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.sgawrys.bencoder.tokens.BencodingToken;

/**
 * 
 * Singleton class for bencoding strings most commonly used in torrent files.
 * 
 * @author Stefan Gawrys
 *
 */
public class Encoder {

	private static final Encoder ENCODER = new Encoder();
	
	private Encoder() {
		
	}
	
	public static Encoder getInstance() {
		return ENCODER;
	}
	
	/**
	 * Encodes the given String ("x" -> "1:x")
	 * @param encodeString
	 * @return
	 */
	public String encode(String encodeString) {
		return encodeString.length() + BencodingToken.COLON_TOKEN + encodeString;
	}
	
	/**
	 * Encodes the given integer ( 4 -> "i4e")
	 * @param encodeInt
	 * @return
	 */
	public String encodeInteger(Integer encodeInt) {
		return BencodingToken.START_TOKEN + encodeInt + BencodingToken.END_TOKEN;
	}
	
	/**
	 * Encodes each element in the list sequentially and returns the bencoded string.
	 * 
	 * @param encodeList
	 * @return
	 */
	public String encodeList(List<?> encodeList) {
		StringBuilder sb = new StringBuilder();
		sb.append(BencodingToken.START_LIST_TOKEN);
		for(Object entry : encodeList) {
			sb.append(determineEncoding(entry));
		}
		sb.append(BencodingToken.END_TOKEN);
		return sb.toString();
	}
	
	/**
	 * Encodes each key/value pair in the specified map and determines the type
	 * of the respective key/value pair objects for their own Bencoding methods.
	 * 
	 * @param encodeDictionary
	 * @return
	 */
	public String encodeDictionary(Map<?,?> encodeDictionary) {
		StringBuilder sb = new StringBuilder();
		sb.append(BencodingToken.START_DICT_TOKEN);
		for(Entry<?,?> entry : encodeDictionary.entrySet()) {
			Object entryKey = entry.getKey();
			Object entryValue = entry.getValue();
			
			sb.append(determineEncoding(entryKey));
			sb.append(determineEncoding(entryValue));
		}
		sb.append(BencodingToken.END_TOKEN);
		return sb.toString();
	}
	
	
	private String determineEncoding(Object obj) {
		
		if(obj instanceof String) {
			return encode((String)obj);
		}
		
		if(obj instanceof Integer) {
			return encodeInteger((Integer)obj);
		}
		
		if(obj instanceof List<?>) {
			return encodeList((List<?>)obj);
		}
		
		if(obj instanceof Map<?,?>) {
			return encodeDictionary((Map<?,?>)obj);
		}
		
		return "";
	}
	
}
