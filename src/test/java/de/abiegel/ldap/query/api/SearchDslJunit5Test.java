package de.abiegel.ldap.query.api;

import static de.abiegel.ldap.query.api.Query.and;
import static de.abiegel.ldap.query.api.Query.attr;
import static de.abiegel.ldap.query.api.Query.not;
import static de.abiegel.ldap.query.api.Query.or;
import static de.abiegel.ldap.query.api.Query.query;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class SearchDslJunit5Test {

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.api.Query#query(de.abiegel.ldap.query.internal.Token)}
	 * .
	 */
	@Test
	@DisplayName("Testing Dsl access Class")
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
	@DisplayName("conjunction test")
	public void testAnd() {
		Assertions.assertAll("And", () -> {
			Assertions.assertEquals("(&(test=value)(test2=value2))",
					query(and(attr("test", "value"), attr("test2", "value2"))).asString());
			Assertions.assertEquals("(&(test=value)(test2=value2)(&(test=value)(test2=value2)))",
					query(and(attr("test", "value"), attr("test2", "value2"),
							and(attr("test", "value"), attr("test2", "value2")))).asString());
			Assertions.assertEquals("(&(test=value)(test2=value2)(&(test=value)(test2=value2)))",
					query(and(attr("test", "value"), attr("test2", "value2"),
							and(attr("test", "value")).add(attr("test2", "value2")))).asString());
		});

	}

	/**
	 * Test method for
	 * {@link de.abiegel.ldap.query.internal.Or#or(de.abiegel.ldap.query.internal.Token[])}
	 * .
	 */
	@Test
	@DisplayName("disjunction test")
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
	@DisplayName("negation test")
	public void testNot() {
		Assertions.assertEquals("(!(test=value))", query(not(attr("test", "value"))).asString());
		Assertions.assertThrows(UnsupportedOperationException.class,
				() -> not(attr("test", "value")).add(attr("test", "value2")));
	}

	@Test
	// implicitly uses TestInfoParameterResolver to provide testInfo
	void testWithBuiltIntParameterResolver(TestInfo testInfo) {
		// ...
	}

}
