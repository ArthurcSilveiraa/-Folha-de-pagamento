# Ficha de testes e reflexão — Trabalho de POO

## 1. Resultados dos cinco objetos

Valores calculados conforme as regras fornecidas no enunciado.

| Objeto | Bruto | INSS | IRRF | FGTS | DAS | Líquido |
|---|---:|---:|---:|---:|---:|---:|
| Ana — CLT, salário R$ 3.000 | R$ 3.000,00 | R$ 248,60 | R$ 0,00 | R$ 240,00 | R$ 0,00 | R$ 2.751,40 |
| Bruno — CLT, salário R$ 6.000 | R$ 6.000,00 | R$ 641,51 | R$ 385,10 | R$ 480,00 | R$ 0,00 | R$ 4.973,39 |
| Carla — PJ, R$ 50 x 120 h | R$ 6.000,00 | R$ 0,00 | R$ 0,00 | R$ 0,00 | R$ 86,05 | R$ 5.913,95 |
| Daniel — professor, R$ 50 x 20 h/semana | R$ 5.250,00 | R$ 536,51 | R$ 89,53 | R$ 420,00 | R$ 0,00 | R$ 4.623,96 |
| Elisa — professora, R$ 100 x 20 h/semana | R$ 10.500,00 | R$ 988,09 | R$ 1.707,05 | R$ 840,00 | R$ 0,00 | R$ 7.804,86 |

### Conferência do cálculo de Daniel
- Horas-aula mensais: 20 x 4,5 = 90 horas.
- Remuneração das aulas: 90 x R$ 50,00 = R$ 4.500,00.
- DSR didático: R$ 4.500,00 / 6 = R$ 750,00.
- Bruto: R$ 4.500,00 + R$ 750,00 = R$ 5.250,00.
- INSS: R$ 536,51.
- Base do IR: R$ 5.250,00 - R$ 607,20 (desconto simplificado) = R$ 4.642,80.
- IR inicial: R$ 4.642,80 x 22,5% - R$ 675,49 = R$ 369,14.
- Redução: R$ 978,62 - (0,133145 x R$ 5.250,00) = R$ 279,61.
- IRRF final: R$ 369,14 - R$ 279,61 = R$ 89,53.
- Líquido: R$ 5.250,00 - R$ 536,51 - R$ 89,53 = R$ 4.623,96.

## 2. Ficha dos testes obrigatórios

| Teste | Resultado esperado | Resultado obtido | Passou? | Motivo / correção |
|---|---|---|---|---|
| Salário negativo | Rejeitar com IllegalArgumentException | Rejeitado | Sim | Validação no construtor da classe Funcionario. |
| Valor da hora negativo | Rejeitar com IllegalArgumentException | Rejeitado | Sim | Validação no construtor de FuncionarioPJ. |
| Quantidade de horas negativa | Rejeitar com IllegalArgumentException | Rejeitado | Sim | Validação no construtor de FuncionarioPJ. |
| CLT com salário zero | Todos os cálculos iguais a zero | Bruto, INSS, IRRF, FGTS e líquido = R$ 0,00 | Sim | Zero é permitido e não gera descontos. |
| PJ com hora R$ 50 e zero horas | Bruto R$ 0,00; DAS R$ 86,05; líquido -R$ 86,05 | Bruto R$ 0,00; DAS R$ 86,05; líquido -R$ 86,05 | Sim | O DAS permanece devido mesmo sem horas trabalhadas. |
| CLT com salário R$ 8.000 | INSS R$ 925,09 | R$ 925,09 | Sim | A contribuição é progressiva e ainda não atingiu o teto. |
| CLT com R$ 10.500 e R$ 12.000 | INSS R$ 988,09 nos dois | R$ 988,09 nos dois | Sim | Acima do teto previdenciário, a contribuição não aumenta. |
| CLT com R$ 5.000 | IRRF R$ 0,00 | R$ 0,00 | Sim | Regra didática zera o imposto até R$ 5.000,00 de bruto. |
| CLT com R$ 6.000 | IRRF R$ 385,10 | R$ 385,10 | Sim | Há redução parcial do imposto nessa faixa de remuneração. |
| Calcular líquido duas vezes | Mesmo resultado, sem alterar bruto | Mesmo resultado nas duas chamadas | Sim | Os métodos apenas calculam valores; não acumulam descontos. |
| Recalcular Ana após criar Bruno | Valores de Ana permanecem iguais | Bruto R$ 3.000,00; INSS R$ 248,60; IRRF R$ 0,00; líquido R$ 2.751,40 | Sim | Cada objeto mantém seus próprios atributos. |

O INSS de R$ 8.000,00 é R$ 921,51. O teto de R$ 988,09 é alcançado a partir de R$ 8.475,55.

## 3. Reflexão

### 1. Qual é a diferença entre classe e objeto? Identifique dois objetos do programa.
Uma classe é como um modelo que define os atributos e os comportamentos de algo. O objeto é uma instância criada a partir desse modelo. No programa, `ana` e `bruno` são dois objetos da classe `Funcionario`, cada um com seu próprio nome e salário.

### 2. O que o construtor garante na criação? Por que o regime não tem setter?
O construtor garante que o objeto já seja criado com os dados necessários e válidos, como nome e salário ou valor da hora e quantidade de horas. O regime não tem setter porque deve ser definido na criação e permanecer fixo durante a existência do objeto.

### 3. Por que o FGTS aparece no demonstrativo, mas não reduz o líquido?
Porque o FGTS é um depósito feito pelo empregador em benefício do trabalhador. Ele corresponde a 8% do salário, mas não é descontado do pagamento mensal do funcionário.

### 4. Qual método foi sobrescrito? O que acontece quando o cálculo de INSS chama esse método em um professor?
O método sobrescrito foi `calcularBruto()`. Quando `calcularINSS()` é chamado para um objeto `Professor`, o Java executa a versão de `calcularBruto()` da classe `Professor`, calculando as horas-aula mensais, a remuneração e o DSR. Assim, o INSS é calculado sobre o bruto correto do professor.

### 5. Por que um professor CLT herdar de uma classe chamada FuncionarioPJ causa um problema de modelagem? Sugira um nome ou uma organização melhor para compartilhar o cálculo por hora, sem precisar implementar.
Isso causa um problema porque o professor desta atividade é CLT, mas herda de uma classe que representa um funcionário PJ. O nome da classe não corresponde ao regime real do professor e pode confundir quem lê ou mantém o código. Uma organização melhor seria criar uma classe intermediária chamada `FuncionarioPorHora`, com os atributos e cálculos compartilhados de valor da hora e quantidade de horas. Tanto `FuncionarioPJ` quanto `Professor` poderiam herdar dela.

## 4. Observações para a apresentação

- `private` protege os atributos e os getters/setters controlam o acesso.
- `final` impede que o regime seja alterado depois da construção.
- `extends` representa a herança.
- `super(...)` chama o construtor da classe mãe.
- `@Override` indica sobrescrita de método.
- `calcularBruto()` é sobrescrito em `FuncionarioPJ` e novamente em `Professor`.
- O método `calcularINSS()` usa `calcularBruto()`, permitindo o polimorfismo.
- O FGTS é exibido, mas não é subtraído do líquido.
- Para PJ, o DAS é descontado uma única vez; INSS, IRRF e FGTS em folha são zero.

**Antes de entregar:** execute `javac *.java` e `java Main` no terminal do VS Code e confira a saída. A ficha registra os resultados previstos pelo programa; se fizer alterações, atualize os valores obtidos após executar.
