import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class JogoBatalhaNaval {

    static final int VAZIO = 0;
    static final int NAVIO = 1;
    static final int ERROU_TIRO = 2;
    static final int ACERTOU_TIRO = 3;
    static final int POSICAO_X = 0;
    static final int POSICAO_Y = 1;

    static String nomeJogador1, nomeJogador2;
    static int alturaT, larguraT, quantidadeDeNavios, limiteMaximoDeNavios;
    static int tabuleiroJogador1[][], tabuleiroJogador2[][];
    static Scanner input = new Scanner(System.in);
    static int naviosJogador1, naviosJogador2;


    public static void obterTamanhoDosTabuleiros() {

        for (;;) {
            boolean tudoOk = false;
            try {
                input = new Scanner(System.in);
                System.out.println("Digite a altura do tabuleiro: ");
                alturaT = input.nextInt();
                System.out.println("Digite a largura do tabuleiro: ");
                larguraT = input.nextInt();
                tudoOk = true;
            } catch (InputMismatchException erro) {
                System.out.println("Digite um valor numérico");
            }
            if (tudoOk) {
                break;
            }
        }
    }

    public static void obterNomesDosJogadores() {
        System.out.println("Digite o nome do jogador 1: ");
        nomeJogador1 = input.next();
        System.out.println("Digite o nome do jogador 2: ");
        nomeJogador2 = input.next();
    }

    public static void calcularQuantidadeMaximaDeNaviosNoJogo() {
        limiteMaximoDeNavios = (alturaT * larguraT) / 3;
    }

    public static void iniciandoOsTamanhosDosTabuleiros() {
        tabuleiroJogador1 = retornarNovoTabuleiroVazio();
        tabuleiroJogador2 = retornarNovoTabuleiroVazio();
    }

    public static int[][] retornarNovoTabuleiroVazio() {
        return new int [larguraT][alturaT];
    }


    public static void obterQuantidadeDeNaviosNoJogo() {
        System.out.println("Digite a quantidade de navios do jogo:");
        System.out.println("Max: " + limiteMaximoDeNavios + " navios");
        quantidadeDeNavios = input.nextInt();
        if(quantidadeDeNavios < 1 && quantidadeDeNavios > limiteMaximoDeNavios) {
            quantidadeDeNavios = limiteMaximoDeNavios;
        }
    }

    public static void instanciarContadoresDeNaviosDosJogadores() {
        naviosJogador1 = quantidadeDeNavios;
        naviosJogador2 = quantidadeDeNavios;

    }

    public static int[][] retornarNovoTabuleiroComOsNavios() {

        int novoTabuleiro[][] = retornarNovoTabuleiroVazio();
        int quantidadeRestanteDeNavios = quantidadeDeNavios;
        int x = 0, y = 0;
        Random numeroAleatorio = new Random();
        do {
            x = 0;
            y = 0;
            for (int[] linha : novoTabuleiro) {
                for (int coluna : linha) {
                    if (numeroAleatorio.nextInt(100) <= 10) {
                        if (coluna == VAZIO) {
                            novoTabuleiro[x][y] = NAVIO;
                            quantidadeRestanteDeNavios--;
                            break;
                        }
                        if (quantidadeRestanteDeNavios <= 0) {
                            break;
                        }
                    }
                    y++;
                }
                y = 0;
                x++;
                if (quantidadeRestanteDeNavios <= 0) {
                    break;
                }
            }
        } while(quantidadeRestanteDeNavios > 0);
        return novoTabuleiro;
    }

    public static void inserirOsNaviosNosTabuleirosDosJogadores() {
        tabuleiroJogador1 = retornarNovoTabuleiroComOsNavios();
        tabuleiroJogador2 = retornarNovoTabuleiroComOsNavios();
    }


    public static void exibirNumerosDasColunasDosTabuleiros() {
        int numeroDaColuna = 1;
        String numerosDoTabuleiro = "   ";

        for(int i = 0; i < alturaT; i++) {
            numerosDoTabuleiro += (numeroDaColuna++) + " ";
        }
        System.out.println(numerosDoTabuleiro);
    }

    public static void exibirTabuleiro(String nomeDoJogador, int [][] tabuleiro, boolean seuTabuleiro) {
        System.out.println("|-----" + nomeDoJogador + "-----|");
        exibirNumerosDasColunasDosTabuleiros();
        String linhaDoTabuleiro = "";
        char letraDaLinha = 65;
        for(int[] linha : tabuleiro) {
            linhaDoTabuleiro = (letraDaLinha++) + " |";

            for(int coluna : linha) {
                switch (coluna) {
                    case VAZIO:
                        linhaDoTabuleiro += " |";
                        break;
                    case NAVIO:
                        if(seuTabuleiro) {
                            linhaDoTabuleiro += "N|";
                            break;
                        } else {
                            linhaDoTabuleiro += " |";
                            break;
                        }
                    case ERROU_TIRO:
                        linhaDoTabuleiro += "X|";
                        break;
                    case ACERTOU_TIRO:
                        linhaDoTabuleiro += "D|";
                        break;
                }
            }
            System.out.println(linhaDoTabuleiro);
        }
    }

    public static void exibirTabuleirosDosJogadores(){
        exibirTabuleiro(nomeJogador1, tabuleiroJogador1, true);
        exibirTabuleiro(nomeJogador2, tabuleiroJogador2, false);
    }

    public static String receberValorDigitadoPeloJogador() {
        System.out.println("Digite a posição do seu tiro: ");
            return input.next();
    }
    public static boolean validarTiroDoJogador(String tiroDoJogador) {
        int quantidadeDeNumeros = (alturaT > 10) ? 2 : 1;
        String expressaoDeVerificacao = "^[A-Za-z]{1}[0-9]{" +quantidadeDeNumeros+ "}$";
        return tiroDoJogador.matches(expressaoDeVerificacao);
    }

    public static boolean validarPosicoesInseridasPeloJogador(int [] posicoes) {
        boolean retorno = true;
        if(posicoes[0] > larguraT -1) {
            retorno = false;
            System.out.println("A posição das letras não pode ser maior que " + (char) (larguraT + 64));
        }
        if(posicoes[1] > alturaT) {
            retorno = false;
            System.out.println("A posição numérica não pode ser maior que " + alturaT);
        }

        return retorno;
    }

    public static int[] retornarPosicoesDigitadasPeloJogador(String tiroDoJogador) {
        String tiro = tiroDoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[POSICAO_X] = tiro.charAt(0) - 97;
        retorno[POSICAO_Y] = Integer.parseInt(tiro.substring(1)) - 1;
        return retorno;
    }


    public static void inserirValoresDaAcaoNoTabuleiro(int [] posicoes, int numeroDoJogador) {

        if(numeroDoJogador == 1) {
            if (tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador2--;
                System.out.println("Você acertou um navio!");
            } else {
                tabuleiroJogador2[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println("Você errou o tiro!");
            }
        } else {
            if (tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] == NAVIO) {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ACERTOU_TIRO;
                naviosJogador1--;
                System.out.println("Você acertou um navio!");
            } else {
                tabuleiroJogador1[posicoes[POSICAO_X]][posicoes[POSICAO_Y]] = ERROU_TIRO;
                System.out.println("Você errou o tiro!");
            }
        }
    }


    public static boolean acaoDoJogador() {
        boolean acaoValida = true;
        String tiroDoJogador = receberValorDigitadoPeloJogador();
        if(validarTiroDoJogador(tiroDoJogador)) {
            int[] posicoes = retornarPosicoesDigitadasPeloJogador(tiroDoJogador);
            if (validarPosicoesInseridasPeloJogador(posicoes)) {
                inserirValoresDaAcaoNoTabuleiro(posicoes, 1);
            } else {
                acaoValida = false;
            }
        } else {
            System.out.println("Posição inválida.");
            acaoValida = false;
        }
        return acaoValida;
    }

    public static void acaoDoComputador() {
        int [] posicoes = retornarJogadaDoComputador();
        inserirValoresDaAcaoNoTabuleiro(posicoes, 2);

    }

    public static int[] retornarJogadaDoComputador() {
        int [] posicoes = new int[2];
        posicoes[POSICAO_X] = retornarJogadaAleatoriaDoComputador(larguraT);
        posicoes[POSICAO_Y] = retornarJogadaAleatoriaDoComputador(alturaT);
        return posicoes;

    }

    public static int retornarJogadaAleatoriaDoComputador(int limite) {
        Random jogadaDoComputador = new Random();
        int numeroGerado = jogadaDoComputador.nextInt(limite);
        return (numeroGerado == limite) ? --numeroGerado : numeroGerado;
    }


    public static void verificarFimDeJogo() {
        boolean jogoAtivo = true;
        do {
            exibirTabuleirosDosJogadores();
            if (acaoDoJogador()) {
                if (naviosJogador2 <= 0) {
                    jogoAtivo = false;
                    System.out.println(nomeJogador1 + " venceu o jogo!");
                    break;
                }
                acaoDoComputador();
                if (naviosJogador1 <= 0) {
                    jogoAtivo = false;
                    System.out.println(nomeJogador2 + " venceu o jogo!");
                    break;
                }
            }
        } while(jogoAtivo);
        exibirTabuleirosDosJogadores();
    }

    public static void main(String[] args) {
        // ToDo
        obterNomesDosJogadores();
        obterTamanhoDosTabuleiros();
        calcularQuantidadeMaximaDeNaviosNoJogo();
        iniciandoOsTamanhosDosTabuleiros();
        obterQuantidadeDeNaviosNoJogo();
        instanciarContadoresDeNaviosDosJogadores();
        inserirOsNaviosNosTabuleirosDosJogadores();
        verificarFimDeJogo();

        input.close();

    }
}
