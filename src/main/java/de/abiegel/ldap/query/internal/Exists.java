package de.abiegel.ldap.query.internal;

public interface Exists extends Attribute {
 
	static Attribute exists(String key) {
		return new Attribute() {

			@Override
			public String value() {
				return "*";
			}

			@Override
			public String key() {
				return key;
			}
		};
	}

}
