
package Fontes;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String senhaCriptografada = CriptografiaPersonalizada.criptografar(senha);

        try {
            Usuario usuario = new UsuarioDAO().autenticar(email, senhaCriptografada);
            if (usuario != null) {
                resp.sendRedirect("sucesso.jsp");
            } else {
                resp.sendRedirect("login.jsp?erro=Credenciais%20inválidas");
            }
        } catch (Exception e) {
            resp.sendRedirect("login.jsp?erro=Erro%20no%20login");
        }
    }
}
