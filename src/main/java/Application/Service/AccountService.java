package Application.Service;

import java.util.List;

import Application.DAO.AccountsDAO;
import Application.Model.Account;

public class AccountService {
    AccountsDAO accountsDAO;

    public AccountService(){
        accountsDAO = new AccountsDAO();
    }
    public AccountService(AccountsDAO accountsDAO){
        this.accountsDAO = accountsDAO;
    }
    public Account addAccount(Account account){
        return accountsDAO.insertAccount(account);
    }

    public Account updateAccount(int accountId, Account account){
        accountsDAO.updateAccount(accountId, account);
        return accountsDAO.getAccountById(accountId);
    }
    public List<Account> getAllAccounts(){
        return accountsDAO.getAllAccounts();
    }
    public Account getAccountById(int id){
        return accountsDAO.getAccountById(id);
    }
    public Account getAccountByUsername(String username){
        return accountsDAO.getAccountByUsername(username);
    }

    // TESTING authenticate method
    public boolean authenticate(String username, String password){
        return accountsDAO.authenticate(username, password);
    }


}
