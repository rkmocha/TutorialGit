<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>com.geet</groupId>
	<artifactId>MavenLog4j</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>MavenLog4j</name>


	<dependencies>
		<!-- https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core -->
		<dependency>
			<groupId>org.apache.logging.log4j</groupId>
			<artifactId>log4j-core</artifactId>
			<!-- <version>2.16.0</version> -->
			<version>2.17.0</version>
		</dependency>
	</dependencies>
</project>


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class TestLog4j {

	private static Logger log = LogManager.getLogger(TestLog4j.class);

	public TestLog4j() {

		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
		builder.add(builder.newRootLogger());
		LoggerContext context = Configurator.initialize(builder.build());

		FileAppender fa = FileAppender.newBuilder().setName("FileAppender").withAppend(false)
				.withFileName(
						"F:/WS/Eclipse-WS/MavenLog4j/logs2/NewFile" + System.getProperty("current.date.time") + ".log")
				.setLayout(PatternLayout.newBuilder().withPattern("%d %-5p %c %t %m%n").build()).build();

		fa.start();
		context.getRootLogger().addAppender(fa);
		// context.getRootLogger().setLevel(Level.INFO);

		System.out.println(context.getRootLogger().getAppenders());
		Appender log4jConsole = context.getRootLogger().getAppenders().get("DefaultConsole-2");
		if (null != log4jConsole) {
			context.getRootLogger().removeAppender(log4jConsole);
		}

		// https://www.programcreek.com/java-api-examples/?api=org.apache.logging.log4j.core.appender.FileAppender
		// Example 11
		// https://stackoverflow.com/questions/23434252/programmatically-change-log-level-in-log4j2
		Configuration config = context.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
		loggerConfig.setLevel(Level.DEBUG);

		context.updateLoggers();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TestLog4j l = new TestLog4j();

		log.trace("Trace Message!");
		log.debug("Debug Message!");
		log.info("Info Message!");
		log.warn("Warn Message!");
		log.error("Error Message!");
		log.fatal("Fatal Message!");

		log.log(Level.INFO, "Test");

		MyClass c = new MyClass();
		c.hello();
	}

}
