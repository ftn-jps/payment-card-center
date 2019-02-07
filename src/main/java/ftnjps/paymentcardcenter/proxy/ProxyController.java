package ftnjps.paymentcardcenter.proxy;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ftnjps.paymentcardcenter.bank.Bank;
import ftnjps.paymentcardcenter.bank.BankService;
import ftnjps.paymentcardcenter.carddetails.CardDetails;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

	@Autowired
	private BankService bankService;
	@Autowired
	RestTemplate restClient;

	@PostMapping("/amount/{amount}")
	public ResponseEntity<Boolean> forwardTransaction(
		@PathVariable double amount,
		@RequestBody @Valid CardDetails cardDetails)
	{
		System.out.println("Getting bank by iin ");
		final Bank bank = bankService.findByIin(cardDetails.getPan().substring(0, 6));
		System.out.println("Bank found! Bank " + bank.getUrl() + "matches the specified iin: " + bank.getIin());

		System.out.println("Card number: " + bank.getIin() + " ******** " +
			cardDetails.getPan().substring(cardDetails.getPan().length()-2));

		System.out.println("Starting transaction...");
		ResponseEntity<Boolean> response =
				restClient.postForEntity(
					bank.getUrl() + "/api/transactions/amount/" + amount,
					cardDetails,
					Boolean.class);

		System.out.println("Transaction completed successefully");
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}

}
