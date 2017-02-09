package de.abiegel.ldap.query.internal;

import static de.abiegel.ldap.query.internal.And.and;

import java.util.ArrayList;
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
				return new ArrayList<Token>(Arrays.asList(children));
			}
			
			@Override
			public Operation add(Token child) {
				List<Token> ref = this.children();
				ref.add(child);
				return and(ref.toArray(new Token[ref.size()]));
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
			
			@Override
			public Operation add(Token child) {
				List<Token> ref = this.children();
				ref.add(child);
				return and(ref.toArray(new Token[ref.size()]));
			}

		};
	}

}
