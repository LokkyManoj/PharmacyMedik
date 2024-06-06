package pharmacy.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pharmacy.model.PharmacyUserReg;
import pharmacy.util.PharmacyRegConnection;

public class PharmacyUserDAO {

    public void saveUser(PharmacyUserReg user) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO pharmacy_reg_user (name, mobile_no, email, password) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = PharmacyRegConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, String.valueOf(user.getMobileNo()));
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            
//            preparedStatement.executeUpdate();
            int rowsAffected = preparedStatement.executeUpdate();
			System.out.println("Rows affected: " + rowsAffected);
        }
    }
}

