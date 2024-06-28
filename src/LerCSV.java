import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LerCSV {

    public class Aluno {
        String matricula;
        String nome;
        String nota;

        public Aluno(String matricula, String nome, String nota) {
            this.matricula = matricula;
            this.nome = nome;
            this.nota = nota;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getNota() {
            return nota;
        }

        public void setNota(String nota) {
            this.nota = nota;
        }

    }

    public static void main(String[] args) {
        LerCSV lerCSV = new LerCSV();
        lerCSV.executar();
    }

    public void executar() {
        Map<String, Aluno> mapaAlunos = new HashMap<>();

        String arquivoAlunos = "C:\\_ws\\prova-java-gabriel\\Prova-java-Gabriel\\src\\alunos(1).csv";
        String linhaAluno;

        int contadorLinhas = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoAlunos))) {
            while ((linhaAluno = br.readLine()) != null) {
                if (contadorLinhas == 0) {
                    contadorLinhas++;
                    continue;
                }

                String[] dadosAluno = linhaAluno.split(";");
                String matriculaAluno = dadosAluno[0];
                String nomeAluno = dadosAluno[1];
                String notaAluno = dadosAluno[2];

                try {
                    Double nota = Double.parseDouble(notaAluno.replace(",", "."));
                    Aluno aluno = new Aluno(matriculaAluno, nomeAluno, nota.toString());
                    mapaAlunos.put(matriculaAluno, aluno);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter nota para número: " + e.getMessage());
                }
            }

            if (!mapaAlunos.isEmpty()) {
                int quantidadeAlunos = mapaAlunos.size();
                int aprovados = 0;
                int reprovados = 0;
                double menorNota = Double.MAX_VALUE;
                double maiorNota = Double.MIN_VALUE;
                double somaNotas = 0.0;

                for (Aluno aluno : mapaAlunos.values()) {
                    double nota = Double.parseDouble(aluno.getNota());

                    if (nota >= 6.0) {
                        aprovados++;
                    } else {
                        reprovados++;
                    }

                    if (nota < menorNota) {
                        menorNota = nota;
                    }
                    if (nota > maiorNota) {
                        maiorNota = nota;
                    }

                    somaNotas += nota;
                }

                double mediaGeral = somaNotas / quantidadeAlunos;

                String arquivoSaida = "C:\\\\_ws\\\\prova-java-gabriel\\\\Prova-java-Gabriel\\estatisticas_alunos.txt";
                try (FileWriter writer = new FileWriter(arquivoSaida)) {
                    writer.write("Quantidade de alunos na turma: " + quantidadeAlunos + "\n");
                    writer.write("Quantidade de alunos aprovados (nota >= 6.0): " + aprovados + "\n");
                    writer.write("Quantidade de alunos reprovados (nota < 6.0): " + reprovados + "\n");
                    writer.write("Menor nota da turma: " + menorNota + "\n");
                    writer.write("Maior nota da turma: " + maiorNota + "\n");
                    writer.write("Média geral das notas: " + mediaGeral + "\n");

                    System.out.println("Estatísticas gravadas com sucesso em: " + arquivoSaida);
                } catch (IOException e) {
                    System.err.println("Erro ao escrever estatísticas no arquivo: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de alunos: " + e.getMessage());
        }
    }
}
