package ch.obermuhlner.ammonites.config

import org.jooq.ConnectionProvider
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.impl.DataSourceConnectionProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import java.sql.DriverManager
import javax.sql.DataSource


@Configuration
class JooqConfiguration {
    @Bean
    fun dataSource(): DataSource {
        val connection = DriverManager.getConnection("jdbc:h2:file:~/ammonites-db;DB_CLOSE_DELAY=-1", "sa", "")
        return SingleConnectionDataSource(connection, true)
    }

    @Bean
    fun dslContext(dataSource: DataSource?): DSLContext {
        val connectionProvider: ConnectionProvider = DataSourceConnectionProvider(dataSource)
        return DSL.using(connectionProvider.acquire())
    }
}
