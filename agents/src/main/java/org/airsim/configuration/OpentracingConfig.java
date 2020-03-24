package org.airsim.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentracing.contrib.spring.cloud.jdbc.JdbcTracingProperties;

@Configuration
public class OpentracingConfig {
	
	@Bean
	public JdbcTracingProperties jdbcTracingProperties() {
		JdbcTracingProperties properties = new JdbcTracingProperties();
		properties.getIgnoreStatements().add("update token_entry set timestamp=? where processor_name=? and segment=? and owner=?");
		properties.getIgnoreStatements().add("update token_entry set owner=null where owner=? and processor_name=? and segment=?");
		properties.getIgnoreStatements().add("update token_entry set owner=?, timestamp=?, token=?, token_type=? where processor_name=? and segment=?");
		properties.getIgnoreStatements().add("select tokenentry0_.processor_name as processo1_6_0_, tokenentry0_.segment as segment2_6_0_, tokenentry0_.owner as owner3_6_0_, tokenentry0_.timestamp as timestam4_6_0_, tokenentry0_.token as token5_6_0_, tokenentry0_.token_type as token_ty6_6_0_ from token_entry tokenentry0_ where tokenentry0_.processor_name=? and tokenentry0_.segment=? for update");
		properties.getIgnoreStatements().add("select tokenentry0_.processor_name as processo1_4_0_, tokenentry0_.segment as segment2_4_0_, tokenentry0_.owner as owner3_4_0_, tokenentry0_.timestamp as timestam4_4_0_, tokenentry0_.token as token5_4_0_, tokenentry0_.token_type as token_ty6_4_0_ from token_entry tokenentry0_ where tokenentry0_.processor_name=? and tokenentry0_.segment=? for update");

		return properties;
	}

}
