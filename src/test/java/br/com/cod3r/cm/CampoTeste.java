package br.com.cod3r.cm;

import br.com.cod3r.cm.excecao.ExplosaoException;
import br.com.cod3r.cm.modelo.Campo;
import org.junit.Test;

import static org.junit.Assert.*;

public class CampoTeste {
    private final Campo campo = new Campo(3, 3);

    @Test
    public void testeVizinhoRealDistancia1Direita(){
        Campo vizinho = new Campo(3, 4);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoRealDistancia1Esquerda(){
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoRealDistancia1EmCima(){
        Campo vizinho = new Campo(2, 3);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoRealDistancia1EmBaixo(){
        Campo vizinho = new Campo(3, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeVizinhoRealDistancia2(){
        Campo vizinho = new Campo(2, 2);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertTrue(resultado);
    }

    @Test
    public void testeNaoVizinho(){
        Campo vizinho = new Campo(1, 1);
        boolean resultado = campo.adicionarVizinho(vizinho);
        assertFalse(resultado);
    }

    @Test
    public void testeValorPadraoAtributoMarcado(){
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacao(){
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    public void testeAlternarMarcacaoDuasChamadas(){
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    public void testeAbrirNaoMinadoNaoMarcado(){
        assertTrue(campo.abrir());
    }

    @Test
    public void testeAbrirNaoMinadoMarcado(){
        campo.alternarMarcacao();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoMarcado(){
        testeAlternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    public void testeAbrirMinadoNaoMarcado(){
        campo.minar();
        assertThrows(ExplosaoException.class, campo::abrir);
    }

    @Test
    public void testeAbrirVizinhos(){
        Campo campo11 = new Campo(1, 1);

        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isAberto());
    }

    @Test
    public void testeComAbrirVizinhos(){
        Campo campo11 = new Campo(1, 1);
        Campo campo12 = new Campo(1, 2);
        campo12.minar();

        Campo campo22 = new Campo(2, 2);
        campo22.adicionarVizinho(campo11);
        campo22.adicionarVizinho(campo12);

        campo.adicionarVizinho(campo22);
        campo.abrir();

        assertTrue(campo22.isAberto() && campo11.isFechado());
    }


}
