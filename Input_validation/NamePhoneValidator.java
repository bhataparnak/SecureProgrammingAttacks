

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;



public class NamePhoneValidator {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
          initializeDB();
        if(args.length == 0) {
            System.out.println("Usage:");
            System.out.println("ADD \"<Name>\" \"<Phone number>\"");
            System.out.println("DEL \"<Name>\"" );
            System.out.println("DEL  \"<Phone number>\"");
            System.out.println("LIST");
        }

        /*else if (args.length < 3 || args.length > 3)
        {
            System.out.println("Invalid number of args");
            System.exit(1);
        }*/
        else
        {
            String operation = args[0];
            System.out.println("Operation : "+operation);
            if(!operation.equalsIgnoreCase("Add")&& !operation.equalsIgnoreCase("Del")&&
                    !operation.equalsIgnoreCase("List"))
            {
                System.out.println("Invalid Operation");
                System.exit(1);
            }
            else
            {

                switch(operation)
                {
                    case "ADD" : if(args[1] == null || args[2] == null)
                                 {
                                    System.err.println("Invalid arguments");
                                    System.exit(1);
                                 }
                                 else {
                                    String name = args[1].replace("\"", "");
                                    String phone = args[2].replace("\"", "");
                                    //System.out.println("UID : " + getUID() + " attempting to add record");
                                    validateAndAddToDB(name, phone);
                                }
                                break;
                    case "DEL" : if(args[1] == null)
                                {
                                    System.err.println("Invalid arguments");
                                    System.exit(1);
                                }
                                else {
                                    //System.out.println("UID : " + getUID() + " attempting to delete record");
                                    String str = args[1].replace("\"", "");
                                    deleteRecord(str);
                                    }
                                 break;
                    case "LIST" : //System.out.println("UID : "+getUID()+" attempting to list records");
                                listAllRecords();
                                    break;
                }
            }
        }
    }

    private static String getUID() throws IOException {
        InputStream in  = null;
        StringBuilder uid = null;
        //List<Integer> uid
        try {
            String userName = System.getProperty("user.name");
            String command = "id -u "+userName;
            Process child = Runtime.getRuntime().exec(command);


            // Get the input stream and read from it
             in = child.getInputStream();
            int c;
             uid = new StringBuilder();
            //List<Integer> uid = new LinkedList<>();
            while ((c = in.read()) != -1) {
                uid.append(String.valueOf(c));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        in.close();
        return uid.toString();
    }

    private static void initializeDB() throws SQLException {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Person.db");
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PERSON " +
                    " (NAME           TEXT    NOT NULL, " +
                    " PHONE          TEXT PRIMARY KEY   NOT NULL)";
            stmt.executeUpdate(sql);
            //System.out.println("Table created successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally
        {
            if(stmt != null && c!= null) {
                stmt.close();
                c.close();
            }
        }
    }

    private static void listAllRecords() throws SQLException {
        PreparedStatement stmt = null;
        Connection c = null;
        ResultSet personRecs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Person.db");
            System.out.println("Opened database successfully");
            stmt = c.prepareStatement("select * from PERSON");
             personRecs = stmt.executeQuery();
            while(personRecs.next())
            {
                System.out.println(personRecs.getString("NAME")+"      "+personRecs.getString("PHONE"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(stmt != null && c!= null && personRecs !=null) {
                personRecs.close();
                stmt.close();
                c.close();
            }
        }

    }

    private static void deleteRecord(String str) throws SQLException {
        PreparedStatement stmt = null;
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
             c = DriverManager.getConnection("jdbc:sqlite:Person.db");
            System.out.println("Opened database successfully");


            if (isValidName(str)) {
                 stmt = c.prepareStatement("delete from PERSON where NAME = ?");
                stmt.setString(1, str);
                int delCnt = stmt.executeUpdate();
                if(delCnt > 0)
                    System.out.println("Deleted " + str + " record from DB at " + new Date());
                else
                    System.out.println("Record " + str + " not found in DB");
            } else if (isValidPhone(str)) {
                 stmt = c.prepareStatement("delete from PERSON where PHONE = ?");
                stmt.setString(1, str);
                int delCnt = stmt.executeUpdate();
                if(delCnt > 0)
                    System.out.println("Deleted " + str + " record from DB at " + new Date());
                else
                    System.out.println("Record " + str + " not found in DB");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(stmt != null && c!= null) {
                stmt.close();
                c.close();
            }
        }
    }


        private static void validateAndAddToDB(String name , String phone) throws ClassNotFoundException, SQLException {

        Boolean validPh = false;
        Boolean validName = false;

        if((name== null || name.trim().length()==0)|| (phone == null || phone.trim().length()==0))
        {
            System.err.println("Invalid name or phone number specified");
            System.exit(1);
        }

            validName = isValidName(name);
            validPh = isValidPhone(phone);

        if(validName && validPh)
        {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:Person.db");
            System.out.println("Opened database successfully");
            PreparedStatement stmt = c.prepareStatement("insert into PERSON values(?,?)");
            stmt.setString(1,name);
            stmt.setString(2,phone);
            stmt.executeUpdate();
            System.out.println("added  "+name+" into DB at "+new Date());
        }
        else
        {
            if(!validName)
                System.out.println("Invalid name format supplied");
            if(!validPh)
                System.out.println("Invalid phone number format supplied");
        }

    }

    private static boolean isValidName(String name)
    {
        Pattern namePatterm = Pattern.compile("([a-zA-Z]+['`]?[a-zA-Z]+)(,)( )?([a-zA-z][ '-]?[a-zA-Z]+){1}");
        Matcher nameMatcher = namePatterm.matcher(name);
        boolean validName = false;
        if(nameMatcher.matches())
            validName = true;

        if(!validName)
        {
            System.err.println("Invalid Name format");
            System.exit(1);
        }

        return validName;
    }

    private static boolean isValidPhone(String phone)
    {
        boolean validPh = false;
        //Pattern pattern2 = Pattern.compile("^(\\d{3}[- .]?){2}\\d{4}$");//2055550125, 202 555 0125, 202.555.0125, and 202-555-0125.
        Pattern pattern2 = Pattern.compile("^([1-9]{1}[0-9]{2}[- .]?){2}\\d{4}$");//2055550125, 202 555 0125, 202.555.0125, and 202-555-0125.
       // Pattern pattern3 = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");//(202)5550125, (202) 555-0125 or (202)-555-0125.
        Pattern pattern3 = Pattern.compile("^((\\([1-9]{1}[0-9]{2}\\))|[1-9]{1}[0-9]{2})[- .]?\\d{3}[- .]?\\d{4}$");//(202)5550125, (202) 555-0125 or (202)-555-0125.
        Pattern pattern4 = Pattern.compile("^\\d{1}((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");//1(202)5550125, 1(202) 555-0125 or 1(202)-555-0125.
        //Pattern pattern5 = Pattern.compile("^(\\+\\d{1,3}( )?){1}((\\(\\d{2,3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Pattern pattern5 = Pattern.compile("^(\\+[1-9]{1}[0-9]?[0-9]?( )?){1}((\\(\\d{2,3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Pattern pattern6 = Pattern.compile("^(\\d{5}[.]?)\\d{5}$");
        Pattern pattern7 = Pattern.compile("^\\d{5}$");
        Pattern pattern8 = Pattern.compile("^([0][1][1])[- .][1]?[- .]?(\\d{3}[- .]?){2}\\d{4}$");
        Pattern pattern9 = Pattern.compile("^([0][0])[- .][1]?[- .]?(\\d{3}[- .]?){2}\\d{4}$");
        Pattern pattern10 = Pattern.compile("^(\\d{3}[-]?)\\d{4}$");

        Matcher phMatcher1 = pattern2.matcher(phone);
        Matcher phMatcher2 = pattern3.matcher(phone);
        Matcher phMatcher3 = pattern4.matcher(phone);
        Matcher phMatcher4 = pattern5.matcher(phone);
        Matcher phMatcher5 = pattern6.matcher(phone);
        Matcher phMatcher6 = pattern7.matcher(phone);
        Matcher phMatcher7 = pattern8.matcher(phone);
        Matcher phMatcher8 = pattern9.matcher(phone);
        Matcher phMatcher9 = pattern10.matcher(phone);
        if(phMatcher1.matches()) {
            System.out.println("ph1 match");
            validPh = true;
        }
        else if(phMatcher2.matches()){
            System.out.println("ph2 match");
            validPh = true;
        }
        else if(phMatcher3.matches()){
            System.out.println("ph3 match");
            validPh = true;
        }
        else if(phMatcher4.matches()){
            System.out.println("ph4 match");
            validPh = true;
        }
        else if(phMatcher5.matches()){
            System.out.println("ph5 match");
            validPh = true;
        }
        else if(phMatcher6.matches()){
            System.out.println("ph6 match");
            validPh = true;
        }
        else if(phMatcher7.matches()){
            System.out.println("ph7 match");
            validPh = true;
        }
        else if(phMatcher8.matches()){
            System.out.println("ph8 match");
            validPh = true;
        }
        else if(phMatcher9.matches()){
            System.out.println("ph9 match");
            validPh = true;
        }

        if(!validPh)
        {
            System.out.println("Invalid Phone number format");
            System.exit(1);
        }

        return  validPh;
    }
}
