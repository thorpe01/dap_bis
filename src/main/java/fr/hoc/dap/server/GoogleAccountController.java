package fr.hoc.dap.server;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.hoc.dap.server.data.DapUser;
import fr.hoc.dap.server.data.DapUserRepository;
import fr.hoc.dap.server.service.GoogleService;

//TODO brs by Djer |JavaDoc| Il manque la JavaDoc
/**
 * Controller tout pourrie qui étend un Service
 * @author house
 *
 */
@Controller
public class GoogleAccountController extends GoogleService {
    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    private static final Logger LOG = LogManager.getLogger();
    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;
    //TODO brs by Djer |JavaDoc| Il manque la JavaDoc
    private static final int SENSIBLE_DATA_LAST_CHAR = 0;

    /**
     * Handle the Google response.
     * 
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException         When Google account could not be connected
     *                                  to DaP.
     * @throws GeneralSecurityException
     */
    @Autowired
    private DapUserRepository dapUserRepo;

    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException {

        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, getLaConf().getoAuth2CallbackUrl());

        final String userId = getUserid(session);

        final String loginName = getloginName(session);

        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
            } else {

                //TODO : 1b- récupérer le loginName (jette un oeil a getUserid(); (fait)
                //TODO : 1b- SAUVEAGRDER en BDD (fait)
                DapUser monUser = new DapUser();
                monUser.setLoginName(loginName);
                monUser.setUserkey(userId);
                dapUserRepo.save(monUser);

            }

            if (LOG.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/user-added";
    }

    private String getloginName(HttpSession session) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * retrieve the User ID in Session.
     * 
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserid(final HttpSession session) throws ServletException {
        String userId = null;
        if (null != session && null != session.getAttribute("userId")) {
            userId = (String) session.getAttribute("userId");
        }

        if (null == userId) {
            LOG.error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return userId;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * 
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final HttpServletRequest request) throws ServletException {
        final StringBuffer buf = request.getRequestURL();
        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }
        final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        final String decodeCode = responseUrl.getCode();

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {
            LOG.error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
            // onError(request, resp, responseUrl);
        }

        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     * 
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /* Add a Google account (user will be prompt to connect and accept required
    * access).
    * @param userId  the user to store Data
    * @param request the HTTP request
    * @param session the HTTP session
    * @return the view to Display (on Error)
    */
    //TODO 0a - Demander le loginName

    @RequestMapping("/account/add/{loginName}")
    public String addAccount(@PathVariable final String loginName, @RequestParam final String userId,
            final HttpServletRequest request, final HttpSession session) {

        //TODO ????? Vérifier que le userKey nb'est pas déja utilisé ?

        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(loginName);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getLaConf().getoAuth2CallbackUrl()));
                // store userId in session for CallBack Access
                session.setAttribute("userId", userId);
                session.setAttribute("loginName", loginName);
                //TODO 0b - stocker temporairement le loginName
                response = "redirect:" + authorizationUrl.build();
            }

        } catch (IOException | GeneralSecurityException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }

}
