package com.config;


import com.DAO.UserDao;
import com.Model.UserModel;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter
{
	static Logger logger=LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private UserDao loginservice;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException
	{
		final String requestTokenHeader = request.getHeader("token");
		//logger.info("incoming request :"+requestTokenHeader.toString());
		String username = null;
		String userid = null;
		String useerid=null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
			jwtToken = requestTokenHeader.substring(6);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				Claims claim=jwtTokenUtil.getAllClaimsFromToken(jwtToken);
				userid= (String) claim.get("userid");
				useerid=userid+"";
			} catch (IllegalArgumentException e) {
				logger.info("Unable to get JWT Token");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Error");
				return;

			} catch (Exception e) {
				logger.info("JWT Token has expired");
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
				return;

			}
		} else {
			logger.info("JWT Token does not begin with Bearer String");
		}

		// Once we get the token validate it.
		if (username != null && useerid!=null && SecurityContextHolder.getContext().getAuthentication() == null) {

			Optional<UserModel> userDetailsO = this.loginservice.findById(Integer.valueOf(useerid));
			UserModel userDetails=null;
			if(userDetailsO.isPresent())
			{
				userDetails=userDetailsO.get();
			// if token is valid configure Spring Security to manually set
			// authentication
				logger.info("token validation "+jwtTokenUtil.validateToken(jwtToken, new User(userDetails.getUsername(), Arrays.toString(userDetails.getPassword()), new ArrayList<>())));
				if (jwtTokenUtil.validateToken(jwtToken, new User(userDetails.getUsername(), Arrays.toString(userDetails.getPassword()), new ArrayList<>()))) {
	
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, null);
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					// After setting the Authentication in the context, we specify
					// that the current user is authenticated. So it passes the
					// Spring Security Configurations successfully.
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
				else {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

}