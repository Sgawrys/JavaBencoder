package com.sgawrys.bencoder.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
	
	private static final Pattern DECODE_PATTERN = Pattern.compile("([idel])|(\\d+):|(-?\\d+)");
	
	private Decoder() {
		
	}
	
	public static Decoder getInstance() {
		return DECODER;
	}
	
	/**
	 * Entry method into starting decoding, will return null if any errors
	 * are encountered.
	 * 
	 * @param bencodedString
	 * @return
	 */
	public Object decode(String bencodedString) {
		List<String> tokenList;
		try {
			tokenList = tokenize(bencodedString);
			return parse(tokenList.listIterator());
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that takes in a list iterator and returns objects based on the next
	 * value within the token list, for lists and dictionaries it will recursively call
	 * itself to create those data structures correctly.
	 * 
	 * @param it
	 * @return
	 * @throws DecoderException 
	 */
	private static Object parse(ListIterator<String> it) throws DecoderException {
		String currentToken;
		if(it.hasNext()) {
			switch(it.next()) {
				case BencodingToken.START_TOKEN:
					return Integer.parseInt(it.next());
				case BencodingToken.STRING_TOKEN:
					return it.next();
				case BencodingToken.START_LIST_TOKEN:
					List<Object> objList = new ArrayList<Object>();
					while(it.hasNext() && (currentToken = it.next()) != BencodingToken.END_TOKEN) {
						it.previous();
						objList.add(parse(it)); 
					}
					return objList;
				case BencodingToken.START_DICT_TOKEN:
					List<Object> objDictList = new ArrayList<Object>();
					while(it.hasNext() && (currentToken = it.next()) != BencodingToken.END_TOKEN) {
						it.previous();
						objDictList.add(parse(it)); 
					}
					Map<String, Object> objMap = new HashMap<String, Object>();
					ListIterator<Object> objListIterator = objDictList.listIterator();
					while(objListIterator.hasNext()) {
						String key = (String)objListIterator.next();
						if(objListIterator.hasNext()) {
							objMap.put(key, objListIterator.next());
						} else {
							throw new DecoderException();
						}
					}
					return objMap;
			}
		}
		return null;
	}
	
	/**
	 * Takes a bencoded string and converts contents into a list of tokens, when confronted with an
	 * integer is does not add the end token to the list and for strings it adds a starting string token
	 * to signify the next value in the list should be interpreted as a String. For lists and dictionaries
	 * it will add their respective start and end tokens to the list of tokens.
	 * 
	 * @param bencodedString
	 * @return
	 * @throws DecoderException
	 */
	private static List<String> tokenize(String bencodedString) throws DecoderException {
		List<String> tokenList = new ArrayList<String>();
		Matcher m = DECODE_PATTERN.matcher(bencodedString);
		while(m.find()) {
			String extractedToken = bencodedString.substring(m.start(), m.end());
			if(extractedToken.equals(BencodingToken.START_TOKEN)) {
				m.find();
				tokenList.add(BencodingToken.START_TOKEN);
				tokenList.add(bencodedString.substring(m.start(), m.end()));
				m.find();
				if(!bencodedString.substring(m.start(), m.end()).equals(BencodingToken.END_TOKEN)) {
					throw new DecoderException();
				}
			}
			
			if(extractedToken.contains(BencodingToken.COLON_TOKEN)) {
				int stringLength = Integer.parseInt(extractedToken.substring(0, extractedToken.length()-1));
				String stringToken = bencodedString.substring(m.end(), m.end() + stringLength);
				tokenList.add(BencodingToken.STRING_TOKEN);
				tokenList.add(stringToken);
				m = m.region(m.end()+stringLength, bencodedString.length());
			}
			
			if(extractedToken.equals(BencodingToken.START_LIST_TOKEN)) {
				tokenList.add(BencodingToken.START_LIST_TOKEN);
			}
			
			if(extractedToken.equals(BencodingToken.START_DICT_TOKEN)) {
				tokenList.add(BencodingToken.START_DICT_TOKEN);
			}
			
			if(extractedToken.equals(BencodingToken.END_TOKEN)) {
				tokenList.add(BencodingToken.END_TOKEN);
			}
			
		}
		return tokenList;
	}
}
