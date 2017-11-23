package dados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import negocio.Equipamentos;
import negocio.Funcionario;
import negocio.Reserva;

public class EmprestimosBD {
	private Connection conexao;
	public EmprestimosBD() throws SQLException{
		conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
}
	public void sair() {
		try {
			conexao.close();
		} catch (SQLException e) {
			System.out.println("Erro ao fechar conexão");
		}	
	}
	public ArrayList<String> getAllFuncionarios() {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select funcionarios.nome_completo from funcionario order by funcionarios.nome_completo asc";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
			ResultSet registros = consultaSQL.executeQuery(); 
			ArrayList<String> allFuncionarios = new ArrayList<String>();
	
		    while(registros.next()){
		    	allFuncionarios.add(registros.getString("nome_completo"));
		    }
		    registros.close();
		    consultaSQL.close();
			return allFuncionarios;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}
	public ArrayList<Funcionario> getFuncionarioPorNome(String nome) {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select nro_matricula, data_nasc, data_admissao, sexo, endreco, salario_mensal from funcionarios where funcionario.nome= "+"''"+ nome + "''";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
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
			return filtroFunct;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}

	public ArrayList<Equipamentos> getEquipamentoPorDescricao(String descricao) {
		try {
			Connection conexao = DriverManager.getConnection(BDInfo.SERVER, BDInfo.USER, BDInfo.PASSWORD);
			String comandoSQL = "select identificador_equip, data_aquisicao, descricao, custo_diario_uso, em_manutencao, tipo from equipamentos where equipamentos.descricao like(%" + 
					"''"+ descricao + "''" + "%)";
			PreparedStatement consultaSQL = conexao.prepareStatement(comandoSQL);
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
			return filtroEquip;
		}
		catch (SQLException e) {
			System.out.println("Erro ao instanciar o banco de dados");
			return null;
		}
		
	}
	public void relatorioReservasFuturas() {
		// TODO Auto-generated method stub
		
	}
	public void qtdReservasEcustoPorFunc() {
		// TODO Auto-generated method stub
		
	}
	public void qtdReservasEcustoDeEquip(Equipamentos equip) {
		// TODO Auto-generated method stub
		
	}
	public void realizaReserva(Reserva reserva) {
		// TODO Auto-generated method stub
		
	}

	
}
