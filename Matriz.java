import java.util.Random;

class Matriz{
	private int[][] mat;
	private int tamLinha;
	private int tamColuna;

	//construtor padrao pra matriz de ordem 6
	Matriz(){
		mat = new int[6][6];
		this.setTamanhoLinha(6);	
		this.setTamanhoColuna(6);
	}

	//contrutor pra inicializar com qualquer valor
	Matriz(int numLinhas, int numColunas){
		mat = new int[numLinhas][numColunas];
		this.setTamanhoLinha(numLinhas);	
		this.setTamanhoColuna(numColunas);
	}

	//metodos get e set
	public int getValor(int indiceI,int indiceJ){
		return mat[indiceI][indiceJ];
	}	
	
	public void setValor(int indiceI,int indiceJ, int novoValor){
		mat[indiceI][indiceJ] = novoValor;
	}

	public int getTamanhoLinha(){
		return tamLinha;
	}	
	
	public int getTamanhoColuna(){
		return tamColuna;
	}	

	private void setTamanhoLinha(int novoValor){
		tamLinha = novoValor;
	}

	private void setTamanhoColuna(int novoValor){
		tamColuna = novoValor;
	}


	//metodo pra imprimir a matriz
	public void imprime(){
		int conti,contj;	
		for(conti = 0; conti < this.getTamanhoLinha(); conti++){
			System.out.println();
			for(contj = 0; contj < this.getTamanhoColuna(); contj++){
				System.out.print(this.getValor(conti,contj) + " ");
			}
		}
		System.out.println();	
	}


	//metodo pra inicilizar com números aleatorios
	public void inicializaRandomico(){
		int conti,contj, novoValor;
		int ordem = this.getTamanhoLinha();
		Random gerador = new Random();
		for(conti = 0; conti < this.getTamanhoLinha(); conti++){
			for(contj = 0; contj < this.getTamanhoColuna(); contj++){
				novoValor = gerador.nextInt(ordem);
				this.setValor(conti,contj,novoValor);
			}
		}
	}

	// caso matriz nao quadrada, retorna -1
	public int retorneOrdem(){
		int numL, numC, ordem;

		numL = this.getTamanhoLinha();
		numC = this.getTamanhoColuna();
		ordem = -1;
		if(numL == numC){
			ordem = numL;
		}

		return ordem;
	}	


	//calcular o det de uma matriz de ordem 1
	private int detOrdem1(Matriz mat){
		return mat.getValor(0,0);
	}


	//calcular o det de uma matriz de ordem 2
	private int detOrdem2(Matriz mat){
		int diagonalP, diagonalI;

		diagonalP = mat.getValor(0,0) * mat.getValor(1,1);		
		diagonalI = mat.getValor(1,0) * mat.getValor(0,1);		

		return (diagonalP - diagonalI);
	}


	//calcular o sinal da matriz
	private int calculaSinal(int indiceL, int indiceC){
		int sinal;

		sinal = -1;

		if( ((indiceL + indiceC)% 2) == 0 ){
			sinal = 1;
		}

		return sinal;		
	}


	//copiar uma matriz maior pra uma menor
	public void copiaMatrizMaiorParaMenor(Matriz maior,Matriz menor,int isqn,int jsqn){
		int contAi,contAj,contBi,contBj,temp,numL,numC;
		numL = menor.getTamanhoLinha();
		numC = menor.getTamanhoColuna();

		contAi = 0;
		for(contBi = 0; contBi < numL; contBi++){
			if(contAi == isqn){
				contAi++;
			}
			contAj = 0;
			for(contBj = 0; contBj < numC; contBj++){
				if(contAj == jsqn){
					contAj++;
				}
				temp = maior.getValor(contAi,contAj);
				menor.setValor(contBi,contBj,temp);
				contAj++;
			}
			contAi++;
		}
	}


	//calcular o determinante de uma matriz de ordem 3 ou maior
	private int detOrdemN(Matriz mat){
		int sinal,cofator,detTemp,resposta,contC,numL,numC;
		Matriz matmenor;
		numL = this.getTamanhoLinha();
		numC = this.getTamanhoColuna();
		
		resposta = 0;
		for(contC = 0; contC < mat.getTamanhoColuna(); contC++){
			cofator = mat.getValor(0,contC);
			sinal = this.calculaSinal(0,contC);
			matmenor = new Matriz(numL-1,numC-1);
			this.copiaMatrizMaiorParaMenor(mat,matmenor,0,contC);
			detTemp = matmenor.determinanteOtimizadoV4();
			resposta = resposta + (cofator * sinal * detTemp);
		}
		return (resposta);
	}


	//metodo principal pra calcular o determinante
	public int determinante(){
		int ordem,det;

		ordem = this.retorneOrdem();
		det = 0;

		if(ordem > 0){
			switch (ordem) {
			    case 1:  det = this.detOrdem1(this);
				     break;
			    case 2:  det = this.detOrdem2(this);;
				     break;
			    default: det = this.detOrdemN(this);;
				     break;
			}
			
		}
		else{
			System.out.println("Matriz nao eh quadrada!! retornando 0");
		}

		return det;
	}

	//metodo otimizado 1

	public int determinanteOtimizadoV1() {
		int ordem = this.retorneOrdem();
		int det = 0;
	
		if (ordem > 0) {
			switch (ordem) {
				case 1:
					det = this.detOrdem1(this);
					break;
				case 2:
					det = this.detOrdem2(this);
					break;
				default:
					// Verifica se tem zeros na primeira linha e economiza chamadas recursivas
					for (int j = 0; j < this.getTamanhoColuna(); j++) {
						if (this.getValor(0, j) == 0) {
							continue; // pular colunas com zero
						}
						Matriz matmenor = new Matriz(ordem - 1, ordem - 1);
						copiaMatrizMaiorParaMenor(this, matmenor, 0, j);
						int sinal = calculaSinal(0, j);
						int cofator = this.getValor(0, j);
						int detTemp = matmenor.determinanteOtimizadoV1();
						det += cofator * sinal * detTemp;
					}
					break;
			}
		} else {
			System.out.println("Matriz não é quadrada! Retornando 0");
		}
	
		return det;
	}
	

	//metodo otimizado V2

	public int determinanteOtimizadoV2() {
		int ordem = this.retorneOrdem();
		int det = 0;
	
		if (ordem > 0) {
			switch (ordem) {
				case 1:
					det = this.detOrdem1(this);
					break;
				case 2:
					det = this.detOrdem2(this);
					break;
				default:
					// Verifica se tem zeros na primeira linha e economiza chamadas recursivas
					for (int j = 0; j < this.getTamanhoColuna(); j++) {
						int valor0j = this.getValor(0, j);
						if (valor0j == 0) {
							continue; // pular colunas com zero
						}
						Matriz matmenor = new Matriz(ordem - 1, ordem - 1);
						this.copiaMatrizMaiorParaMenor(this, matmenor, 0, j);
						int sinal = this.calculaSinal(0, j);
						int cofator = valor0j;
						int detTemp = matmenor.determinanteOtimizadoV2();
						det += cofator * sinal * detTemp;
					}
					//verifica se há linhas proporcionais
					for (int i = 1; i < ordem; i++) {
						boolean linhaProporcional = true;
						int valor0j = this.getValor(0, 0);
						int valorij = this.getValor(i, 0);
						if (valorij != 0 && valor0j != 0) {
							if (valorij % valor0j != 0) {
								linhaProporcional = false;
							}
						} else if (valorij != 0 || valor0j != 0) {
							linhaProporcional = false;
						}
						if (linhaProporcional) {
							int multiplicador = valorij / valor0j;
							for (int j = 0; j < this.getTamanhoColuna(); j++) {
								int novoValor = this.getValor(i, j) - multiplicador * this.getValor(0, j);
								this.setValor(i, j, novoValor);
							}
						}
					}
					break;
			}
		} else {
			System.out.println("Matriz não é quadrada! Retornando 0");
		}
	
		return det;
	}
	
}
