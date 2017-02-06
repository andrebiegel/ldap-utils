package de.abiegel.ldap.processing.api;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import org.apache.directory.server.annotations.CreateLdapServer;
import org.apache.directory.server.annotations.CreateTransport;
import org.apache.directory.server.core.annotations.ApplyLdifFiles;
import org.apache.directory.server.core.annotations.CreateDS;
import org.apache.directory.server.core.annotations.CreatePartition;
import org.apache.directory.server.core.integ.AbstractLdapTestUnit;
import org.apache.directory.server.core.integ.FrameworkRunner;
import org.apache.directory.server.integ.ServerIntegrationUtils;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.abiegel.ldap.query.api.Query;

@RunWith(FrameworkRunner.class)
@CreateLdapServer(transports = { @CreateTransport(protocol = "LDAP") })
@CreateDS(allowAnonAccess = true, partitions = {
		@CreatePartition(name = "Example Partition", suffix = "dc=example,dc=com") })
@ApplyLdifFiles("users-import.ldif")
public class LdapAccessApacheDsIT extends AbstractLdapTestUnit {

	Integer elementSize = 0;
	Integer amountOfPages = 0 ;
	
	@Before
	public void initVars(){
		this.elementSize = 0 ;
		this.amountOfPages = 0;
	}

	/**
	 * Apache DS does not implement RFC 2696!!
	 * @throws Exception 
	 */
	@Test
	public void pagedAccessWorksNotOnApacheDs() throws Exception {
		LdapContext ctx = (LdapContext) ServerIntegrationUtils.getWiredContext(ldapServer, null);

		int pageSize = 1;
		PagedProcessor processor = new PagedProcessor(ctx, pageSize);
		processor.process(this::pagedWork);
		Assertions.assertThat(amountOfPages).isEqualTo(1);
		Assertions.assertThat(elementSize).isEqualTo(0);
	}

	private void pagedWork(LdapContext ctx, Object[] args) throws NamingException {
		NamingEnumeration<SearchResult> res = ctx.search("", Query.attr("objectClass", "person").asString(),
				new SearchControls());
		if (res.hasMore()) {
			this.elementSize ++ ;
		}
		this.amountOfPages ++ ;  
	}

	@Test
	public void shouldFindAllPersons() throws Exception {
		LdapContext ctx = (LdapContext) ServerIntegrationUtils.getWiredContext(ldapServer, null)
				.lookup("ou=Users,dc=example,dc=com");

		// we want a sorted result, based on the canonical name
		ctx.setRequestControls(new Control[] { new SortControl("cn", Control.CRITICAL) });

		NamingEnumeration<SearchResult> res = ctx.search("", Query.attr("objectClass", "person").asString(),
				new SearchControls());

		Assertions.assertThat(res.hasMore()).isEqualTo(Boolean.TRUE);
		Assertions.assertThat(res.next().getName()).isEqualToIgnoringCase("cn=John Steinbeck");
		Assertions.assertThat(res.next().getName()).isEqualToIgnoringCase("cn=Micha Kops");
		Assertions.assertThat(res.next().getName()).isEqualToIgnoringCase("cn=Santa Claus");

	}
}
