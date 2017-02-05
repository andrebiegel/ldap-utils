package de.abiegel.ldap.query.internal;

public interface Attribute extends Token {
    default String asString() {
        return "(" +key() + "=" + value() + ")";
    }
    
	String value();
	String key();

	static Attribute attr(String key, String value) {
		return new Attribute() {

			@Override
			public String value() {
				return value;
			}

			@Override
			public String key() {
				return key;
			}
		};
	}
}