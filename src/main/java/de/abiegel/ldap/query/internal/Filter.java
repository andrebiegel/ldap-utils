package de.abiegel.ldap.query.internal;

public interface Filter extends Token {
	static String EQUAL = "=";
	static String GREATER = ">";
	static String LESS = "<";
	static String GREATER_OR_EQUAL = ">=";
	static String LESSER_OR_EQUAL = "<=";
	static String SIMILAR = "=~";
	
    default String asString() {
        return "(" +key() + op() + value() + ")";
    }
    
    String op();
	String value();
	String key();

	static Filter filter(String key,String op, String value) {
		return new Filter() {

			@Override
			public String value() {
				return value;
			}

			@Override
			public String key() {
				return key;
			}

			@Override
			public String op() {
				return op;
			}
		};
	}
}