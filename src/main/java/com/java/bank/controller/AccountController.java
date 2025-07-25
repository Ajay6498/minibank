package com.java.bank.controller;

import java.awt.Color;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.bank.entity.Account;
import com.java.bank.entity.Transactions;
import com.java.bank.repo.AccountRepo;
//import com.java.bank.entity.Transactions;
import com.java.bank.service.AccountServiceImpl;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
 

@Controller
public class AccountController {

	static {
		System.out.println("Controller Class");
	}

	@Autowired
	private AccountServiceImpl asi;

	@Autowired
	private AccountRepo accountRepo;

	@GetMapping("/")
	public String WellComePage() {

		return "wellcome";
	}

	@GetMapping("/admin")
	public String Admin() {

		return "admin";
	}

	@GetMapping("/user")
	public String User() {

		return "user";
	}

	@GetMapping("/userlogin")
	public String UserLogIn(Model model) {
		model.addAttribute("userlogin", new com.java.bank.entity.UserLogIn());
		return "login";
	}

	@PostMapping("/checklogin")
	public String CheckLogIn(HttpSession session, Model model, @RequestParam String username,
			@RequestParam String password) {

		Account account = accountRepo.findByUserName(username);

		if (account != null) {
			if (account.getPassword().equals(password)) {

				// ✅ Set account number in session
				session.setAttribute("loggedInAccountNo", account.getAnumber());

				List<Transactions> transactions = asi.getTransactionsByAccountNo(account.getAnumber());
				model.addAttribute("transaction", transactions);

				String usermsg = "Hi " + account.getName() + "...!";
				String balance = "Your Current Balance: " + account.getBalance();
				String anumber = "Account Number : " + account.getAnumber();

				model.addAttribute("usermsg", usermsg);
				model.addAttribute("balance", balance);
				model.addAttribute("anumber", anumber);

				return "userdashbord";

			} else {
				model.addAttribute("usermsg", "Wrong Password");
				return "login";
			}
		} else {
			model.addAttribute("msg", "Enter valid username or password");
			return "login";
		}
	}

	// ****************************************************************************************

	@GetMapping("/adminlogin")
	public String AdminLogIn(Model model) {
		model.addAttribute("userlogin", new com.java.bank.entity.UserLogIn());
		return "adminlogin";
	}

	@PostMapping("/checkadminlogin")
	public String checkAdminLogin(HttpServletRequest request, Model model, @RequestParam String username,
			@RequestParam String password) {
		String uname = "admin@yes";
		String pass = "admin@123";

		if (uname.equals(username) && pass.equals(password)) {
			// Set session attribute for logged-in admin
			request.getSession().setAttribute("adminLoggedIn", true);

			return "admin"; // Redirect to admin dashboard
		} else {
			model.addAttribute("error", "Invalid username or password.");
			return "login"; // Return to login page
		}
	}

	// ****************************************************************************************

	@GetMapping("/create")
	public String CreateForm(Model model) {
		model.addAttribute("create", new Account());
		return "create";
	}

	@PostMapping("/save")
	public String createAccount(Model model, Account account, BindingResult result) {
		asi.createAccount(account);
		// model.addAttribute("msg", "created");
		return "login";
	}

	// ***************************************************************************************

	@GetMapping("/getbyid")
	public String getIdForm() {

		return "getbyaccount";
	}

	@GetMapping("/account")
	public String Search(@RequestParam Long anumber, Model model) {

		Account account = asi.getByAccountNo(anumber);
		System.out.println(account);

		model.addAttribute("account", account);
		return "showaccount";
	}

	// ****************************************************************************************

	@GetMapping("/accounts")
	public String getAllAccounts(Model model) {
		List<Account> accounts = asi.getAllAccount();
		model.addAttribute("account", accounts);
		return "showaccounts";
	}

	// ****************************************************************************************

	@GetMapping("/dp")
	public String depositForm() {

		return "depositform";
	}

	@PostMapping("/deposit")
	public String deposit(@RequestParam Long anumber, @RequestParam double amount, Model model) {
		String result = asi.deposit(anumber, amount);
		model.addAttribute("depositResult", result);

		if (result.contains("successful")) {
			model.addAttribute("msg", "Deposite Success");
			return "depositform"; // View for successful deposit
		} else {
			model.addAttribute("msg", "Deposit Error");
			return "depositform"; // View for failed deposit
		}
	}

	// ****************************************************************************************

	// This service is use in Bank and ATM only
	@PostMapping("/withdraw")
	public String withdraw(@RequestParam Long anumber, @RequestParam double amount) {
		String result = asi.withdraw(anumber, amount);

		if (result.contains("successful")) {
			return result;
		} else {
			return result;
		}
	}

	// ****************************************************************************************

	@GetMapping("/transferform")
	public String TransferForm() {

		return "transferfrom";
	}

//	@PostMapping("/transfer")
//	public String transferAmount(@RequestParam Long sourceAccountNo, @RequestParam Long targetAccountNo,
//			@RequestParam double amount,Model model) {
//
//		String result = asi.transfer(sourceAccountNo, targetAccountNo, amount);
//         model.addAttribute("message", result);
//         
// 		if (result.contains("successful")) {
//			return "transferfrom";
//		} else {
//			return "transferfrom";
//		}
//	}

	// ***********************

	@PostMapping("/transfer")
	public String transferAmount(HttpSession session, @RequestParam Long targetAccountNo, @RequestParam double amount,
			Model model) {

		// Get the source account number from session (assuming user is logged in and
		// stored in session)
		Long sourceAccountNo = (Long) session.getAttribute("loggedInAccountNo");

		// Handle case where session does not contain source account info
		if (sourceAccountNo == null) {
			model.addAttribute("message", "Session expired or user not logged in.");
			return "login";
		}

		// Perform the transfer
		String result = asi.transfer(sourceAccountNo, targetAccountNo, amount);
		model.addAttribute("message", result);

		return "transferfrom"; // same view regardless of success/failure
	}

	// ****************************************************************************************

	@GetMapping("/removeform")
	public String RemoveAccount() {

		return "removeuser";
	}

	@GetMapping("/removed")
	public String RemoveAccount(Model model, Long anumber) {

		asi.RemoveAccount(anumber);
		model.addAttribute("remove", "Removed Success");

		return "msg";
	}

	// ****************************************************************************************

	@GetMapping("/balance/{anumber}")
	public Double getBalance(@PathVariable Long anumber) {
		return asi.getBalance(anumber);
	}

	// ****************************************************************************************

	@GetMapping("/transactions/{anumber}")
	public String getTransactionsByAccountNo(Long anumber, Model model) {

		List<Transactions> transactions = asi.getTransactionsByAccountNo(anumber);
		model.addAttribute("transaction", transactions);

		return "transactions";
	}

	@GetMapping("/transactionss")
	public String getAllTransactions(Model model) {

		List<Transactions> transactions = asi.getAllTransaction();
		model.addAttribute("transaction", transactions);

		return "transaction";
	}

	// ******************************************************************************************************

	@GetMapping("/client")
	public String client() {
		return "client";
	}

	// *********************************************************************************************************

	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}

	// *****************************************************************

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		// Invalidate session
		HttpSession session = request.getSession(false); // avoid creating a new session
		if (session != null) {
			session.invalidate();
		}

		// Optional: Clear any cookies or tokens

		// Redirect to login page
		return "wellcome";
	}
	
	//******************************************************************************
	
	@GetMapping("/download-statement")
	public void downloadStatement(HttpServletResponse response, HttpSession session) throws IOException, DocumentException {
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=bank-statement.pdf");

	    Long sourceAccountNo = (Long) session.getAttribute("loggedInAccountNo");

	    List<Transactions> transactions = asi.getTransactionsByAccountNo(sourceAccountNo);

	    // ✅ Fetch actual account from database
	    Account account = asi.getByAccountNo(sourceAccountNo);

	    Document document = new Document(PageSize.A4);
	    try {
			PdfWriter.getInstance(document, response.getOutputStream());
		} catch (DocumentException e) {
 			e.printStackTrace();
		} catch (java.io.IOException e) {
			 
			e.printStackTrace();
		}

	    document.open();

	    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
	    Paragraph title = new Paragraph("Bank Statement", titleFont);
	    title.setAlignment(Element.ALIGN_CENTER);
	    document.add(title);
	    document.add(new Paragraph(" ")); // spacer

	    // Account Info Section 
	    Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
	    Paragraph accountInfo = new Paragraph(
	        "Account Number : " + account.getAnumber() + "\n" +
	        "Account Holder : " + account.getName() + "\n" +
	        "Username       : " + account.getUsername() + "\n" +
	        "Address        : " + account.getAddress() + "\n" +
	        "Balance        : ₹" + account.getBalance(),
	        infoFont
	    );
	    accountInfo.setSpacingAfter(10f);
	    document.add(accountInfo);

	    PdfPTable table = new PdfPTable(6);
	    table.setWidthPercentage(100f);
	    table.setSpacingBefore(10f);
	    table.setWidths(new float[]{2.5f, 3f, 2.5f, 2f, 4f, 2.5f});

	    writeTableHeader(table);
	    writeTableData(table, transactions);

	    document.add(table);
	    document.close();
	}


	private void writeTableHeader(PdfPTable table) {
	    PdfPCell cell = new PdfPCell();
	    cell.setBackgroundColor(Color.LIGHT_GRAY);
	    cell.setPadding(5);

	    Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

	    cell.setPhrase(new Phrase("Txn ID", font));
	    table.addCell(cell);

	    cell.setPhrase(new Phrase("Date & Time", font));
	    table.addCell(cell);

	    cell.setPhrase(new Phrase("Type", font));
	    table.addCell(cell);

	    cell.setPhrase(new Phrase("Amount", font));
	    table.addCell(cell);

	    cell.setPhrase(new Phrase("Message", font));
	    table.addCell(cell);
	    
	    cell.setPhrase(new Phrase("Current Bal", font));
	    table.addCell(cell);
	}

	private void writeTableData(PdfPTable table, List<Transactions> transactions) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	    for (Transactions txn : transactions) {
	        table.addCell(String.valueOf(txn.getTxnId()));
	        table.addCell(txn.getLocalDateTime().format(formatter));
	        table.addCell(txn.getType());
	        table.addCell(String.valueOf(txn.getAmount()));
	        table.addCell(txn.getMsg());
	        table.addCell(String.valueOf(txn.getCurrentBalance()));
	        
	    }
	}

	
	
}
