package bank.service;

import bank.domain.Account;
import bank.domain.Customer;
import bank.integration.jms.JMSSender;
import bank.integration.jms.JMSSenderImpl;
import bank.integration.logging.Logger;
import bank.integration.logging.LoggerImpl;
import bank.repository.AccountRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.transaction.Transactional;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;
	private CurrencyConverter currencyConverter;
	private JMSSender jmsSender;
	private Logger logger;
	
	public AccountServiceImpl() {
		currencyConverter= new CurrencyConverterImpl();
		jmsSender =  new JMSSenderImpl();
		logger = new LoggerImpl();
	}

	public Account createAccount(long accountNumber, String customerName) {
		Account account = new Account(accountNumber);
		Customer customer = new Customer(customerName);
		account.setCustomer(customer);
		accountRepository.save(account);
		logger.log("createAccount with parameters accountNumber= "+accountNumber+" , customerName= "+customerName);
		return account;
	}

	public void deposit(long accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		account.deposit(amount);
		accountRepository.save(account);
		logger.log("deposit with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		if (amount > 10000){
			jmsSender.sendJMSMessage("Deposit of $ "+amount+" to account with accountNumber= "+accountNumber);
		}
	}

	public Account getAccount(long accountNumber) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		return account;
	}

	public Collection<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public void withdraw(long accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if(account.getBalance() >= amount){
			account.withdraw(amount);
		    accountRepository.save(account);
	    	logger.log("withdraw with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		}
		
		
	}

	public void depositEuros(long accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		double amountDollars = currencyConverter.euroToDollars(amount);
		account.deposit(amountDollars);
		accountRepository.save(account);
		logger.log("depositEuros with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		if (amountDollars > 10000){
			jmsSender.sendJMSMessage("Deposit of $ "+amount+" to account with accountNumber= "+accountNumber);
		}
	}

	public void withdrawEuros(long accountNumber, double amount) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		double amountDollars = currencyConverter.euroToDollars(amount);
		if (account.getBalance() >= amount){
			account.withdraw(amount);
			accountRepository.save(account);
			logger.log("withdrawEuros with parameters accountNumber= "+accountNumber+" , amount= "+amount);
		}
	}

	public void transferFunds(long fromAccountNumber, long toAccountNumber, double amount, String description) {
		Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
		Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);
		fromAccount.transferFunds(toAccount, amount, description);
		accountRepository.save(fromAccount);
		accountRepository.save(toAccount);
		logger.log("transferFunds with parameters fromAccountNumber= "+fromAccountNumber+" , toAccountNumber= "+toAccountNumber+" , amount= "+amount+" , description= "+description);
		if (amount > 10000){
			jmsSender.sendJMSMessage("TransferFunds of $ "+amount+" from account with accountNumber= "+fromAccount+" to account with accountNumber= "+toAccount);
		}
	}

}


@JoinTable(name="cust_car",
			JoinColumns={@joinColumn(name = "cust_id")},
			InvesrseJoinColumn={@JoinColumn(name ="car_id")});
