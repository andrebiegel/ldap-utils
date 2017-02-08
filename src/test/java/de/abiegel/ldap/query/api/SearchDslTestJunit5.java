package de.abiegel.ldap.query.api;

import static de.abiegel.ldap.query.api.Query.and;
import static de.abiegel.ldap.query.api.Query.attr;
import static de.abiegel.ldap.query.api.Query.not;
import static de.abiegel.ldap.query.api.Query.or;
import static de.abiegel.ldap.query.api.Query.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class SearchDslTestJunit5 {

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.api.Query#query(de.abiegel.ldap.query.internal.Token)}
	 * .
	 */
	@Test
	public void testQuery() {
		Assertions.assertAll("simple query",
				() -> Assertions.assertEquals("(test=value)", query(attr("test", "value")).asString()));

	}

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.internal.And#and(de.abiegel.ldap.query.internal.Token[])}
	 * .
	 */
	@Test
	public void testAnd() {
		Assertions.assertAll("And", () -> {
			Assertions.assertEquals("(&(test=value)(test2=value2))",
					query(and(attr("test", "value"), attr("test2", "value2"))).asString());
			Assertions.assertEquals("(&(test=value)(&(test=value)(test2=value2))(test2=value2))",
					query(and(attr("test", "value"), attr("test2", "value2"),
							and(attr("test", "value"), attr("test2", "value2")))).asString());
		});

	}

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.internal.Or#or(de.abiegel.ldap.query.internal.Token[])}
	 * .
	 */
	@Test
	public void testOr() {
		Assertions.assertEquals("(|(test=value)(test2=value2))",
				query(or(attr("test", "value"), attr("test2", "value2"))).asString());
	}

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.internal.Not#not(de.abiegel.ldap.query.internal.Token[])}
	 * .
	 */
	@Test
	public void testNot() {
		Assertions.assertEquals("(!(test=value))",
				query(not(attr("test" , "value"))).asString());
	}

	@Test
	// implicitly uses TestInfoParameterResolver to provide testInfo
	void testWithBuiltIntParameterResolver(TestInfo testInfo) {
		// ...
	}

}
