package io.pivotal.pal.tracker;


import org.springframework.jdbc.core.JdbcTemplate;


import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry any) {

        final String sql = "INSERT INTO `time_entries` (`project_id`, `user_id`, `date`, `hours`) VALUES (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(sql, new String[] {"id"});
                        ps.setLong(1, any.getProjectId());
                        ps.setLong(2, any.getUserId());
                        ps.setDate(3, java.sql.Date.valueOf(any.getDate()));
                        ps.setInt(4, any.getHours());
                        return ps;
                    }
                },
                keyHolder);

        any.setId(keyHolder.getKey().longValue());
        return any;
    }

    @Override
    public TimeEntry find(long timeEntryId) {

        final String sql = "SELECT * FROM `time_entries` WHERE id = ?;";

        return jdbcTemplate.query(sql, new Object[]{timeEntryId}, extractor);
    }

    @Override
    public List<TimeEntry> list() {
        final String sql = "SELECT * FROM `time_entries`;";

        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        final String sql = "UPDATE `time_entries` SET `project_id` = ?, `user_id` = ?, `date` = ?, `hours` = ? WHERE id = ?;";

        jdbcTemplate.update(sql, any.getProjectId(), any.getUserId(), any.getDate(), any.getHours(), eq);

        any.setId(eq);
        return any;
    }

    @Override
    public void delete(long timeEntryId) {
        final String sql = "DELETE FROM `time_entries` WHERE id = ?;";

        jdbcTemplate.update(sql, timeEntryId);

    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

}
