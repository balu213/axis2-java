package example;

import java.rmi.RemoteException;

import example.BankServiceStub.AccountNotExistFaultMessageException;
import example.BankServiceStub.InsufficientFundFaultMessageException;

public final class BankClient {

	/**
	 * If account# == "13", then you will get a AccountNotExistFault.
	 * otherwise if you provide an amount > 1000, you will get a InsufficientFundFaultMessageException,
	 * otherwise you will get a response with a balance equal to 1000 - amountWithdrawn. 
	 */
	public static void main(String[] args) {

		if (args.length != 3) {
			System.err.println("Usage: BankClient <url> <account> <amount>");
			return;
		}
		
		final String url = args[0];
		final String account = args[1];
		final Integer withdrawalAmount = new Integer(args[2]);
		
		System.out.println();
		System.out.println("Withdrawing " + withdrawalAmount + " dollars from account#" + account);
		
        try {
            final BankService bankService = new BankServiceStub(url);
            final Withdraw withdrawRequest = new Withdraw();
            withdrawRequest.setAccount(account);
            withdrawRequest.setAmount(withdrawalAmount.intValue());
            
            final WithdrawResponse withdrawResponse = bankService.withdraw(withdrawRequest);
            System.out.println("Balance = " + withdrawResponse.getBalance());
            
        } catch (AccountNotExistFaultMessageException e) {
            final AccountNotExistFault fault = e.getFaultMessage();
            System.out.println("Account#" + fault.getAccount() + " does not exist");
        } catch (InsufficientFundFaultMessageException e) {
            final InsufficientFundFault fault = e.getFaultMessage();
            System.out.println("Account#" + fault.getAccount() + " has balance of " + fault.getBalance() + ". It cannot support withdrawal of " + fault.getRequestedFund());
        } catch (RemoteException e) {
            e.printStackTrace();  
        } catch (Exception e) {
            e.printStackTrace(); 
        }


		
	}
}