package de.abiegel.ldap.query.internal;

import static de.abiegel.ldap.query.internal.Or.or;

import java.util.ArrayList;
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
				return new ArrayList<Token>(Arrays.asList(children));
			}
			
			@Override
			public Operation add(Token child) {
				List<Token> ref = this.children();
				ref.add(child);
				return or(ref.toArray(new Token[ref.size()]));
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

			@Override
			public Operation add(Token child) {
				List<Token> ref = this.children();
				ref.add(child);
				return or(ref.toArray(new Token[ref.size()]));
			}
		};
	}
}
