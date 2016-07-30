package main.java.al.antlr4;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;

import al.antlr4.AluminumLexer;
import al.antlr4.AluminumParser;
public class AluminumRunner 
{
    public static void main( String[] args) throws Exception 
    {
        AluminumLexer lexer = new AluminumLexer(new ANTLRFileStream("test/samples/ensure.al"));
        AluminumParser parser = new AluminumParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        
//        System.out.println(Trees.toStringTree(tree, parser));
        
        ALScope globalScope = ALScope.RootScope;
        
        ALSymbolVisitor symbolVisitor = new ALSymbolVisitor();
        symbolVisitor.visit(tree);
        
        ALEvalVisitor visitor = new ALEvalVisitor(globalScope, symbolVisitor.getFunctions());
        visitor.visit(tree);
    }
}