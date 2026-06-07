package com.generated.ldlibseat.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.generated.ldlibseat.model.ViolationRecord;
import com.generated.ldlibseat.model.ViolationUserStats;

@Repository
public class ViolationRecordRepository {
  private final JdbcTemplate jdbcTemplate;

  public ViolationRecordRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<ViolationRecord> findByDateRange(LocalDate startDate, LocalDate endDate, int offset, int limit, String sortBy, String sortDirection) {
    StringBuilder sql = new StringBuilder(
      "SELECT v.id, v.user_id, u.real_name as user_name, v.reservation_id, v.seat_id, " +
      "v.seat_code, v.violation_type, v.violation_time, v.reservation_date, " +
      "v.start_time, v.end_time, v.description, v.handled, v.created_at " +
      "FROM violation_records v " +
      "LEFT JOIN users u ON v.user_id = u.id " +
      "WHERE 1=1"
    );

    if (startDate != null) {
      sql.append(" AND v.reservation_date >= '").append(startDate.toString()).append("'");
    }
    if (endDate != null) {
      sql.append(" AND v.reservation_date <= '").append(endDate.toString()).append("'");
    }

    String orderColumn = switch (sortBy) {
      case "userName" -> "u.real_name";
      case "seatCode" -> "v.seat_code";
      case "violationTime" -> "v.violation_time";
      case "reservationDate" -> "v.reservation_date";
      default -> "v.violation_time";
    };

    sql.append(" ORDER BY ").append(orderColumn).append(" ").append("ASC".equalsIgnoreCase(sortDirection) ? "ASC" : "DESC");
    sql.append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);

    return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
      ViolationRecord record = new ViolationRecord();
      record.setId(rs.getLong("id"));
      record.setUserId(rs.getLong("user_id"));
      record.setUserName(rs.getString("user_name"));
      record.setReservationId(rs.getLong("reservation_id"));
      record.setSeatId(rs.getLong("seat_id"));
      record.setSeatCode(rs.getString("seat_code"));
      record.setViolationType(rs.getString("violation_type"));
      record.setViolationTime(rs.getTimestamp("violation_time").toLocalDateTime());
      record.setReservationDate(rs.getDate("reservation_date").toLocalDate());
      record.setStartTime(rs.getTime("start_time").toLocalTime());
      record.setEndTime(rs.getTime("end_time").toLocalTime());
      record.setDescription(rs.getString("description"));
      record.setHandled(rs.getBoolean("handled"));
      record.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
      return record;
    });
  }

  public long countByDateRange(LocalDate startDate, LocalDate endDate) {
    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM violation_records v WHERE 1=1");
    if (startDate != null) {
      sql.append(" AND v.reservation_date >= '").append(startDate.toString()).append("'");
    }
    if (endDate != null) {
      sql.append(" AND v.reservation_date <= '").append(endDate.toString()).append("'");
    }
    return jdbcTemplate.queryForObject(sql.toString(), Long.class);
  }

  public List<ViolationUserStats> findUserViolationStats(LocalDate startDate, LocalDate endDate) {
    StringBuilder sql = new StringBuilder(
      "SELECT v.user_id, u.real_name as user_name, COUNT(*) as violation_count " +
      "FROM violation_records v " +
      "LEFT JOIN users u ON v.user_id = u.id " +
      "WHERE 1=1"
    );

    if (startDate != null) {
      sql.append(" AND v.reservation_date >= '").append(startDate.toString()).append("'");
    }
    if (endDate != null) {
      sql.append(" AND v.reservation_date <= '").append(endDate.toString()).append("'");
    }

    sql.append(" GROUP BY v.user_id, u.real_name ORDER BY violation_count DESC");

    List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString());
    return rows.stream().map(row -> new ViolationUserStats(
        ((Number) row.get("user_id")).longValue(),
        (String) row.get("user_name"),
        ((Number) row.get("violation_count")).intValue()
    )).collect(Collectors.toList());
  }

  public int updateHandled(Long id, boolean handled) {
    return jdbcTemplate.update(
      "UPDATE violation_records SET handled = ? WHERE id = ?",
      handled, id
    );
  }
}
