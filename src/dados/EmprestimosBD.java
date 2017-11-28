package dados;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
import negocio.Equipamentos;
import negocio.Funcionario;
import negocio.Reserva;

public class EmprestimosBD {
	private Connection conexao;
	private static EmprestimosBD ref = null;
	public EmprestimosBD() throws SQLException{
		conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
}
	
	public static EmprestimosBD getInstance() throws ClassNotFoundException, SQLException {
		if (ref == null)
			ref = new EmprestimosBD();
		return ref;
	}
	//consulta 0
	public Boolean sair() {
		boolean sucesso = false;
		try {
			conexao.close(); //só para garantir fechamos novamente, como foi visto na aula que foi fechado duas vezespara garantir
			sucesso = true;
		} catch (SQLException e) {
			System.out.println("Erro ao fechar conex�o");
		}	
		return sucesso;
	}
	//consulta 01
	public ArrayList<Funcionario> getAllFuncionarios() {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select * from funcionarios order by funcionarios.nome_completo asc";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<Funcionario> allFuncionarios = new ArrayList<Funcionario>();
	
		    while(registros.next()){
		    	allFuncionarios.add(
		    			new Funcionario(registros.getString("nro_matricula"), registros.getDate("data_nasc"), registros.getDate("data_admissao"),
    					registros.getString("sexo"), registros.getString("nome_completo"), registros.getString("endereco"), registros.getDouble("salario_mensal")));
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return allFuncionarios;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}
	//consulta 02
	public ArrayList<Funcionario> getFuncionarioPorNome(String nome) {
		try {	
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = ("SELECT * FROM FUNCIONARIOS WHERE UPPER(FUNCIONARIOS.nome_completo) LIKE ?");
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			consultaSQL.setString(1, "%" + nome + "%");
			ResultSet registros = consultaSQL.executeQuery();
			ArrayList<Funcionario> filtroFunct = new ArrayList<Funcionario>();

		    while(registros.next()){
		    	filtroFunct.add(
		    			new Funcionario(registros.getString("nro_matricula"), registros.getDate("data_nasc"), registros.getDate("data_admissao"),
		    					registros.getString("sexo"), registros.getString("nome_completo"), registros.getString("endereco"), registros.getDouble("salario_mensal"))
		    			);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return filtroFunct;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			e.printStackTrace();
			return null;
		}
		
	}
//consulta 03
	public ArrayList<Equipamentos> buscaEquipPorDescricao(String descricao) {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select identificador_equip, data_aquisicao, descricao, custo_diario_uso, em_manutencao, tipo from equipamentos where UPPER(equipamentos.descricao) like ?";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			consultaSQL.setString(1, "%" + descricao + "%");
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<Equipamentos> filtroEquip = new ArrayList<Equipamentos>();
	
		    while(registros.next()){
		    	filtroEquip.add(
		    			new Equipamentos(registros.getString("identificador_equip"), registros.getDate("data_aquisicao"),
		    					registros.getString("descricao"), registros.getDouble("custo_diario_uso"),registros.getString("em_manutencao"),registros.getString("tipo"))
		    			);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return filtroEquip;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}
	//consulta 04
	public boolean realizaReserva(Reserva reserva) throws SQLException {
		boolean sucesso = false;
	
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQLQtdReservas ="SELECT seq_emprestimos.NEXTVAL as sequencia FROM DUAL";
			PreparedStatement consultaSQLQtdReservas = conexao.prepareStatement(comandoSQLQtdReservas);
			ResultSet resultado = consultaSQLQtdReservas.executeQuery();
			while(resultado.next()){
				reserva.setIdentificador_emprestimo(resultado.getInt("sequencia"));	
			}
			String comandoSQL = "INSERT INTO EMPRESTIMOS(identificador_emprestimo, data_inicial_reserva, data_final_reserva,nro_matricula,identificador_equip)  VALUES(?, ?, ?, ?, ?) ";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			consultaSQL.setInt(1, reserva.getIdentificador_emprestimo());
			consultaSQL.setDate(2, Date.valueOf(reserva.getData_inicial_reserva()));
			consultaSQL.setDate(3, Date.valueOf(reserva.getData_final_reserva()));
			consultaSQL.setString(4, reserva.getNro_matricula());
			consultaSQL.setString(5,reserva.getIdentificador_equip());

			if(consultaSQL.executeUpdate() > 0){
				sucesso = true;
			}
			consultaSQL.close();
			conexao.close();
		
		return sucesso;
	}
	//consulta 05 (quem reservou qual equipamento e para qual período)
	public ArrayList<String> relatorioReservasFuturas() {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select nome_completo, nro_matricula, identificador_equip, data_inicial_reserva, data_final_reserva from funcionarios join emprestimos using(nro_matricula) join equipamentos using (identificador_equip) where data_inicial_reserva > SYSDATE group by nome_completo,nro_matricula, identificador_equip, data_inicial_reserva, data_final_reserva order by data_inicial_reserva";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<String> filtroEquipFunctData = new ArrayList<String>();
	
		    while(registros.next()){
		    	filtroEquipFunctData.add("Nome do Funcionário: " + registros.getString("nome_completo")
		    	+ ", Número matrícula: " + registros.getString("nro_matricula")
		    	+ ", Data Inicial Reserva: " + registros.getDate("data_inicial_reserva")
		    	+ ", Data Final da Reserva: " + registros.getDate("data_final_reserva")
		    	+ ", Identificador do equipamento: " + registros.getString("identificador_equip")
		    	);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return filtroEquipFunctData;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}

	public ArrayList<String> qtdReservasEquipECusto(String equipId) {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select identificador_equip, count(*) as qtd_reservas, SUM(custo_diario_uso * (data_final_reserva -  data_inicial_reserva)) as somaCustos from equipamentos  join emprestimos using(identificador_equip)  where identificador_equip = ? group by identificador_equip,custo_diario_uso";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			consultaSQL.setString(1,equipId);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<String> qtdReservasECusto = new ArrayList<String>();
	
		    while(registros.next()){
		    	qtdReservasECusto.add("Custo total das reservas feitas: " + registros.getString("somaCustos")
		    	+ ", Quantidade de Reservas: " + registros.getString("qtd_reservas")
		    	);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return qtdReservasECusto;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}
	public ArrayList<String> listarNumeroDeReservasECustoTotalporFunct() {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select nro_matricula, identificador_emprestimo as nro_reserva, SUM(custo_diario_uso * (data_final_reserva - data_inicial_reserva)) as precoTotal from funcionarios join emprestimos using(nro_matricula) join equipamentos using(identificador_equip) group by nro_matricula, identificador_emprestimo";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<String> qtdReservasECusto = new ArrayList<String>();
	
		    while(registros.next()){
		    	qtdReservasECusto.add("Número de matrícula: " + registros.getString("nro_matricula")
		    	+ ", Identificador de Reserva: " + registros.getString("nro_reserva")
		    	+ ", Preço total: R$" + registros.getString("precoTotal")
		    	);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return qtdReservasECusto;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}

	public ArrayList<String> listaFuncionariosSemReserva() {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select funcionarios.nro_matricula,funcionarios.nome_completo from funcionarios where funcionarios.nro_matricula NOT IN (SELECT emprestimos.nro_matricula FROM emprestimos) ORDER BY funcionarios.nome_completo ASC";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<String> functSemReservas = new ArrayList<String>();
	
		    while(registros.next()){
		    	functSemReservas.add("Número de matrícula: " + registros.getString("nro_matricula")
		    	+ ", Nome do funcionário: " + registros.getString("nome_completo")
		    	);
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return functSemReservas;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}

	public boolean checkDisponibilidade(Reserva reserva) { //RETORNA DIFERENTE DE NULL SE ACHOU ALGUMA DATA CONFLITANTE
		boolean sucesso = false;
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select data_final_reserva, data_inicial_reserva  from  emprestimos where (data_inicial_reserva between ? AND ? OR data_final_reserva between ? and ? OR ( data_inicial_reserva < ? and  ? < data_final_reserva ) ) AND identificador_equip = ?";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			consultaSQL.setDate(1, Date.valueOf(reserva.getData_inicial_reserva()));
			consultaSQL.setDate(2, Date.valueOf(reserva.getData_final_reserva()));
			consultaSQL.setDate(3, Date.valueOf(reserva.getData_inicial_reserva()));
			consultaSQL.setDate(4, Date.valueOf(reserva.getData_final_reserva()));
			consultaSQL.setDate(5, Date.valueOf(reserva.getData_inicial_reserva()));
			consultaSQL.setDate(6, Date.valueOf(reserva.getData_final_reserva()));
			consultaSQL.setString(7, reserva.getIdentificador_equip());
			ResultSet registros = consultaSQL.executeQuery(); 
			
		    if(!registros.isBeforeFirst()){
		    	sucesso = true;
		    }
		    registros.close();
		    consultaSQL.close();
		    conexao.close();
			return sucesso;
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao instanciar o banco de dados");
			return sucesso;
		}
	}
	
}
