package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import asiakas.dao.Dao;
import tehtävät.Asiakas;


@WebServlet("/asiakkaat/*")
public class asiakkaat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
       

    public asiakkaat() {
        super();
        System.out.println("asiakkaat.asiakkaat()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat.doGet()");
		String pathInfo = request.getPathInfo();
		System.out.println("polku: " +pathInfo);
		Dao dao = new Dao();
		ArrayList<Asiakas> asiakkaat;
		String strJSON="";
		if(pathInfo==null) {
			asiakkaat = dao.listaaKaikki();
			strJSON = new JSONObject().put("asiakkaat", asiakkaat).toString();	
		}else if(pathInfo.indexOf("haeyksi")!=-1) {	
			String asiakas_id = pathInfo.replace("/haeyksi/", "");		
			Asiakas Asiakas = dao.etsiAsiakas(asiakas_id);
			JSONObject JSON = new JSONObject();
			JSON.put("etunimi", Asiakas.getEtunimi());
			JSON.put("sukunimi", Asiakas.getSukunimi());
			JSON.put("puhelin", Asiakas.getPuhelin());
			JSON.put("sposti", Asiakas.getSposti());
			JSON.put("asiakas_id", Asiakas.getAsiakas_id());	
			strJSON = JSON.toString();		
		}else{
			String hakusana = pathInfo.replace("/", "");
			asiakkaat = dao.listaaKaikki(hakusana);
			strJSON = new JSONObject().put("Asiakas", asiakkaat).toString();	
		}	
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(strJSON);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat.doPost()");
		JSONObject jsonObj = new JsonStrToObj().convert(request);		
		Asiakas asiakas = new Asiakas();
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhlin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.lisaaAsiakas(asiakas)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat.doPut()");
		JSONObject jsonObj = new JsonStrToObj().convert(request);		
		String vanhaasiakas_id = jsonObj.getString("vanhaasiakas_id");
		Asiakas asiakas = new Asiakas();
		asiakas.setAsiakas_id(jsonObj.getInt("asiakas_id"));
		asiakas.setEtunimi(jsonObj.getString("etunimi"));
		asiakas.setSukunimi(jsonObj.getString("sukunimi"));
		asiakas.setPuhelin(jsonObj.getString("puhelin"));
		asiakas.setSposti(jsonObj.getString("sposti"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.muutaAsiakas(asiakas, vanhaasiakas_id)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("asiakkaat.doDelete()");
		String pathInfo = request.getPathInfo();		
		System.out.println("polku: "+pathInfo);
		String poistettavaEtunimi = pathInfo.replace("/", "");		
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();			
		if(dao.poistaAsiakas(poistettavaEtunimi)){
			out.println("{\"response\":1}");
		}else{
			out.println("{\"response\":0}");
		}	
	}

}
