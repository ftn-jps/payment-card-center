package ftnjps.paymentcardcenter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ftnjps.paymentcardcenter.bank.Bank;
import ftnjps.paymentcardcenter.bank.BankService;

@Component
public class TestData {

	@Autowired
	private BankService bankService;

	@PostConstruct
	private void init() {
		Bank b1 = new Bank("444444", "https://localhost:8085");
		bankService.add(b1);
		Bank b2 = new Bank("555555", "https://localhost:8086");
		bankService.add(b2);
	}

}
