package de.abiegel.ldap.query.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * disjunctive Operation
 * @author usiabiegel
 *
 */
public interface Or extends Operation {
	public static final String OR = "|";

	static Operation or(Token... children) {
		return new Or() {
			@Override
			public String asString() {
				return operation(OR);
			}

			@Override
			public List<Token> children() {
				return Arrays.asList(children);
			}
		};
	}
	
	static Operation or(Stream<Token> children) {
		return new Or() {
			@Override
			public String asString() {
				return operation(OR);
			}

			@Override
			public List<Token> children() {
				return childrenAsList(children);
			}
		};
	}
}
