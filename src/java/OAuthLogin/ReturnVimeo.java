/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuthLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Krystian
 */
@WebServlet(name = "ReturnVimeo", urlPatterns = {"/ReturnVimeo"})
public class ReturnVimeo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
           
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try(PrintWriter out = response.getWriter()) {
            HttpSession atrybutSesji = request.getSession();
            
            String sessionID = atrybutSesji.getId();
            String state = request.getParameter("state");
            String client_id = "c202e05aa5d347065897d43ca9ce438d5b668841";
            String client_secrets = "oDTevlq8YHq9xNZ75Cez3Ul/7L7Ocel68lA9k+Yk9X+CPRWqJsa63WXKVDiskgzbS5/U32kz+PR2/8fGFpylm01llDlp+RgDUn5Loh5cQhSKnEO9/2h+LjaQ15YvdK0z";
            String code = request.getParameter("code");
            if(code != null)
            {
                String redirect_uri= "http://localhost:8084/RSI-NetBeans/ReturnVimeo";

               
               String token_ans = GetVimeoToken("redirect_uri=" + redirect_uri + "&code=" + code + "&grant_type=authorization_code" );
               //out.print(token_ans);

               JSONObject obj = new JSONObject(token_ans);
               JSONObject user = obj.getJSONObject("user");
               JSONArray pictures = user.getJSONArray("pictures");
              
               atrybutSesji.setAttribute("codeVimeo", code);
               atrybutSesji.setAttribute("nameVimeo", user.getString("name"));
               atrybutSesji.setAttribute("pictureLinkVimeo", pictures.getJSONObject(1).getString("link"));
               //zapis do ciasteczek
               request.getRequestDispatcher("/showVimeoUser.jsp").forward(request, response);

            }else{

                out.print("<h1>BLAD!!!</h1>");
            }
        }
        
       
        
        processRequest(request, response);
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void BASE64Encoder(String asas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    private String GetVimeoToken(String params) throws MalformedURLException, IOException{
            URL url = new URL("https://api.vimeo.com/oauth/access_token");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization", "Basic YzIwMmUwNWFhNWQzNDcwNjU4OTdkNDNjYTljZTQzOGQ1YjY2ODg0MTpvRFRldmxxOFlIcTl4Tlo3NUNlejNVbC83TDdPY2VsNjhsQTlrK1lrOVgrQ1BSV3FKc2E2M1dYS1ZEaXNrZ3piUzUvVTMya3orUFIyLzhmR0ZweWxtMDFsbERscCtSZ0RVbjVMb2g1Y1FoU0tuRU85LzJoK0xqYVExNVl2ZEsweg==");
        connection.setRequestProperty("Content-Length", "" + params.length());
        String c = "" + params.length();
                
        OutputStreamWriter out = new OutputStreamWriter(
        connection.getOutputStream());
        out.write(params);
        out.close();
        
        
        // Read response
        String responseSB = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));

        String line;
        while ((line = br.readLine()) != null)
            responseSB += line;

        // Close streams
        br.close();
        return responseSB;

        
    }
    
    
    
}
