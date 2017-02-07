package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
/**
 * Conjunction
 * @author usiabiegel
 *
 */
public interface And extends Operation {
	public static final String AND = "&";

	static And and(Token... children) {
		return new And() {
			@Override
			public String asString() {
				return operation(AND);
			}

			@Override
			public List<Token> children() {
				return Arrays.asList(children);
			}
		};
	}
	static And and(Stream<Token> children) {
		return new And() {
			@Override
			public String asString() {
				return operation(AND);
			}

			@Override
			public List<Token> children() {
				return childrenAsList(children);
			}
		};
	}

}
