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
	
	private static final Pattern DECODE_PATTERN = Pattern.compile("([idel])|(\\d+):|(-?\\d+)");
	
	private Decoder() {
		
	}
	
	public static Decoder getInstance() {
		return DECODER;
	}
	
	public Object decode(String bencodedString) {
		List<String> tokenList;
		try {
			tokenList = tokenize(bencodedString);
			System.out.println("PRINTING TOKEN LIST");
			for(int i = 0; i < tokenList.size(); i++) {
				switch(tokenList.get(i)) {
					case BencodingToken.START_TOKEN:
						System.out.println("INT TOKEN : " + Integer.parseInt(tokenList.get(i+1)));
						i++;
						break;
					case BencodingToken.STRING_TOKEN:
						System.out.println("STRING TOKEN : "+ tokenList.get(i+1));
						i++;
						break;
					case BencodingToken.START_LIST_TOKEN:
						break;
					case BencodingToken.START_DICT_TOKEN:
						break;
						
				}
			}
			System.out.println("ENDING TOKEN LIST");
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
