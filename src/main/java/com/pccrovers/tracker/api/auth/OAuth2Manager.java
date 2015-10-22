package com.pccrovers.tracker.api.auth;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2Scopes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class OAuth2Manager
{
    private final AuthorizationCodeFlow flow;
    private final String userId;

    public OAuth2Manager(String userId)
    {
        this(userId, Collections.singleton(Oauth2Scopes.USERINFO_EMAIL));
    }

    public OAuth2Manager(String userId, String... scopes)
    {
        this(userId, Arrays.asList(scopes));
    }

    public OAuth2Manager(String userId, Collection<String> scopes)
    {
        try
        {
            flow = initializeFlow(scopes);
            this.userId = userId;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public String createAuthorizationURL(String redirectUri)
    {
        return flow.newAuthorizationUrl().setRedirectUri(redirectUri).set("something", "cool").build();
    }

    public Credential createAndStoreCredential(HttpServletRequest req, String redirectUrl) throws IOException
    {
        StringBuffer buf = req.getRequestURL();
        if(req.getQueryString() != null) {
            buf.append('?').append(req.getQueryString());
        }

        AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        String code = responseUrl.getCode();

        if(responseUrl.getError() != null) {
            throw new RuntimeException(responseUrl.getError());
        } else if(code == null) {
            throw new RuntimeException("Missing authorization code");
        } else {
            return createAndStoreCredential(code, redirectUrl);
        }
    }

    public Credential createAndStoreCredential(String code, String redirectUrl) throws IOException
    {
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUrl).execute();
        return flow.createAndStoreCredential(response, userId);
    }

    public Credential getStoredCredential() throws IOException
    {
        return flow.loadCredential(userId);
    }

    public Collection<String> getScopes()
    {
        return flow.getScopes();
    }

    public boolean hasAccessTo(String gapiEndpoint)
    {
        return getScopes().contains(gapiEndpoint);
    }

    public boolean isAuthorized()
    {
        try
        {
            return getStoredCredential().getExpiresInSeconds() > 0;
        }
        catch (IOException e)
        {
            return false;
        }
    }

    protected AuthorizationCodeFlow initializeFlow(Collection<String> scopes) throws ServletException, IOException
    {
        return new GoogleAuthorizationCodeFlow.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                GoogleClientSecrets.load(new JacksonFactory(), new InputStreamReader(new FileInputStream(new File("WEB-INF/client_secrets.json")))),
                scopes
        ).setCredentialDataStore(StoredCredential.getDefaultDataStore(AppEngineDataStoreFactory.getDefaultInstance())).build();
    }
}
