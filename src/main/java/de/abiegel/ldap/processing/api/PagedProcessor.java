package de.abiegel.ldap.processing.api;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.naming.ldap.Control;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

/**
 * paginiertes abarbeiten von ldap queries
 * 
 * @author usiabiegel
 *
 */
public class PagedProcessor {

	private LdapContext ctx;
	private int pageSize;
	private Object[] params;
	private final Logger logger = Logger.getLogger(PagedProcessor.class.getName());

	public PagedProcessor(LdapContext ctx, int pageSize, Object... params) {
		this.ctx = ctx;
		this.pageSize = pageSize;
		this.params = params;
	}

	public void process(CheckedBiConsumer<LdapContext, Object[]> consumer) {
		try {
			long timeInMillsStart = new Date().getTime();
			logger.info("***************** START-OF-TASK " + " ) *****************");
			// Activate paged results
			int pageSize = this.pageSize;
			byte[] cookie = null;
			this.ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });
			int total;
			int alreadyProcessed = 0;
			do {
				/* execute */
				long timeInMillsPage = new Date().getTime();
				consumer.accept(ctx, this.params);
				alreadyProcessed += pageSize;
				// Examine the paged results control response
				Control[] controls = ctx.getResponseControls();
				if (controls != null) {
					for (int i = 0; i < controls.length; i++) {
						if (controls[i] instanceof PagedResultsResponseControl) {
							PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
							total = prrc.getResultSize();
							long timeInMills2 = new Date().getTime();
							long time = timeInMills2 - timeInMillsPage;

							if (total != 0) {

								logger.fine("***************** END-OF-PAGE " + "(already processed (interpolated): "
										+ alreadyProcessed + " , total : " + total + " , duration (MS): " + time
										+ " ) *****************");
							} else {
								logger.fine("***************** END-OF-PAGE " + "(already processed (interpolated): "
										+ alreadyProcessed + " , duration (MS): " + time
										+ ", total: unknown) ***************");
							}
							cookie = prrc.getCookie();
						}
					}
				} else {
					logger.info("No controls were sent from the server");
				}
				// Re-activate paged results
				ctx.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });

			} while (cookie != null);

			long timeInMillsEnd = new Date().getTime();
			long time = timeInMillsEnd - timeInMillsStart;

			logger.info("***************** END-OF-TASK " + "(already processed (interpolated): " + alreadyProcessed
					+ " , duration (MS): " + time + " ) *****************");

		} catch (NamingException e) {
			logger.log(Level.SEVERE, "PagedProcessor: Fehler", e);
		} catch (IOException ie) {
			logger.log(Level.SEVERE, "PagedProcessor: Fehler", ie);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "PagedProcessor: Fehler", e);
		}
	}

}
