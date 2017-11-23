package negocio;

import java.sql.Date;

public class Funcionario {
	private String nro_matricula;
	private Date data_nasc;
	private  Date data_admissao;
	private String sexo;
	private String nome_completo;
	private String endereco;
	private double salario_mensal;
	
	public Funcionario(String nro_matricula, Date data_nasc, Date data_admissao, String sexo, String nome_completo, String endereco,double salario_mensal) {
		this.nro_matricula = nro_matricula;
		this.data_nasc = data_nasc;
		this.data_admissao = data_admissao;
		this.sexo = sexo;
		this.nome_completo = nome_completo;
		this.endereco = endereco;
		this.salario_mensal = salario_mensal;
	}
	
	public String getNroMatricula(){
		return nro_matricula;
	}
	public Date getDataNascimento(){
		return data_nasc;
	}
	public Date getDataAdmissao(){
		return data_admissao;
	}
	public String getSexo(){
		return sexo;
	}
	public String getNome(){
		return nome_completo;
	}
	public String getEndereco(){
		return endereco;
	}
	public Double getSalarioMensal(){
		return salario_mensal;
	}
	@Override
	public String toString(){
		return "Número de matrícula: " + getNroMatricula() + ", Nome: " + getNome();
	}
}
