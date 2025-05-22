<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Fontes.CriptografiaPersonalizada" %>
<%@ page import="Fontes.UsuarioDAO" %>
<%@ page import="java.io.*, javax.servlet.*, javax.servlet.http.*" %>


<%
    String mensagem = null;
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String email = request.getParameter("email");
        String senha1 = request.getParameter("senha1");
        String senha2 = request.getParameter("senha2");

        if (!senha1.equals(senha2)) {
            mensagem = "Senhas não coincidem. Tente novamente.";
        } else {
            try {
                String senhaCriptografada = CriptografiaPersonalizada.criptografar(senha1);
                UsuarioDAO dao = new UsuarioDAO();
                dao.alterarSenha(email, senhaCriptografada);
                mensagem = "Senha redefinida com sucesso!";
            } catch (Exception e) {
                mensagem = "Erro ao atualizar senha: " + e.getMessage();
            }
        }
    }
%>

<html>
<head>
    <title>Redefinir Senha</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <link rel="icon" type="image/x-icon" href="favicons/icone.ico">
    <style>
        .input-group-text {
            cursor: pointer;
        }
    </style>
</head>
<body class="container mt-5">

<h3>Redefinir Senha</h3>
<form method="post" onsubmit="return validarSenhas();">
    <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input type="email" name="email" id="email" class="form-control" required />
    </div>

    <div class="mb-3">
        <label for="senha1" class="form-label">Nova Senha:</label>
        <div class="input-group">
            <input type="password" name="senha1" id="senha1" class="form-control" required />
            <span class="input-group-text" onclick="toggleSenha('senha1', this)">
                <i class="bi bi-eye-slash" id="icon-senha1"></i>
            </span>
        </div>
    </div>

    <div class="mb-3">
        <label for="senha2" class="form-label">Confirmar Senha:</label>
        <div class="input-group">
            <input type="password" name="senha2" id="senha2" class="form-control" required />
            <span class="input-group-text" onclick="toggleSenha('senha2', this)">
                <i class="bi bi-eye-slash" id="icon-senha2"></i>
            </span>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Redefinir</button>
</form>

<!-- Modal Bootstrap -->
<div class="modal fade" id="modalErro" tabindex="-1" aria-labelledby="modalErroLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content text-bg-danger">
            <div class="modal-header">
                <h5 class="modal-title" id="modalErroLabel">Erro</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
                Senhas não coincidem. Tente novamente.
            </div>
        </div>
    </div>
</div>

<%
    if (mensagem != null && mensagem.contains("Senhas não coincidem")) {
%>
<script>
    window.onload = function() {
        var modal = new bootstrap.Modal(document.getElementById('modalErro'));
        modal.show();
    }
</script>
<%
    } else if (mensagem != null) {
%>
<div class="alert alert-info mt-4"><%= mensagem %></div>
<%
    }
%>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function validarSenhas() {
        const senha1 = document.getElementById("senha1").value;
        const senha2 = document.getElementById("senha2").value;
        if (senha1 !== senha2) {
            var modal = new bootstrap.Modal(document.getElementById('modalErro'));
            modal.show();
            return false;
        }
        return true;
    }

    function toggleSenha(inputId, span) {
        const input = document.getElementById(inputId);
        const icon = span.querySelector("i");
        if (input.type === "password") {
            input.type = "text";
            icon.classList.remove("bi-eye-slash");
            icon.classList.add("bi-eye");
        } else {
            input.type = "password";
            icon.classList.remove("bi-eye");
            icon.classList.add("bi-eye-slash");
        }
    }
</script>

</body>
</html>
