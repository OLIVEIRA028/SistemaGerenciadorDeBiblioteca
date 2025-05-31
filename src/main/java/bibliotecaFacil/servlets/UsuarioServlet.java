protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String nome = request.getParameter("nome");
    int matricula = Integer.parseInt(request.getParameter("matricula"));
    String cpf = request.getParameter("cpf");
    String email = request.getParameter("email");
    String tipo = request.getParameter("tipo");

    Usuario usuario;
    if ("Aluno".equalsIgnoreCase(tipo)) {
        usuario = new Aluno(matricula, nome, cpf, email);
    } else {
        usuario = new Professor(matricula, nome, cpf, email);
    }

    UsuarioDAO dao = new UsuarioDAO();
    dao.inserir(usuario);

    response.sendRedirect("sucesso.html");
}
