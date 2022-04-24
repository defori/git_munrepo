package asiakas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import tehtävät.Asiakas;



public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Myynti.sqlite";
	
	private Connection yhdista(){		
    	Connection con = null;    	
    	String path = System.getProperty("user.dir")+"/";    	
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	public ArrayList<Asiakas> listaaKaikki() {
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM asiakkaat";       
		try {
			con=yhdista();
			if(con!=null){
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){				
					System.out.println();
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
						System.out.print(rs.getInt(1) +"\t\t");
						System.out.print(rs.getString(2)+"\t\t");
						System.out.print(rs.getString(3)+"\t\t");	
						System.out.print(rs.getString(4)+"\t\t");
						System.out.print(rs.getString(5)+"\t\t");
						asiakkaat.add(asiakas);
					}
					System.out.println();
				}
				con.close();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asiakkaat;
		
	}
}
