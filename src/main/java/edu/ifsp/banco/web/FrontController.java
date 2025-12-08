package edu.ifsp.banco.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.ifsp.banco.administrador.ConsultarUsuariosCommand;
import edu.ifsp.banco.administrador.LiberarUsuarioCommand;
import edu.ifsp.banco.login.LoginCommand;
import edu.ifsp.banco.login.LogoutCommand;
import edu.ifsp.banco.web.emprestimo.AprovarEmprestimoCommand;
import edu.ifsp.banco.web.emprestimo.ListarSolicitacoesCommand;
import edu.ifsp.banco.web.emprestimo.MeusEmprestimosCommand;
import edu.ifsp.banco.web.emprestimo.PagarParcelaCommand;
import edu.ifsp.banco.web.emprestimo.PrepararSimulacaoCommand;
import edu.ifsp.banco.web.emprestimo.SimularEmprestimoCommand;
import edu.ifsp.banco.web.emprestimo.SolicitarEmprestimoCommand;
import edu.ifsp.banco.web.helpers.RedirectCommand;
import edu.ifsp.banco.web.movimentacao.DepositoCommand;
import edu.ifsp.banco.web.movimentacao.EscolherInvestimentoCommand;
import edu.ifsp.banco.web.movimentacao.ExtratoCommand;
import edu.ifsp.banco.web.movimentacao.FinalizarInvestimentoCommand;
import edu.ifsp.banco.web.movimentacao.InvestimentoCommand;
import edu.ifsp.banco.web.movimentacao.MeusInvestimentosCommand;
import edu.ifsp.banco.web.movimentacao.TransferenciaCommand;
import edu.ifsp.banco.web.usuario.AtualizarDadosUsuarioCommand;
import edu.ifsp.banco.web.usuario.CadastrarUsuarioCommand;
import edu.ifsp.banco.web.usuario.DadosUsuarioCommand;
import edu.ifsp.banco.web.usuario.DashboardCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Command> commands = new HashMap<>();

	@Override
	public void init() throws ServletException {
		commands.put("cadastrarUsuario", new CadastrarUsuarioCommand());
		commands.put("depositar", new DepositoCommand());
		commands.put("login", new LoginCommand());
		commands.put("redirect", new RedirectCommand());
		commands.put("consultarBloqueados", new ConsultarUsuariosCommand());
		commands.put("liberarUsuario", new LiberarUsuarioCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("investimento", new InvestimentoCommand());
		commands.put("escolherInvestimento", new EscolherInvestimentoCommand());
		commands.put("finalizarInvestimento", new FinalizarInvestimentoCommand());
		commands.put("meusInvestimentos", new MeusInvestimentosCommand());
		commands.put("transferir", new TransferenciaCommand());
		commands.put("atualizarUsuario", new AtualizarDadosUsuarioCommand());
		commands.put("mostrarDadosUsuario", new DadosUsuarioCommand());
		commands.put("simularEmprestimo", new SimularEmprestimoCommand());
		commands.put("solicitarEmprestimo", new SolicitarEmprestimoCommand());
		commands.put("aprovarEmprestimo", new AprovarEmprestimoCommand());
		commands.put("pagarParcela", new PagarParcelaCommand());
		commands.put("dashboardCliente", new DashboardCommand());
		commands.put("prepararSimulacao", new PrepararSimulacaoCommand());
		commands.put("listarEmprestimosGerente", new ListarSolicitacoesCommand());
		commands.put("meusEmprestimos", new MeusEmprestimosCommand());
		commands.put("extrato", new ExtratoCommand());
		//coloquem aqui
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("command");

		if (acao == null || !commands.containsKey(acao)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comando inválido");
			return;
		}

		Command command = commands.get(acao);
		command.execute(request, response);
	}
	
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	service(req, resp);
    }
}