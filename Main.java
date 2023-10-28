class Main {

	public static void main(String[] args) {
		Matriz mat1, mat2;
		int det;
		long inicio, fim, resultado;

		mat1 = new Matriz(9, 9);
		mat1.inicializaRandomico();

		mat1.imprime();

		// baseline
		// inicio = System.currentTimeMillis();
		inicio = System.nanoTime();
		det = mat1.determinante(); // baseline
		// fim = System.currentTimeMillis();
		fim = System.nanoTime();
		resultado = fim - inicio;
		System.out.println(det);
		System.out.println(resultado);

		// metodo otimizado pulando 0
		// inicio = System.currentTimeMillis();
		inicio = System.nanoTime();
		det = mat1.determinanteOtimizadoV2(); // otimizado 1
		// fim = System.currentTimeMillis();
		fim = System.nanoTime();
		resultado = fim - inicio;
		System.out.println(det);
		System.out.println(resultado);

		// metodo otimizado v2 pulando 0 e linhas proporcionais
		// inicio = System.currentTimeMillis();
		inicio = System.nanoTime();
		det = mat1.determinanteOtimizadoV2();
		// fim = System.currentTimeMillis();
		fim = System.nanoTime();
		resultado = fim - inicio;
		System.out.println(det);
		System.out.println(resultado);

		// o metodo calcula o tempo em nanosegundos

	}

}
