package GraphVisitor;

import java.util.ArrayList;
import ast.*;
import Visitor.Visitor;

public class GrapherVisitor implements Visitor {

    public static ArrayList<Integer> VisitedNode = new ArrayList<>();
    public static int nodeCount; //contador de nodos
    public String concat = "";//string para concatenar los nodos visitados
    public String member = "";//string que guarda los nodos reducidos

    public GrapherVisitor() {
        this.member = "digraph g {\n"; //Sintaxis inicial
        this.nodeCount = 0;
    }

    @Override
    public void visit(Program visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"Program{}\"];\n";
    }

    @Override
    public void visit(var_declaration visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"" + "Var, " + visitor.getIdent() + " " + visitor.getTipo() + "\"];\n";
    }

    @Override
    public void visit(fun_declaration visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"" + "Function: " + visitor.getTipo() + " " + visitor.getIdent() + "\"];\n";
    }

    //Este metodo tiene 2 tipos distintos uno de arreglo. (el que tiene el corchete).
    public void visit(Param visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        if (visitor.getBrackets()) {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"" + "Param: " + visitor.getTipo() + "\"];\n";
        } else {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"" + "Param: " + visitor.getTipo() + "\"];\n";
        }
    }

    @Override
    public void visit(compound_stmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"Body{}\"];\n";
    }

    @Override
    public void visit(Empty_Stmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"Empty\"];\n";
    }
    /*Este metodo tiene un boolean que se encarga de verificar si la sintaxis if tiene un else.*/

    @Override
    public void visit(selection_stmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        if (visitor.getElse()==true) {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"If_Else{}\"];\n";
        } else {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"If{}\"];\n";
        }
    }
    /*Al igual que la sintxis if este tiene un boolean que se encarga de ver si el while tiene un do antees.*/

    @Override
    public void visit(WhileStmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"While{}\"];\n";
    }

    @Override
    public void visit(return_stmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"Return " + visitor.getTipo() + ";\"];\n";
    }

    @Override
    public void visit(ExprAsign visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        if (visitor.getValor() == null) {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"::=\"];\n";
        } else {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"::= " + visitor.getValor() + "\"];\n";
        }

    }
    /*Expresiones compuestas y normales.*/

    @Override
    public void visit(ExprVar visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        if (!visitor.isCorchetes()) {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"Exp, " + visitor.getIdent() + "  " + visitor.getValor() + "\"];\n";
        } else {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"Exp, " + visitor.getIdent() + "[Exp] = " + visitor.getValor() + "\"];\n";
        }

    }
    /*Expresiones que utilizan operadores, suma, resta, mult, etc.*/

    @Override
    public void visit(ExprBynary visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        String operador = visitor.getTipo();
        switch (operador) {
            case "Menorigual":
                operador = "<=";
                break;
            case "Menor":
                operador = "<";
                break;
            case "Mayorigual":
                operador = ">=";
                break;
            case "Mayor":
                operador = ">";
                break;
            case "Igual":
                operador = "=";
                break;
            case "Distinto":
                operador = "!=";
                break;
            case "Suma":
                operador = "+";
                break;
            case "Resta":
                operador = "-";
                break;
            case "Multiplicacion":
                operador = "*";
                break;
            case "Division":
                operador = "/";
                break;
            case "Exponente1":
                operador = "^";
                break;
            case "Exponente2":
                operador = "**";
                break;
            default:
                operador = "unknown";
                break;
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"Operator,  " + " " + operador + " " + "\"];\n";
    }
    /*Expresion constante.*/

    @Override
    public void visit(ExprConst visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
            this.member += Integer.toString(visitor.getINode()) + " [label=\"Constant " + visitor.getValor() + "\"];\n";
    }

    @Override
    public void visit(CallFunction visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        if (visitor.getTipo() == "void") {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"Call " + visitor.getIdent() + "(args)" + "\"];\n";
        } else {
            this.member += Integer.toString(visitor.getINode()) + " [label=\"Valor " + visitor.getValor() + "\"];\n";
        }
    }

    @Override
    public void visit(ForStmt visitor) {
        Node nodoPadre = visitor.getPadre();
        if (nodoPadre != null) {
            this.concat += Integer.toString(nodoPadre.getINode()) + "->" + Integer.toString(visitor.getINode()) + ";\n";
        }
        this.member += Integer.toString(visitor.getINode()) + " [label=\"For{}\"];\n";
    }
    /*Metodo que retorna la cadena completa para generar el .dot.*/

    public String returnString() {
        String cadena = "";
        cadena = cadena.concat(this.member);
        cadena = cadena.concat(this.concat);
        cadena += "}";
        return cadena;
    }

}
