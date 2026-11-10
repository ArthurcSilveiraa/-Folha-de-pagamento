public class Funcionario {
    private String nome;
    private double salarioMensal;
    private final boolean pj;

    // Construtor público: todo funcionário comum é CLT.
    public Funcionario(String nome, double salarioMensal) {
        this(nome, salarioMensal, false);
    }

    // Construtor protegido: permite que as subclasses definam o regime.
    protected Funcionario(String nome, double salarioMensal, boolean pj) {
        validarNome(nome);
        validarNaoNegativo(salarioMensal, "O salário mensal não pode ser negativo.");

        this.nome = nome.trim();
        this.salarioMensal = salarioMensal;
        this.pj = pj;
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome deve ser preenchido.");
        }
    }

    protected static void validarNaoNegativo(double valor, String mensagem) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    public static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    public double getSalarioMensal() {
        return salarioMensal;
    }

    public void setSalarioMensal(double salarioMensal) {
        validarNaoNegativo(salarioMensal, "O salário mensal não pode ser negativo.");
        this.salarioMensal = salarioMensal;
    }

    // O regime é definido no construtor e não possui setter.
    public boolean isPJ() {
        return pj;
    }

    public double calcularBruto() {
        return salarioMensal;
    }

    public double calcularINSS() {
        if (isPJ()) {
            return 0.0;
        }

        double bruto = calcularBruto();
        double inss = 0.0;

        // INSS progressivo: cada alíquota incide somente sobre sua faixa.
        inss += Math.min(bruto, 1621.00) * 0.075;

        if (bruto > 1621.00) {
            inss += (Math.min(bruto, 2902.84) - 1621.00) * 0.09;
        }
        if (bruto > 2902.84) {
            inss += (Math.min(bruto, 4354.27) - 2902.84) * 0.12;
        }
        if (bruto > 4354.27) {
            inss += (Math.min(bruto, 8475.55) - 4354.27) * 0.14;
        }

        return arredondar(inss);
    }

    public double calcularIRRF() {
        if (isPJ()) {
            return 0.0;
        }

        double bruto = calcularBruto();
        double inss = calcularINSS();
        double deducao = Math.max(inss, 607.20);
        double baseIR = Math.max(0.0, bruto - deducao);

        double impostoInicial;
        if (baseIR <= 2428.80) {
            impostoInicial = 0.0;
        } else if (baseIR <= 2826.65) {
            impostoInicial = baseIR * 0.075 - 182.16;
        } else if (baseIR <= 3751.05) {
            impostoInicial = baseIR * 0.15 - 394.16;
        } else if (baseIR <= 4664.68) {
            impostoInicial = baseIR * 0.225 - 675.49;
        } else {
            impostoInicial = baseIR * 0.275 - 908.73;
        }

        impostoInicial = arredondar(Math.max(0.0, impostoInicial));

        double reducao = 0.0;
        if (bruto <= 5000.00) {
            // A redução zera o IRRF nessa faixa.
            reducao = impostoInicial;
        } else if (bruto <= 7350.00) {
            reducao = Math.max(0.0, 978.62 - 0.133145 * bruto);
        }
        reducao = arredondar(reducao);

        return arredondar(Math.max(0.0, impostoInicial - reducao));
    }

    public double calcularFGTS() {
        if (isPJ()) {
            return 0.0;
        }
        return arredondar(calcularBruto() * 0.08);
    }

    public double calcularMEI() {
        return isPJ() ? 86.05 : 0.0;
    }

    public double calcularLiquido() {
        if (isPJ()) {
            return arredondar(calcularBruto() - calcularMEI());
        }
        return arredondar(calcularBruto() - calcularINSS() - calcularIRRF());
    }
}
