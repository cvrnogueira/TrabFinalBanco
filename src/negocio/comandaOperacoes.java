package negocio;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dados.EmprestimosBD;
import javafx.util.Pair;

public class comandaOperacoes {

	static comandaOperacoes instance;
	
	public comandaOperacoes(){
		
	}
	public static comandaOperacoes getInstance() {
		if(instance == null){
			instance = new comandaOperacoes();
		}		
		return(instance);	
	}
	//caso zero
	public boolean sair() throws SQLException {
		try {
			return EmprestimosBD.getInstance().sair();
		} 
		catch (ClassNotFoundException e) {
		}
		return false;
	}
	//caso 01
	public ArrayList<Funcionario> listaFuncionarios() throws SQLException {
		try {
			return EmprestimosBD.getInstance().getAllFuncionarios();
		} 
		catch (ClassNotFoundException e) {
		}
		return null;
		
	}
	//caso 02
	public ArrayList<Funcionario> buscaFuncionarioPorNome(String nome) throws SQLException {
		try {
			return EmprestimosBD.getInstance().getFuncionarioPorNome((nome).toUpperCase());
		} 
		catch (ClassNotFoundException e) {
		}
		return null;
		
	}
	//caso 03
		public ArrayList<Equipamentos> buscaEquipPorDescricao(String descricao) throws SQLException {
			try {
				return EmprestimosBD.getInstance().buscaEquipPorDescricao(descricao.toUpperCase());
			} 
			catch (ClassNotFoundException e) {
			}
			return null;
	}
		//caso 04
	public boolean fazerNovaReserva(Reserva realizaReserva) throws SQLException {
		try {
			if(EmprestimosBD.getInstance().checkDisponibilidade(realizaReserva)){
				return EmprestimosBD.getInstance().realizaReserva(realizaReserva);
			}
			else{
				return false;
			}
		} 
		catch (ClassNotFoundException e) {
		}
		return false;
		
	}
	//consulta 05
	public ArrayList<String> relatorioReservasFuturas() throws SQLException {
		try {
			return EmprestimosBD.getInstance().relatorioReservasFuturas();
		} 
		catch (ClassNotFoundException e) {
		}
		return null;
		
	}
	//consulta 06
	public ArrayList<String> qtdReservasEquipECusto(String equipId) throws SQLException {
		try {
			return EmprestimosBD.getInstance().qtdReservasEquipECusto(equipId);
		} 
		catch (ClassNotFoundException e) {
		}
		return null;
		
	}

	//consulta 07
	public ArrayList<String> listarNumeroDeReservasECustoTotalporFunct() throws SQLException {
		try {
			return EmprestimosBD.getInstance().listarNumeroDeReservasECustoTotalporFunct();
		} 
		catch (ClassNotFoundException e) {
		}
		return null;
		
	}
	//consulta 08
		public ArrayList<String> listaFuncionariosSemReserva() throws SQLException {
			try {
				return EmprestimosBD.getInstance().listaFuncionariosSemReserva();
			} 
			catch (ClassNotFoundException e) {
			}
			return null;
			
		}


}
