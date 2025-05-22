package Fontes;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String senhaCriptografada = CriptografiaPersonalizada.criptografar(senha);

        Usuario user = new Usuario();
        user.setEmail(email);
        user.setSenha(senhaCriptografada);

        try {
            new UsuarioDAO().cadastrar(user);
            resp.sendRedirect("login.jsp?msg=Cadastro%20sucesso");
        } catch (Exception e) {
            resp.sendRedirect("cadastro.jsp?erro=Erro%20ao%20cadastrar");
        }
    }
}
