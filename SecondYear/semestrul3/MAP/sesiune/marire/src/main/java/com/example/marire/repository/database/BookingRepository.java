package com.example.marire.repository.database;

import com.example.marire.domain.Booking;
import com.example.marire.domain.Status;
import com.example.marire.domain.validators.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BookingRepository extends AbstractDBRepository<Integer, Booking> {

    public BookingRepository(Validator<Booking> validator) {
        super(validator);
    }

    @Override
    protected PreparedStatement findOneQuery(Integer integer) throws SQLException {
        String query = "SELECT * FROM bookings WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement findAllQuery() throws SQLException {
        String query = "SELECT * FROM bookings";
        return connection.prepareStatement(query);
    }

    @Override
    protected PreparedStatement saveQuery(Booking entity) throws SQLException {
        String query = "INSERT INTO bookings (licenseplatenumber, bookingtimeminutes, status, creationdate, bookingstartdate, bookingenddate, parkingslot) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getLicensePlateNumber());
        statement.setInt(2, entity.getBookingTimeMinutes());
        statement.setString(3, entity.getStatus().toString());
        statement.setTimestamp(4, Timestamp.valueOf(entity.getCreationDate()));
        statement.setTimestamp(5, Timestamp.valueOf(entity.getBookingStartDate()));
        statement.setTimestamp(6, Timestamp.valueOf(entity.getBookingEndDate()));
        statement.setInt(7, entity.getParkingSlot());
        return statement;
    }


    @Override
    protected PreparedStatement updateQuery(Booking entity) throws SQLException {
        String query = "UPDATE bookings SET status = ?, parkingslot = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, entity.getStatus().toString());
        statement.setInt(2, entity.getParkingSlot());
        statement.setInt(3, entity.getId());
        return statement;
    }

    @Override
    protected Booking buildEntity(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("id");
        String licensePlateNumber = resultSet.getString("licenseplatenumber");
        Integer bookingTimeMinutes = resultSet.getInt("bookingtimeminutes");
        Status status = Status.valueOf(resultSet.getString("status"));
        LocalDateTime creationDate = resultSet.getTimestamp("creationdate").toLocalDateTime();
        LocalDateTime bookingStartDate = resultSet.getTimestamp("bookingstartdate").toLocalDateTime();
        LocalDateTime bookingEndDate = resultSet.getTimestamp("bookingenddate").toLocalDateTime();
        Integer parkingSlot = resultSet.getInt("parkingslot");

        Booking booking = new Booking(licensePlateNumber, bookingTimeMinutes, status, creationDate, bookingStartDate, bookingEndDate, parkingSlot);
        booking.setId(id);
        return booking;

    }
}
