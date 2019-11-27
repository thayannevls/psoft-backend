package psoft.ufcg.api.AJuDE.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

public class TokenFilter extends GenericFilterBean {

  public final static int TOKEN_INDEX = 7;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
          throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) request;

    if (req.getRequestURI().contains("/campanhas/") && req.getMethod().equals("GET") && !req.getRequestURI().contains("/campanhas/search")) {
		chain.doFilter(request, response);
		return;
    }
    
    String header = req.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
              "É necessário estar autenticado para acessar esse recurso.");
      return;
    }

    String token = header.substring(TOKEN_INDEX);
    try {
      Jwts.parser().setSigningKey("segredo").parseClaimsJws(token).getBody();
    } catch (SignatureException e) {
		throw new ServletException("Authorization Token inválido.");
	}

    chain.doFilter(request, response);
  }

}
