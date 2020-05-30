package com;

import java.sql.*;

public class payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readPayment() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Payment No</th><th>Appointment ID</th><th>Amount</th>"
					+ "<th>Patient Name</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
			while (rs.next()) {
				String PaymentID = Integer.toString(rs.getInt("PaymentID"));
				String PaymentNo = rs.getString("PaymentNo");
				String AppointmentID = rs.getString("AppointmentID");
				String Amount = Double.toString(rs.getDouble("Amount"));
				String PatientName = rs.getString("PatientName");
// Add into the html table
				output += "<tr><td><input id='hidPayIDUpdate' name='hidPayIDUpdate' type='hidden' value='" + PaymentID + "'>" + PaymentNo + "</td>"; 
				output += "<td>" + AppointmentID + "</td>";
				output += "<td>" + Amount + "</td>";
				output += "<td>" + PatientName + "</td>";
// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger' data-payid='" + PaymentID + "'>" + "</td></tr>";
			}
			con.close();
// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertPayment(String payno, String appointno, String amount, String patientname) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
// create a prepared statement
			String query = " insert into payment(PaymentID, PaymentNo, AppointmentID, Amount, PatientName)" + " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, payno);
			preparedStmt.setString(3, appointno);
			preparedStmt.setDouble(4, Double.parseDouble(amount));
			preparedStmt.setString(5, patientname);
// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String ID, String payno, String appointno, String amount, String patientname) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
// create a prepared statement
			String query = "UPDATE payment SET PaymentNo=?,AppointmentID=?,Amount=?,PatientName=? WHERE PaymentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
			preparedStmt.setString(1, payno);
			preparedStmt.setString(2, appointno);
			preparedStmt.setDouble(3, Double.parseDouble(amount));
			preparedStmt.setString(4, patientname);
			preparedStmt.setInt(5, Integer.parseInt(ID));
// execute the statement
			preparedStmt.execute();
			con.close();
			String newpay = readPayment();
			output = "{\"status\":\"success\", \"data\": \"" + newpay + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

public String deleteItem(String payid)
{
String output = "";
try
{
Connection con = connect();
if (con == null)
{
return "Error while connecting to the database for deleting.";
}

// create a prepared statement
String query = "delete from payment where PaymentID=?";
PreparedStatement preparedStmt = con.prepareStatement(query);
// binding values
preparedStmt.setInt(1, Integer.parseInt(payid));
// execute the statement
preparedStmt.execute();
con.close();
String newItems = readPayment();
output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}";
}
catch (Exception e)
{
output = "{\"status\":\"error\", \"data\": \"Error while deleting the item.\"}";
System.err.println(e.getMessage());
}
return output;
}
}