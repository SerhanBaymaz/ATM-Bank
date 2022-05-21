import java.security.InvalidParameterException;
import java.util.*;

/*
    @Ibrahim Baymaz
    @18.05.2022
 */

class Bank {
    //Attributes
    private String name;
    private String address;
    ArrayList<Customer> customers=new ArrayList<Customer>();
    ArrayList<Company> companies=new ArrayList<Company>();
    ArrayList<Account> accounts=new ArrayList<Account>();

    public Bank() {
    }

    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    //Methods
    public void addCustomer(int id,String name,String surname){
        Customer customerX = new Customer(name,surname);
        customerX.setId(id);
        customers.add(customerX);
    }
    public void addCompany(int id,String name){
        Company companyX = new Company(name);
        companyX.setId(id);
        companies.add(companyX);
    }
    public void addAccount(Account account){
        accounts.add(account);
    }


    public Customer getCustomer(int id) {
        boolean isFound = false;
        Customer c=new Customer();
        for (Customer customer1 : customers) {
            if (customer1.getId()==id) {
                isFound=true;
                c=customer1;
            }
        }

        if (isFound==true){
            return c;
        }else{
            throw new CustomerNotFoundException(id);
        }

    }
    public Customer getCustomer(String name, String surname) {
        boolean isFound = false;
        Customer c=new Customer();
        for (Customer customer1 : customers) {
            if (customer1.getName().equals(name) && customer1.getSurname().equals(surname)) {
                isFound=true;
                c=customer1;
            }
        }

        if (isFound==true){
            return c;
        }else{
            throw new CustomerNotFoundException(name,surname);
        }

    }

    public Company getCompany(int id){
        boolean isFound = false;
        Company c=new Company();
        for (Company company1 : companies) {
            if (company1.getId()==id) {
                isFound=true;
                c=company1;
            }
        }

        if (isFound==true){
            return c;
        }else{
            throw new CompanyNotFoundException(id);
        }

    }
    public Company getCompany(String name){
        boolean isFound = false;
        Company c=new Company();
        for (Company company1 : companies) {
            if (company1.getName().equals(name)) {
                isFound=true;
                c=company1;
            }
        }

        if (isFound==true){
            return c;
        }else{
            throw new CompanyNotFoundException(name);
        }

    }

    public Account getAccount(String accountNum){
        boolean isFound = false;
        Account a=new Account();
        for (Account account1 : accounts) {
            if (account1.getAcctNum().equals(accountNum)) {
                isFound=true;
                a=account1;
            }
        }

        if (isFound==true){
            return a;
        }else{
            throw new AccountNotFoundException(accountNum);
        }
    }

    public void transferFunds(String accountFrom,String accountTo,double amount){
        if (getAccount(accountFrom).getBalance()>=amount  && amount>=0){
            try {
                getAccount(accountFrom).withdrawal(amount);
                getAccount(accountTo).deposit(amount);
            }catch (AccountNotFoundException accEx){
                System.out.println(accEx);
            }
        }else{
            throw new InvalidAmountException(amount);
        }
    }

    public void closeAccount(String accountNum){
        if (getAccount(accountNum).getBalance()==0){

            try {
                accounts.remove(getAccount(accountNum));
            }catch (AccountNotFoundException acEx){
                System.out.println(acEx);
            }

        }else{
            throw new BalanceRemainingException(getAccount(accountNum).getBalance());
        }
    }

    public String infoForTostring(){
        String s = getName()+"    "+getAddress();
        System.out.println(s);
        for (int i = 0; i < companies.size(); i++) {
            System.out.println("    "+companies.get(i));
            for (int j = 0; j < companies.size()-i; j++){
                System.out.println("        "+accounts.get(j+i).getAcctNum()+" "+accounts.get(j+i).getBalance()+" ");
            }
        }

        for (int i = 0; i < customers.size(); i++) {
            System.out.println("    "+customers.get(i));

        }

        return "";
    }
    @Override
    public String toString() {
        return infoForTostring();

    }

    public void processTransactions( Collection<Transaction> transactions,String outFile){
            //1-Takes an unsorted List of Transaction objects and sorts them
            //2-Then processes each Transaction by type.
            //3-If an exception is encountered, writes to the file given in String an error log
        // according to the following:
        //1. “ERROR: “ + Exception type + “: “ + Transaction type + {tab} + Account number(s) separated by tab + {tab} + amount
          // Collections.sort(transactions);

        /*

        sorting : firstly sort them for type,
               if type are same sort them according to acct num which is:
                 For Deposits – in order by account to
                 For Transfers – in order by account to
                 For Withdrawals – in order by account from


         */

        //Collections.sort((ArrayList<Transaction>) transactions);

    }
}//Bank class

class Account {
/*
    --balance must be non-negative
 */

    // ATTRIBUTES
    private String accountNumber;
    private double balance;

    //METHODS
    public Account() {
    }

    // i. Constructor that takes the account number as parameter
    //  and defaults the balance to 0.
    public Account(String accountNumber) {

        this.balance=0;
        this.accountNumber=accountNumber;
    }

    //ii. Constructor that takes the account number and starting balance as parameter
    // if the balance is negative,set the balance to 0
    public Account(String accountNumber, double balance) {
        if(balance<0){
            this.balance =0;
        }else{
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }

    //iii. getAcctNum(): String
    public String getAcctNum() {
        return this.accountNumber;
    }
    //iv. getBalance(): double
    public double getBalance() {
        return this.balance;
    }

    //v. deposit(amount: double): None – increase balance by amount,
    // if amount is negative, do not change the balance.
    //1. raises InvalidAmountException if amount is negative
    void deposit(double amount){
        if(amount>=0){
            this.balance+=amount;
        }else{
            throw new InvalidAmountException(amount);
        }
    }

    //vi. withdrawal(amount: double): None – decrease balance by amount,
    // if amount is negative, do not change the balance.
    //     1. raises InvalidAmountException if amount is
    // negative or remaining balance is not enough
    void withdrawal(double amount){
        if (amount<0 && amount>balance){
            throw new InvalidAmountException(amount);
        }else{
            this.balance-=amount;
        }
    }

    //vii. toString(): String – “Account {account number} has {balance}”
    public String toString(){
        return "Account "+this.accountNumber+ " has "+ this.balance;
    }
}//Account class (1)

class PersonalAccount extends Account {

    //ATTRIBUTES
    private String name;
    private String surname;
    private String PIN;
    //private String accountNumber; from Account Class
    //private double balance; from Account Class

    //METHODS
    //i. Constructor that takes the account number, name, and surname as parameters
    //and sets the PIN to four (4) random digits
    public PersonalAccount(){}

    public PersonalAccount(String accountNumber) {
        super(accountNumber);
    }

    public PersonalAccount(String accountNumber, String name, String surname) {
        super(accountNumber);
        this.name = name;
        this.surname = surname;

        Random rand = new Random();
        int randomPIN = rand.nextInt(9999);
        //String randomPinString = Integer.toString(randomPIN);
        this.PIN=Integer.toString(randomPIN);
    }


    //ii. Constructor that takes the account number, name, surname, and balance as parameters
    //and sets the PIN to four (4) random digits
    public PersonalAccount(String accountNumber,  String name, String surname,double balance) {
        super(accountNumber, balance);
        this.name = name;
        this.surname = surname;

        int a = (int)((Math.random() * (9999 - 1000)) + 1000);
        this.PIN=Integer.toString(a);
        //return (int) ((Math.random() * (max - min)) + min);
    }


    //iii. getName() and setName(name: String)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    //iv. getSurname() and setSurname(surname: String)
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }


    //v. getPIN() and setPIN(PIN: String)
    public String getPIN() {
        return PIN;
    }
    public void setPIN(String PIN) {
        this.PIN = PIN;
    }


    //vi. toString(): String – “Account {account number} belonging to {name} {surname all capital letters} has {balance}”
    public String toString(){
        return "Account " + getAcctNum()+ " belonging to " +getName()  + " "+ getSurname().toUpperCase()+ " has "+getBalance();
    }

}//PersonalAccount class has done.

class BusinessAccount extends Account{
    /*
    (rate must be positive)
    set methodunda mı       if(interestRate>0){this.}    yapılacak?
     */
    private double interestRate;
    //private String accountNumber; from Account Class
    //private double balance; from Account Class


    public BusinessAccount() {
    }

    //i. Constructor that takes the account number and rate as parameters
    public BusinessAccount(String accountNumber, double interestRate) {
        super(accountNumber);
        this.interestRate = interestRate;
    }


    //ii. Constructor that takes the account number, balance, and rate as parameters
    public BusinessAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }


    //iii. getRate() and setRate(rate: double)
    public double getRate() {
        return this.interestRate;
    }
    public void setRate(double interestRate) {
        if (interestRate>0)
            this.interestRate = interestRate;
    }


    //iv. calculateInterest(): double – return amount of interest earned for the balance and rate.
    //NOTE: does not change value of balance
    public double calculateInterest(){
        return interestRate/100*getBalance();
    }

}//BusinessAccount class (1)

class Customer {
    /*
1) id: int – must be positive
2)personalAccounts arrayList'i public mi , private mi olacak?
     */
    //ATTRIBUTES
    private int id;
    private String name;
    private String surname;
    ArrayList<PersonalAccount> personalAccounts = new ArrayList<PersonalAccount>();

    //Getter and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        if (id>0);
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }



    //Constructors
    public Customer(){}
    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    //METHODS
    public void openAccount(String acctNum){
        personalAccounts.add( new PersonalAccount(acctNum));
    }

    public PersonalAccount getAccount(String accountNum) {
        boolean isFound = false;
        PersonalAccount pa=new PersonalAccount();
        for (PersonalAccount perAcc1 : personalAccounts) {
            if (perAcc1.getAcctNum().equals(accountNum)) {
                isFound=true;
                pa=perAcc1;
            }
        }

        if (isFound==true){
            return pa;
        }else{
            throw new AccountNotFoundException(accountNum);
        }

    }

    public void closeAccount(String accountNum){
        if (getAccount(accountNum).getBalance()==0){

            try {
                personalAccounts.remove(getAccount(accountNum));
            }catch (AccountNotFoundException acEx){
                System.out.println(acEx);
            }

        }else{
            throw new BalanceRemainingException(getAccount(accountNum).getBalance());
        }
    }


    // toString(): String – “{name} {surname all capital letters}”
    public String toString(){
        return getName()  + " "+ getSurname().toUpperCase();

    }

}//Customer class (2)

class Company{
    /*
    1) id: int – must be positive
    2)BussinesAccount arrayList'i public mi , private mi olacak?
     */

    //ATTRIBUTES
    private int id;
    private String name;
    ArrayList<BusinessAccount> businessAccounts = new ArrayList<BusinessAccount>();


    //Getter and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        if(id>0);
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    //Constructors
    public Company(){}
    public Company(String name) {
        this.name = name;
    }

    //Methods
    public void openAccount(String acctNum,double rate){
        businessAccounts.add(new BusinessAccount(acctNum,rate));
    }



    public BusinessAccount getAccount(String acctNum) {
        boolean isFound = false;
        BusinessAccount ba=new BusinessAccount();
        for (BusinessAccount bussAcc1 : businessAccounts) {
            if (bussAcc1.getAcctNum().equals(acctNum)) {
                isFound=true;
                ba=bussAcc1;
            }
        }

        if (isFound==true){
            return ba;
        }else{
            throw new AccountNotFoundException(acctNum);
        }

    }

    public void closeAccount(String accountNum){
        if (getAccount(accountNum).getBalance()==0){

            try {
                businessAccounts.remove(getAccount(accountNum));
            }catch (AccountNotFoundException acEx){
                System.out.println(acEx);
            }

        }else{
            throw new BalanceRemainingException(getAccount(accountNum).getBalance());
        }
    }

    // toString(): String – “{name}”
    public String toString() {
        return getName();
    }
}//Company class (2)

class Transaction implements Comparable<Transaction>{

    //Attributes
    private int type;
    private String to;
    private  String from;
    private double amount;

    //Constructors
    public Transaction(int type,String to,String from,double amount){
        this.type=type;
        this.to=to;
        this.from=from;
        this.amount=amount;

        if (type!=2){
            throw new InvalidParameterException("You should enter type=2,string to,string from, double amount.");
        }
    }
    public Transaction(int type,String acctNumberToOrFrom,double amount){
        this.type=type;
        this.amount=amount;

        if(type!=1 & type!=3){
            throw new InvalidParameterException("Invalid transaction type");
        }
    }

    //Getter methods
    public int getType() {
        return type;
    }
    public String getTo() {
        return to;
    }
    public String getFrom() {
        return from;
    }
    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "type:"+type + "   amount:"+amount +"\n";
    }

    @Override
    public int compareTo(Transaction o1) {
        int compareSayisi = ((Transaction) o1).getType();
        return this.type - compareSayisi;
    }
    /*
    compare to string;
    public int compareTo(Transaction o1) {
        return from.compareTo(o1.from);
    }
    */





}//Transaction class()

class AccountNotFoundException extends RuntimeException{
    String acctNum;
    public AccountNotFoundException(String acctNum){
        this.acctNum=acctNum;
    }

    @Override
    public String toString() {
        return "AccountNotFoundException: " + acctNum;
    }
}//AccountNotFoundException class

class BalanceRemainingException extends RuntimeException{
    double balance;

    public BalanceRemainingException(double balance){
        this.balance=balance;
    }

    @Override
    public String toString() {
        return "BalanceRemainingException:"+balance;
    }

    public double getBalance(){
        return balance;
    }
}//BalanceRemainingException class

class InvalidAmountException extends RuntimeException{
    double amount;

    public InvalidAmountException(double amount){
        this.amount=amount;
    }

    @Override
    public String toString() {
        return "InvalidAmountException:"+amount;
    }
}//InvalidAmountException class

class CustomerNotFoundException extends RuntimeException{
    int id;
    String name;
    String surname;

    public CustomerNotFoundException(int id){
        this.id=id;
        this.name=null;
        this.surname=null;
    }

    public CustomerNotFoundException(String name,String surname){
        this.name=name;
        this.surname=surname;
        this.id=0000;
    }

    @Override
    public String toString() {
        if (name==null)
            return "CustomerNotFoundException: id - "+id;
        else
            return "CustomerNotFoundException: name - " + name + " " + surname;
    }
}//CustomerNotFoundException class

class CompanyNotFoundException extends RuntimeException{
    int id;
    String name;

    public CompanyNotFoundException(int id) {
        this.id = id;
        this.name=null;
    }
    public CompanyNotFoundException(String name) {
        this.name = name;
        this.id=0000;
    }

    @Override
    public String toString() {
        if (name==null)
            return "CompanyNotFoundException: id - " + id;
        else
            return "CompanyNotFoundException: name - " + name;
    }

}//CompanyNotFoundException

public class Assignment03_20200808006 {

    public static void main(String[] args) throws Exception {
        // write your code here
        Bank b = new Bank("My Bank", "My Bank's Address");
        b.addCompany(1, "Company 1");
        b.getCompany(1).openAccount("1234", 0.05);
        b.addAccount(b.getCompany(1).getAccount("1234"));
        b.getAccount("1234").deposit(500000);
        b.getCompany(1).getAccount("1234").deposit(500000);
        b.getCompany(1).openAccount("1235", 0.03);
        b.addAccount(b.getCompany(1).getAccount("1235"));
        b.getCompany(1).getAccount("1235").deposit(25000);
        b.addCompany(2, "Company 2");
        b.getCompany(2).openAccount("2345", 0.03);
        b.addAccount(b.getCompany(2).getAccount("2345"));
        b.getCompany(2).getAccount("2345").deposit(350);
        b.addCustomer(1, "Customer", "1");
        b.addCustomer(2, "Customer", "2");
        Customer c = b.getCustomer(1);
        c.openAccount("3456");
        c.openAccount("3457");
        c.getAccount("3456").deposit(150);
        c.getAccount("3457").deposit(250);
        c = b.getCustomer(2);
        c.openAccount("4567");
        c.getAccount("4567").deposit(1000);
        b.addAccount(c.getAccount("4567"));
        c = b.getCustomer(1);
        b.addAccount(c.getAccount("3456"));
        b.addAccount(c.getAccount("3457"));
        //System.out.println(b.toString());


        Collection<Transaction> ts = new ArrayList<Transaction>();
        ts.add(new Transaction(1,"1230",45646));
        //ts.add(new Transaction(2,"1234,",500));
        //ts.add(new Transaction(5,"1237","8759",454));
        ts.add(new Transaction(3,"1235",4564654));
        ts.add(new Transaction(2,"1236","8789",4568));
        ts.add(new Transaction(2,"1237","456",500));
        ts.add(new Transaction(3,"1238",508640));
        ts.add(new Transaction(1,"1239",7080));
        Collections.sort((ArrayList<Transaction>) ts);
        System.out.println(ts);


        //new Transaction(0,"4567","3456",1);

    }//main
}//Assigment
