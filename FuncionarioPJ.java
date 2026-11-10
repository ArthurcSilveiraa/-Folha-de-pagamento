public class FuncionarioPJ extends Funcionario {
    private double valorHora;
    private double horasTrabalhadas;

    public FuncionarioPJ(String nome, double valorHora, double horasTrabalhadas) {
        this(nome, valorHora, horasTrabalhadas, true);
    }

    protected FuncionarioPJ(String nome, double valorHora,
                            double horasTrabalhadas, boolean pj) {
        super(nome, 0.0, pj);
        validarNaoNegativo(valorHora, "O valor da hora não pode ser negativo.");
        validarNaoNegativo(horasTrabalhadas, "A quantidade de horas não pode ser negativa.");

        this.valorHora = valorHora;
        this.horasTrabalhadas = horasTrabalhadas;
    }

    public double getValorHora() {
        return valorHora;
    }

    public void setValorHora(double valorHora) {
        validarNaoNegativo(valorHora, "O valor da hora não pode ser negativo.");
        this.valorHora = valorHora;
    }

    public double getHorasTrabalhadas() {
        return horasTrabalhadas;
    }

    public void setHorasTrabalhadas(double horasTrabalhadas) {
        validarNaoNegativo(horasTrabalhadas, "A quantidade de horas não pode ser negativa.");
        this.horasTrabalhadas = horasTrabalhadas;
    }

    @Override
    public double calcularBruto() {
        return arredondar(valorHora * horasTrabalhadas);
    }
}
