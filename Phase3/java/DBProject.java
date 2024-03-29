/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public ResultSet executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      return rs;
      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      /*ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;*/
   }//end executeQuery

  public int executeQuery2 (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);
      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
    // Given customer details add the customer in the DB 
 	int customerID;
	String phNo;
	String fName;
	String lName;
	String address;
	String DOB;
	String gender;

	do{
		System.out.print("Please Enter the Customer ID: ");
		try{
			customerID = Integer.parseInt(in.readLine());
			if(customerID < 0) {
				throw new RuntimeException("Invalid input: input cannot be a negative integer... Please try again");
			}
			break;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			continue;
		}
	}while(true);


        do{
                System.out.print("Please Enter the Customer's First Name: ");
                try{
                        fName = in.readLine();
			if(fName.length() <= 0 || fName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...Please try again");
			}
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        do{
                System.out.print("Please Enter the Customer's Last Name: ");
                try{
                        lName = in.readLine();
			if(lName.length() <= 0 || lName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...Please try again");
			}
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Customer's Address: ");
                try{
                        address = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        do{
                System.out.print("Please Enter the Customer's Phone #: ");
                try{
                        phNo = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        do{
                System.out.print("Please Enter the Customer's Date of Birth (MM/DD/YYYY): ");
                try{
                        DOB = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        do{
                System.out.print("Please Enter the Customer's Gender (Male/Female/Other): ");
                try{
                        gender = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

	try {
     		String query = "INSERT INTO Customer VALUES (" + customerID + ", \'" + fName + "\', \'" + lName + "\', \'" +
				 address + "\', " + phNo + ",\'" + DOB + "\',\'" + gender + "\')";
		esql.executeQuery(query);
	}catch(Exception e) {
		System.err.println(e.getMessage());		
	}	
   }//end addCustomer

   public static void addRoom(DBProject esql){
      // Given room details add the room in the DB
      int hotelID;
      int roomNo;
      String roomType;

        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
			if(hotelID < 0) {
				throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
			}
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Room Number: ");
                try{
                        roomNo = Integer.parseInt(in.readLine());
                        if(roomNo < 0) {
                                throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
                        }
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Room Type: ");
                try{
                        roomType = in.readLine();
                        if(roomType.length() <= 0 || roomType.length() > 10) {
                                throw new RuntimeException("Invalid input: input is null or exceeds 10 characters...");
                        }
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        try {
                String query = "INSERT INTO Room VALUES (" + hotelID + ", " + roomNo + ", \'" + roomType  + "\')";
                esql.executeQuery(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
        // Given maintenance Company details add the maintenance company in the DB
        int cmpID;
        String name;
        String address;
	String isCertified;

        do{
                System.out.print("Please Enter the Company ID: ");
                try{
                        cmpID = Integer.parseInt(in.readLine());
                        if(cmpID < 0) {
                                throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
                        }
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Company Name: ");
                try{
                        name = in.readLine();
			if(name.length() <= 0 || name.length() > 10) {
				throw new RuntimeException("Invalid input: input is null or exceeds 10 characters...");
			}
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Company Address: ");
                try{
                        address = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Is the company certified? (TRUE/FALSE): ");
                try{
                        isCertified = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
                String query = "INSERT INTO MaintenanceCompany VALUES (" + cmpID + ",\'" + name + "\', \'" + address + "\'," + isCertified +")";
                esql.executeQuery(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
    // Given repair details add repair in the DB
	int rID;
	int hotelID;
	int roomNo;
	int mCompany;
	String repairDate;
	String description;
	String repairType;
	 
	do{
                System.out.print("Please Enter the Repair ID: ");
                try{
                        rID = Integer.parseInt(in.readLine());
                        if(rID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}    
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          	}      
			 break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Room Number: ");
                try{
                        roomNo = Integer.parseInt(in.readLine());
                        if(roomNo < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Company ID: ");
                try{
                        mCompany = Integer.parseInt(in.readLine());
                        if(mCompany < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          	}      
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

	do{
                System.out.print("Please Enter the Repair Date: ");
                try{
                        repairDate = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Description of repair: ");
                try{
                        description = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Repair Type: ");
                try{
                        repairType = in.readLine();
                        if(repairType.length() <= 0 || repairType.length() > 10) {
                                throw new RuntimeException("Invalid input: input is null or exceeds 10 characters...");
                        }
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);


        try {
		String query = "INSERT INTO Repair VALUES (" + rID + "," + hotelID + "," + roomNo + "," + mCompany + 
					",\'" + repairDate + "\',\'" + description + "\',\'" + repairType + "\')";
                esql.executeQuery(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   
   }//end addRepair

   public static void bookRoom(DBProject esql){
      // Given hotelID, roomNo and customer Name create a booking in the DB 
    	int hotelID;
        int roomNo;
	int noOfPeople;
	int price;
	String bookingDate;	

        String fName;
        String lName;

        do{
            System.out.print("Please Enter the Hotel ID: ");
            try{
              hotelID = Integer.parseInt(in.readLine());
              if(hotelID < 0) {
                	throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
              }      
	      break;
            }catch(Exception e) {
              System.err.println(e.getMessage());
              continue;
            }
        }while(true);

        do{
            System.out.print("Please Enter the Room Number: ");
            try{
              roomNo = Integer.parseInt(in.readLine());
              if(roomNo < 0) {
                	throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
              }      
	      break;
            }catch(Exception e) {
              System.err.println(e.getMessage());
              continue;
            }
        }while(true);

        do{
               System.out.print("Please Enter the Customer's First Name: ");
               try{
                       fName = in.readLine();
                       break;
               }catch(Exception e) {
                       System.err.println(e.getMessage());
                       continue;
               }
       }while(true);

       do{
              System.out.print("Please Enter the Customer's Last Name: ");
              try{
                      lName = in.readLine();
                      break;
              }catch(Exception e) {
                      System.err.println(e.getMessage());
                      continue;
              }
      }while(true);

       do{
              System.out.print("Please Enter the Booking Date: ");
              try{
                      bookingDate = in.readLine();
                      break;
              }catch(Exception e) {
                      System.err.println(e.getMessage());
                      continue;
              }
      }while(true);

       do{
              System.out.print("Please Enter the Number of Guests: ");
              try{
                      noOfPeople = Integer.parseInt(in.readLine());
                      if(noOfPeople < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          	      }      
		      break;
              }catch(Exception e) {
                      System.err.println(e.getMessage());
                      continue;
              }
      }while(true);

       do{
              System.out.print("Please Enter the Price of Room: ");
              try{
                      price = Integer.parseInt(in.readLine());
                      if(price < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          	      }      
		      break;
              }catch(Exception e) {
                      System.err.println(e.getMessage());
                      continue;
              }
       }while(true);

       try {
               ResultSet getCustomer = esql.executeQuery("SELECT COUNT(*) AS customerID FROM Customer c WHERE c.fname = \'" + 
						fName + "\' AND c.lName = \'" + lName + "\'");
               getCustomer.next();

               ResultSet getNewID = esql.executeQuery("SELECT MAX(bID) AS max_id FROM Booking");
               getNewID.next();
               
               int bID = getNewID.getInt("max_id") + 1;
               int customerID = getCustomer.getInt("customerID");

               String query = "INSERT INTO Booking VALUES (" + bID + ", " + customerID + ", " + 
					hotelID + ", " + roomNo + ", \'" + bookingDate + "\', " +
						 noOfPeople + ", " + price + ")";
	       esql.executeQuery(query);
	}catch(Exception e) {
                System.err.println(e.getMessage());
       }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
      // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
	int staffID;
  	int hotelID;
        int roomNo;
  
        do{
                System.out.print("Please Enter the Staff ID: ");
                try{
                        staffID = Integer.parseInt(in.readLine());
                        if(staffID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}	
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                       	if(hotelID < 0) {
                                throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
                        }
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Room Number: ");
                try{
                        roomNo = Integer.parseInt(in.readLine());
                        if(roomNo < 0) {
                                throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
                        }
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
		ResultSet getasgID = esql.executeQuery("SELECT MAX(asgID) AS max_id FROM Assigned");
                getasgID.next();
               
                int asgID = getasgID.getInt("max_id") + 1;
                
		String query = "UPDATE Assigned SET asgID = " + asgID + ", roomNo = " + 
			roomNo + " WHERE hotelID = " + hotelID + " and staffID = " + staffID;
                esql.executeQuery(query);
        } catch(Exception e) {
                System.err.println(e.getMessage());
       }
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
      // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
	int hotelID;
        int staffID;
        int roomNo;
        int repairID;
        String requestDate;
        String description;

          do{
                  System.out.print("Please Enter the Hotel ID: ");
                  try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }while(true);

          do{
                  System.out.print("Please Enter the Staff's ID: ");
                  try{
                        staffID = Integer.parseInt(in.readLine());
                        if(staffID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }while(true);

          do{
                  System.out.print("Please Enter the Room Number: ");
                  try{
                        roomNo = Integer.parseInt(in.readLine());
                        if(roomNo < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }while(true);

          do{
                  System.out.print("Please Enter the Repair ID: ");
                  try{
                        repairID = Integer.parseInt(in.readLine());
                        if(repairID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}	      
			break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }while(true);

          do{
                  System.out.print("Please Enter the Request Date (MM-DD-YYYY): ");
                  try{
                        requestDate = in.readLine();
                        if(requestDate.length() != 10) {
                          throw new RuntimeException("Invalid input: input must follow format: 'YYYY-MM-DD'");
                        }
                        break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }
          while(true);

          do{
                  System.out.print("Please Enter the Repair Request Description: ");
                  try{
                        description = in.readLine();
                        break;
                  }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                  }
          }
          while(true);

      try {
		ResultSet getReqID = esql.executeQuery("SELECT MAX(reqID) AS max_id FROM Request");
          	getReqID.next();
		
		int ID = getReqID.getInt("max_id") + 1;
       	    	String query = "INSERT INTO Request VALUES (" + ID + ", " + staffID
			+ ", " + repairID + ", \'" + requestDate + "\', \'" + description + "\')";
            	esql.executeQuery(query);
      }catch(Exception e) {
            	System.err.println(e.getMessage());
      }
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
      // Given a hotelID, get the count of rooms available 
        int hotelID;
        
        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);
        
        try {
                ResultSet getCount = esql.executeQuery("SELECT COUNT(*) as COUNT FROM Room r, Booking b WHERE r.hotelID = b.hotelID AND r.hotelID = " + 
					hotelID + "AND r.roomNo NOT IN (SELECT r.roomNo FROM Booking b WHERE r.roomNo = b.roomNo)");
		getCount.next();

		int count = getCount.getInt("count");
		System.out.println("Number of Available Rooms: " + count);
	}catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
      // Given a hotelID, get the count of rooms booked
        int hotelID;
        
        do{
                System.out.print("Please Enter to Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
                ResultSet getCount = esql.executeQuery("SELECT COUNT(*) AS count FROM Booking b WHERE b.hotelID = " + hotelID);
		getCount.next();

		int count = getCount.getInt("count"); 
		System.out.println("Number of Booked Rooms: " + count);               
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms booked for a week(including the input date) 
  	int hotelID;
        String date;
        
        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Date: ");
                try{
                        date = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
                String query = "SELECT b.roomNo, b.customer, b.bookingDate FROM Booking b WHERE b.hotelID = " + hotelID 
			+ "AND (b.bookingDate BETWEEN \'" + date + "\' AND DATE \'" + date + "\' + INTERVAL '1 week') GROUP BY b.roomNo, b.customer, b.bookingDate";
                esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }  
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
    // List Top K Rooms with the highest price for a given date range
       int K;
       String dateStart;
       String dateEnd;

       do{
               System.out.print("Please Enter the Number of Rooms: ");
               try{
                       K = Integer.parseInt(in.readLine());
                       if(K < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}      
			break;
               }catch(Exception e) {
                       System.err.println(e.getMessage());
                       continue;
               }
       }while(true);

       do{
               System.out.print("Please Enter Start Date: ");
               try{
                       dateStart = in.readLine();
                       break;
               }catch(Exception e) {
                       System.err.println(e.getMessage());
                       continue;
               }
       }while(true);

       do{
               System.out.print("Please Enter End Date: ");
               try{
                       dateEnd = in.readLine();
                       break;
               }catch(Exception e) {
                       System.err.println(e.getMessage());
                       continue;
               }
       }while(true);
       
       try {
               String query = "SELECT b.price, b.bookingDate FROM Room r, Booking b WHERE r.roomNo = b.roomNo AND b.bookingDate BETWEEN \'" + 
					dateStart + "\' AND DATE \'" + dateEnd + "\' ORDER BY b.price DESC LIMIT " + K;
               esql.executeQuery2(query);
       }catch(Exception e) {
               System.err.println(e.getMessage());
       }
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
      // Given a customer Name, List Top K highest booking price for a customer 
	String fName;
        String lName;
        int K;

         do{
                System.out.print("Please Enter the Customer's First Name: ");
                try{
                        fName = in.readLine();
                        if(fName.length() <= 0 || fName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...");
			}
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Customer's Last Name: ");
                try{
                        lName = in.readLine();
                        if(lName.length() <= 0 || lName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...");
			}
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

         do{
                System.out.print("Please Provide Number of Bookings: ");
                try{
                        K = Integer.parseInt(in.readLine());
                        if(K < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}    
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
                String query = "SELECT b.price FROM Booking b, Customer c WHERE c.customerID = b.customer AND c.fName = \'" + 
					fName + "\' AND c.lname = \'" + lName + "\' ORDER BY b.price DESC LIMIT " + K;
                esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
      // Given a hotelID, customer Name and date range get the total cost incurred by the customer
	String fName;
        String lName;
        int hotelID;
	String startDate;
	String endDate;

         do{
                System.out.print("Please Enter the Customer's First Name: ");
                try{
                        fName = in.readLine();
                        if(fName.length() <= 0 || fName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...");
			}
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Customer's Last Name: ");
                try{
                        lName = in.readLine();
                        if(lName.length() <= 0 || lName.length() > 30) {
				throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...");
			}
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

         do{
                System.out.print("Please Enter Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}	  
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter Start Date: ");
                try{
                        startDate = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the End Date: ");
                try{
                        endDate = in.readLine();
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
		ResultSet getCustomer = esql.executeQuery("SELECT COUNT(*) AS customerID FROM Customer c WHERE c.fName = \'" +
					 fName + "\' AND c.lName = \'" + lName + "\'");
		getCustomer.next();

		String customerID = getCustomer.getString("customerID");

		String query = "SELECT SUM(b.price), b.customer FROM Booking b WHERE b.hotelID = " + hotelID + 
				" AND b.customer = " + customerID +"AND b.bookingDate BETWEEN \'"+ startDate + "\' AND DATE \'" + endDate + "\' GROUP BY b.customer";
                esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
      // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
	String companyName;

        do{
                System.out.print("Please Enter the Maintenance Company Name: ");
                try{
                        companyName = in.readLine();
			if(companyName.length() <= 0 || companyName.length() > 30) {
				 throw new RuntimeException("Invalid input: input is null or exceeds 30 characters...");	
			}
                        break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
		ResultSet getID = esql.executeQuery("Select c.cmpID as ID FROM MaintenanceCompany c WHERE c.name = \'" + companyName + "\'");
		getID.next();

		int companyID = getID.getInt("ID");

                String query = "SELECT r.mCompany, r.repairType, r.hotelID, r.roomNo FROM Repair r, MaintenanceCompany m WHERE r.mCompany = m.cmpID AND m.cmpID = " + companyID;
                esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	 // List Top K Maintenance Company Names based on total repair count (descending order)
        int K;
        
        do{
                System.out.print("Please Enter the Number of companies: ");
                try{
                        K = Integer.parseInt(in.readLine());
                        if(K < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}  
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        try {
                String query = "SELECT COUNT(r.rID), m.name FROM MaintenanceCompany m, Repair r WHERE m.cmpID = r.mCompany GROUP BY m.name ORDER BY (COUNT(r.rID)) DESC LIMIT " + K;
                esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
      // Given a hotelID, roomNo, get the count of repairs per year
        int hotelID;
        int roomNo;

        do{
                System.out.print("Please Enter the Hotel ID: ");
                try{
                        hotelID = Integer.parseInt(in.readLine());
                        if(hotelID < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}  
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);

        do{
                System.out.print("Please Enter the Room Number: ");
                try{
                        roomNo = Integer.parseInt(in.readLine());
                        if(roomNo < 0) {
                		throw new RuntimeException("Invalid input: input cannot be a negative integer...Please try again");
          		}  
			break;
                }catch(Exception e) {
                        System.err.println(e.getMessage());
                        continue;
                }
        }while(true);
        
        try {
                String query = "SELECT DATE_PART('year', r.repairDate), COUNT(*) FROM Repair r WHERE r.roomNo = " + 
					roomNo +" AND r.hotelID = " + hotelID + " GROUP BY DATE_PART('year', r.repairDate)";
		esql.executeQuery2(query);
        }catch(Exception e) {
                System.err.println(e.getMessage());
        }
   }//end listRepairsMade

}//end DBProject
