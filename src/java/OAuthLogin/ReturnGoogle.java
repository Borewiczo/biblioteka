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
import org.json.JSONObject;

/**
 *
 * @author Lukas
 */
@WebServlet(name = "ReturnGoogle", urlPatterns = {"/ReturnGoogle"})
public class ReturnGoogle extends HttpServlet {

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
        
        try (PrintWriter out = response.getWriter()) {
            
            String state = request.getParameter( "state" );
            String code  = request.getParameter( "code" );
            String error = request.getParameter( "error" );
            
            String client_id     = "155989068474-4ijjgsl586bohlcnap62shia4e7a1hla.apps.googleusercontent.com";
            String client_secret = "7GELoIdsx9FHpMNppfvVyiBj";
            String redirect_uri  = "http://localhost:8084/RSI-NetBeans/ReturnGoogle";
            // wow, such safety, much secure, wow xD
            
            if( code != null )
            {
                String googleParams = 
                          "code=" + code 
                        + "&client_id=" + client_id 
                        + "&client_secret=" + client_secret 
                        + "&redirect_uri=" + redirect_uri 
                        + "&grant_type=authorization_code";
                
                
                String googleOutput = GetGoogleToken( googleParams );
                
                JSONObject jsonResponse = new JSONObject( googleOutput );
                String access_token     = jsonResponse.getString( "access_token" );
                String token_type       = jsonResponse.getString( "token_type" );
                int expires_in          = jsonResponse.getInt( "expires_in" );
                
                String refresh_token = null;
                String id_token = null;
                if( jsonResponse.has( "refresh_token" )) { refresh_token = jsonResponse.getString( "refresh_token" ); }
                if( jsonResponse.has( "id_token" ))      { id_token = jsonResponse.getString( "id_token" ); }
                
                
                String googleUserInfo = GetBasicUserInfo( access_token );
                String greeting = "";
                JSONObject jsonUserInfo = new JSONObject( googleUserInfo );
                if( jsonUserInfo.has( "given_name" ))
                {
                    greeting += " " + jsonUserInfo.getString( "given_name" );
                    if( jsonUserInfo.has( "family_name" ))
                    {
                        greeting += " " + jsonUserInfo.getString( "family_name" );
                    }
                    
                    greeting += " (" + jsonUserInfo.getString( "email" ) + ")";
                }
                else
                {
                    greeting += " " + jsonUserInfo.getString( "email" );
                }
                
                
                // print out dat stuff xD
                out.println( "<h1>Pomyślnie zalogowano!</h1>" );
                out.println( "<h1>Witaj" + greeting + "!</h1>" );
                
                out.println( "<h3>Access token:</h3>" );
                out.println( access_token );
                out.println( "<h3>Token type:</h3>" );
                out.println( token_type );
                out.println( "<h3>Expires in:</h3>" );
                out.println( expires_in );
            }
            else
            {
                if( error.equals( "access_denied" ))
                {
                    out.println( "<h1>Logowanie nieudane: odmowa dostępu!</h1>" );
                }
                else
                {
                    out.println( "<h1>Logowanie nieudane: inny błąd!</h1>" );
                    out.println( "<h2>" + error + "</h2>" );
                }
            }
        }
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

        
    private String GetGoogleToken( String params )
            throws MalformedURLException, IOException {
        
        // req
        URL url = new URL( "https://www.googleapis.com/oauth2/v4/token" );
        
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.setDoOutput( true );
        connection.setRequestMethod( "POST" );
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
        connection.setRequestProperty( "Content-Length", "" + params.length());
        
        try ( OutputStreamWriter out = new OutputStreamWriter (
                connection.getOutputStream())) {
            out.write( params );
            out.close();
        }
        
        // resp
        String response = "";
        try ( BufferedReader br = new BufferedReader( new InputStreamReader(
                connection.getInputStream()))) {
            String line;
            while (( line = br.readLine()) != null ) {
                response += line;
            }
            br.close();
        }
        
        return response;
    }
    
    
    private String GetBasicUserInfo( String access_token )
            throws MalformedURLException, IOException {
        
        // https://developers.google.com/+/web/api/rest/oauth
        // https://developers.google.com/oauthplayground/
        
        // req
        URL url = new URL( "https://www.googleapis.com/oauth2/v2/userinfo" );
        
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.setDoOutput( true );
        connection.setRequestMethod( "GET" );
        connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
        connection.setRequestProperty( "Content-Length", "0" );
        connection.setRequestProperty( "Authorization", "Bearer " + access_token );
        
        // no body to send
        // no code below
        // REMEMBER!
        /*try ( OutputStreamWriter out = new OutputStreamWriter (
                connection.getOutputStream())) {
            out.write( "" );
            out.close();
        }*/
        // otherwise you will get some dank java errors xD
        
        // resp
        String response = "";
        try ( BufferedReader br = new BufferedReader( new InputStreamReader(
                connection.getInputStream()))) {
            String line;
            while (( line = br.readLine()) != null ) {
                response += line;
            }
            br.close();
        }
        
        return response;
    }
    
}
