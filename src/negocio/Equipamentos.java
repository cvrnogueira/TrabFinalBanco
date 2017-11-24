package negocio;

import java.sql.Date;

public class Equipamentos {
	private String identificador_equip;
	private Date data_aquisicao;
	private String descricao;
	private double custo_diario_uso; 
	private String em_manutencao;
	private String tipo;

	public Equipamentos(String identificador_equip, Date data_aquisicao, String descricao, double custo_diario_uso, String em_manutencao, String tipo) {
		this.identificador_equip = identificador_equip;
		this.data_aquisicao = data_aquisicao;
		this.descricao = descricao;
		this.custo_diario_uso = custo_diario_uso;
		this.em_manutencao= em_manutencao;
		this.tipo = tipo;
	}
	@Override
	public String toString(){
		return "ID: " + identificador_equip + ", Descrição completa: " +descricao+ ", Custo Diário de Uso: R$" + custo_diario_uso + ", Em Manutenção?: " + em_manutencao;
	}
}


