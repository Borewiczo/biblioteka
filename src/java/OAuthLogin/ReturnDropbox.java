/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OAuthLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import org.json.JSONObject;
/**
 *
 * @author Konrad
 */
@WebServlet(name = "ReturnDropbox", urlPatterns = {"/ReturnDropbox"})
public class ReturnDropbox extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        session.removeAttribute("nameDropbox");
        session.removeAttribute("surnameDropbox");
        session.removeAttribute("emailDropbox");
        session.removeAttribute("countryDropbox");
        session.removeAttribute("msgDropbox");
         
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Odpowiedź z Dropbox</title>");            
            out.println("</head>");
            out.println("<body><a class=\"btn btn-primary btn-lg\" href=\"./\">Powrót</a>");
            
            String code = request.getParameter("code");
            
            if (code != null){
                out.println("<h1>Uwierzytelniono!</h1>");
                
                out.println("<p>GOT CODE = " + code  + "</p>");
            
                String getTokenUrl = "https://api.dropboxapi.com/1/oauth2/token?code=" + request.getParameter("code") + "&grant_type=authorization_code&redirect_uri=http://localhost:8084/RSI-NetBeans/ReturnDropbox";
                String TokenJson = GetDropboxToken(getTokenUrl);
                out.println("<p>GOT TOKEN JSON:</p>");
                out.println(TokenJson);

                JSONObject obj = new JSONObject(TokenJson);
                String n = obj.getString("access_token");
                out.println("<p>TOKEN = " + n + "</p>");

                String info = GetDropboxUserInfo(n);
                out.println("<p>GOT USER JSON:</p>");
                out.println(info);

                JSONObject obj2 = new JSONObject(info);
                JSONObject nazwa = obj2.getJSONObject("name_details");
                out.println("<h2><p>KRAJ = " + obj2.getString("country") + "</p>");
                out.println("<p>IMIĘ = " + nazwa.getString("given_name") + "</p>");
                out.println("<p>NAZWISKO = " + nazwa.getString("surname") + "</p>");
                out.println("<p>E-MAIL = " + obj2.getString("email") + "</p></h2>");
                
                
                session.setAttribute("nameDropbox", nazwa.getString("given_name"));
                session.setAttribute("surnameDropbox", nazwa.getString("surname"));
                session.setAttribute("emailDropbox", obj2.getString("email"));
                session.setAttribute("countryDropbox", obj2.getString("country"));
                session.setAttribute("msgDropbox", "Zalogowano!");
                
            }else{
                session.setAttribute("msgDropbox", "Nie zalogowano! " + request.getParameter("error_description"));

                out.println("<h1>Nie zalogowano!</h1>");
                out.println("<h2>" + request.getParameter("error_description") + "</h2>");
            }
            
            request.getRequestDispatcher("/showDropboxUser.jsp").forward(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    String GetDropboxToken(String uri) throws MalformedURLException, IOException{
            URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization", "Basic OGQ1eTVpOXlqcmJra3RpOnN2Ym42Z3JsOTZlNGo5eA==");

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
    
    String GetDropboxUserInfo(String token) throws MalformedURLException, IOException{
            URL url = new URL("https://api.dropboxapi.com/1/account/info");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Authorization", "Bearer " + token);

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

}
