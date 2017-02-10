# ldap-utils

## Search Query DSL
The Query dsl is a nested Java 8 dsl to construct ldap search filter queries. Some might think , yet another ldap lib.. of course Spring also offers a (fluent) dsl to achieve this, but i wanted to see how it is to write one, so i wrote this one.. use it or not, if let me know (would be nice)...  
### Usage

Starting point is the Query class allowing to access factory methods of every kind of Tokens, which might be query operators or filters (without filter operators, an Attribute filter filters upon equality) 

disjunction:

		Assertions.assertEquals("(|(test=value)(test2=value2))",
				query(or(attr("test", "value"), attr("test2", "value2"))).asString());


conjunction


		Assertions.assertAll("And", () -> {
			Assertions.assertEquals("(&(test=value)(test2=value2))",
					query(and(attr("test", "value"), attr("test2", "value2"))).asString());
			Assertions.assertEquals("(&(test=value)(&(test=value)(test2=value2))(test2=value2))",
					query(and(attr("test", "value"), attr("test2", "value2"),
							and(attr("test", "value"), attr("test2", "value2")))).asString());
		});

negation:

		Assertions.assertEquals("(!(test=value))",
				query(not(attr("test" , "value"))).asString());

## Paged Access Helper


## Modification Helper