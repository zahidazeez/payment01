/**
 * 
 */
$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	var type = ($("#hidPayIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "paymentAPI",
		type : type,
		data : $("#formPay").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPaymentSaveComplete(response.responseText, status);
		}
	});

});

function onPaymentSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidPayIDSave").val("");
	$("#formPay")[0].reset();
}

// DELET implementation
$(document).on("click", ".btnRemove", function(event) {

	$.ajax({
		url : "paymentAPI",
		type : "DELETE",
		data : "PaymentID=" + $(this).data("payid"),
		dataType : "text",
		complete : function(response, status) {
			onpaymentDeleteComplete(response.responseText, status);
		}
	});

});

function onpaymentDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPaymentGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// UPDATE==========================================
$(document).on("click",".btnUpdate",function(event) {
					$("#hidPayIDSave").val(
							$(this).closest("tr").find('#hidPayIDUpdate')
									.val());
					$("#Payno").val(
							$(this).closest("tr").find('td:eq(0)').text());
					$("#AppointID").val(
							$(this).closest("tr").find('td:eq(1)').text());
					$("#Amount").val(
							$(this).closest("tr").find('td:eq(2)').text());
					$("#PatientName").val(
							$(this).closest("tr").find('td:eq(3)').text());
					
				});

// CLIENTMODEL=========================================================================
function validateItemForm() {
	// Patient ID
	if ($("#Payno").val().trim() == "") {
		return "Insert Patient ID.";
	}
	// Doctor ID
	if ($("#AppointID").val().trim() == "") {
		return "Insert Doctor ID.";
		
		// check the amount, is numerical value
		if ($("#Amount").val().trim() == "") {
			return "Insert Payment Amount.";
		}

		
		
	}
	// Date-------------------------------
	if ($("#Amount").val().trim() == "") {
		return "Insert Date.";
	}
	
	return true;
}
