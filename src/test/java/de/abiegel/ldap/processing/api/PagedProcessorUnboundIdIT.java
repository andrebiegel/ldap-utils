package de.abiegel.ldap.processing.api;

import static org.junit.Assert.assertEquals;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.zapodot.junit.ldap.EmbeddedLdapRule;
import org.zapodot.junit.ldap.EmbeddedLdapRuleBuilder;

import com.google.common.collect.Iterators;

import de.abiegel.ldap.query.api.Query;

public class PagedProcessorUnboundIdIT extends AbstractLdapTestUnit {

	Integer elementSize = 0;
	Integer amountOfPages = 0 ;
	
	@Rule
	public EmbeddedLdapRule embeddedLdapRule = EmbeddedLdapRuleBuilder
	        .newInstance()
	        .usingDomainDsn("dc=example,dc=com")
	        .importingLdifs("users-import.ldif")
	        .build();
	
	
	@Before
	public void initVars(){
		this.elementSize = 0 ;
		this.amountOfPages = 0;
	}

	/**
	 * Apache DS does not implement RFC 2696!! but Unbound does
	 * @throws Exception 
	 */
	@Test
	public void pagedAccessWorksOnUnboundId() throws Exception {
		
		 final LdapContext ctx =  embeddedLdapRule.ldapContext();

		int pageSize = 1;
		PagedProcessor processor = new PagedProcessor(ctx, pageSize);
		processor.process(this::pagedWork);
		Assertions.assertThat(amountOfPages).isEqualTo(3);
		Assertions.assertThat(elementSize).isEqualTo(3);
	}

	private void pagedWork(LdapContext ctx, Object[] args) throws NamingException {
		
	     final SearchControls searchControls = new SearchControls();
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        final NamingEnumeration<javax.naming.directory.SearchResult> resultNamingEnumeration =
	                ctx.search("dc=example,dc=com", Query.attr("objectClass", "person").asString(), searchControls);
	        
	        
	    	if (resultNamingEnumeration.hasMore()) {
				this.elementSize ++ ;
			}
	        //TODO: why does it work only with this line ? consumes enumeration .. hm 
	        assertEquals(1, Iterators.size(Iterators.forEnumeration(resultNamingEnumeration)));
	          
		
	
		this.amountOfPages ++ ;  
	}

}
