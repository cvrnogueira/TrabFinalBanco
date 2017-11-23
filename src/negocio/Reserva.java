package negocio;

import java.sql.Date;
import java.time.LocalDate;

public class Reserva {

	private int identificador_emprestimo;
	private LocalDate data_inicial_reserva;
	private LocalDate data_final_reserva;
	private String nro_matricula;
	private String identificador_equip;
	
	public Reserva(LocalDate data_inicial_reserva, LocalDate data_final_reserva, String nro_matricula, String identificador_equip){
		this.data_inicial_reserva =data_inicial_reserva;
		this.data_final_reserva=data_final_reserva;
		this.nro_matricula=nro_matricula;
		this.identificador_equip=identificador_equip;
	}
	public void setIdentificador_emprestimo(int id){
		this.identificador_emprestimo = id;
	}
	public int getIdentificador_emprestimo(){
		return identificador_emprestimo;
	}
	public LocalDate getData_inicial_reserva(){
		return data_inicial_reserva;
	}
	public LocalDate getData_final_reserva(){
		return data_final_reserva;
	}
	public String getNro_matricula(){
		return nro_matricula;
	}
	public String getIdentificador_equip(){
		return identificador_equip;
	}
}
