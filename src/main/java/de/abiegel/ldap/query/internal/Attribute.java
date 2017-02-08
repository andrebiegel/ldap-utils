package de.abiegel.ldap.query.internal;

public interface Attribute extends Filter {

	String value();
	String key();
	
	default String op() {
	        return Filter.EQUAL;
	}
	
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