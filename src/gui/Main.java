package gui;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import negocio.Equipamentos;
import negocio.Funcionario;
import negocio.Reserva;
import negocio.comandaOperacoes;

public class Main {
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in);
		comandaOperacoes comandaOperacao = new comandaOperacoes();
		String nome = null;
		String descricao = null;
		String lixo = null;
		int option;
		do {
			System.out.println("====Digite o N�mero da op��o====");
			System.out.println("1) Listar funcionarios em ordem alfab�tica -ok");
			System.out.println("2) Buscar funcion�rio por nome -ok");
			System.out.println("3) Buscar equipamento por descrição -ok");
			System.out.println("4) Fazer uma nova reserva -ok");
			System.out.println("5) Relatorio de reservas futuras -ok");
			System.out.println("6) Visualizar a quantidade de reservas de um equipamento e o total do custo correspondente");
			System.out.println("7) Listar n�mero de reserva e custo total de uso de equipamentos por funcin�rio");
			System.out.println("8) TODO: [Uma consulta � escolha do grupo (deve envolver pelo menos uma subconsulta)]");
			System.out.println("0) Sair");
			option = in.nextInt();
			switch(option){
				case 0:{
					try {
						if(comandaOperacao.sair()){
							System.out.println("Conexão fechada adequadamente!");
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 1: {
					ArrayList<Funcionario> todosFunc;
					try {
						todosFunc = comandaOperacao.listaFuncionarios();
						if(todosFunc == null){
							System.out.println("Não há funcionarios cadastrados");
						}
						else{
							for(Funcionario f: todosFunc){
								System.out.println(f);
							}
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 2:{
					ArrayList<Funcionario> funcFiltroPorNome;
					System.out.println("Digite o nome");
					nome = in2.nextLine();
					try {
						funcFiltroPorNome = comandaOperacao.buscaFuncionarioPorNome(nome);
						if(funcFiltroPorNome.isEmpty()){
							System.out.println("Não há funcionarios com esse nome!");
						}
						else{
							for(Funcionario f: funcFiltroPorNome){
								System.out.println(f);
							}
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 3:{
					ArrayList<Equipamentos> EquipFiltroPorDesc;
					System.out.println("Digite a descricao");
					descricao = in2.nextLine();
					try {
						EquipFiltroPorDesc = comandaOperacao.buscaEquipPorDescricao(descricao);
						if(EquipFiltroPorDesc.isEmpty()){
							System.out.println("Não há funcionarios com esse nome!");
						}
						else{
							for(Equipamentos equip: EquipFiltroPorDesc){
								System.out.println(equip);
							}
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 4: {
					try {
						System.out.println("Digite o ano da data de inicio da reserva");
						String anoInicio = in2.nextLine();
						System.out.println("Digite o mes da data de inicio da reserva");
						String mesInicio = in2.nextLine();
						System.out.println("Digite o dia da data de inicio da reserva");
						String diaInicio = in2.nextLine();
						System.out.println("Digite o ano da data de término da reserva");
						String anoFim = in2.nextLine();
						System.out.println("Digite o mes da data de término da reserva");
						String mesFim = in2.nextLine();
						System.out.println("Digite o dia da data de término da reserva");
						String diaFim = in2.nextLine();
							LocalDate data_inicial_reserva =  LocalDate.parse(""+anoInicio +  "-"+ mesInicio+ "-" + diaInicio + "");
							LocalDate data_final_reserva =  LocalDate.parse(""+anoFim +  "-"+ mesFim+ "-" + diaInicio + "");
						 if(data_inicial_reserva.isAfter(LocalDate.now()) && data_final_reserva.isAfter(LocalDate.now())){
							 System.out.println("Digite o seu número de matrícula");
							String nro_matricula = in2.nextLine();
							System.out.println 	("Digite o seu identificador de equipamento");
							String identificador_equip = in2.nextLine();
							if(comandaOperacao.fazerNovaReserva(new Reserva(data_inicial_reserva,data_final_reserva, nro_matricula,identificador_equip))){
								System.out.println("Reserva feita com sucesso!");
							}
						 }
						 else{
							 System.out.println("Sua data já passou, somente reservas para datas futuras são aceitas!");
						 }
					
					} catch(java.time.format.DateTimeParseException e){
						System.out.println("Sua data não existe! Lembre que o fomrato é como em 02-12-2017");
					}
					catch(java.sql.SQLIntegrityConstraintViolationException e){
						System.out.println("Verifique o número de matrícula e o Identificador do equipamento!");
					}
					catch (Exception e) {
							System.out.println("Erro!");
					}
					break;
				}
				case 5: {
					ArrayList<String> filtroEquipFunctData;
					try {
						filtroEquipFunctData = comandaOperacao.relatorioReservasFuturas();
						if(filtroEquipFunctData.isEmpty()){
							System.out.println("Não há reservas mais recentes que a data atual!");
						}
						else{
							for(String s: filtroEquipFunctData){
								System.out.println(s);
							}
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 6: {
					ArrayList<String> qtdReservasECusto;
					try {
						System.out.println("Digite o identificador do seu eqipamento");
						String equipId = in2.nextLine();
						qtdReservasECusto = comandaOperacao.qtdReservasEquipECusto(equipId);
						if(qtdReservasECusto.isEmpty()){
							System.out.println("Tem certeza que esse identificador de equipamento exite? Não foi achado no sistema!");
						}
						else{
							for(String s: qtdReservasECusto){
								System.out.println(s);
							}
						}
					} catch (SQLException e) {
						System.out.println("Erro!");
					}
					break;
				}
				case 7: {
					ArrayList<String> qtdReservasECustoPorFunct;
					try {
						qtdReservasECustoPorFunct = comandaOperacao.listarNumeroDeReservasECustoTotalporFunct();
						if(qtdReservasECustoPorFunct.isEmpty()){
							System.out.println("Não há registros de empréstimos no sistema!");
						}
						else{
							for(String s: qtdReservasECustoPorFunct){
								System.out.println(s);
							}
						}
					} catch (SQLException e) {

						System.out.println("Erro!");
					}
					break;
				}
				case 8: {
					//TODO: comandaOperacao.consultaAEscolha();
					System.out.println("Consulta a escolha");
					break;
				}
				default:{
					System.out.println("Opção não existente!");
				}
			}
		}while(option !=0);
	}
}
