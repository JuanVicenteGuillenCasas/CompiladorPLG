package alex;

import asint.sym;

public class ALexOperations {
  private AnalizadorLexico alex;
  public ALexOperations(AnalizadorLexico alex) {
   this.alex = alex;   
  }
  
  public UnidadLexica unidadEof(){
	  return new UnidadLexica(alex.fila(), sym.EOF , "<EOF>");
  }
  
  public UnidadLexica unidadInt(){
	  return new UnidadLexica(alex.fila(), sym.INT , alex.lexema());
  }
  
  public UnidadLexica unidadId() {
    if(alex.lexema().equals("begin")) return new UnidadLexica(alex.fila(), sym.BEGIN, "begin");
	else if(alex.lexema().equals("end")) return new UnidadLexica(alex.fila(), sym.END, "end");
	else if(alex.lexema().equals("declare")) return new UnidadLexica(alex.fila(), sym.DECLARE, "declare");
	else if(alex.lexema().equals("fun")) return new UnidadLexica(alex.fila(), sym.FUN, "fun");
	else if(alex.lexema().equals("if")) return new UnidadLexica(alex.fila(), sym.IF, "if");
	else if(alex.lexema().equals("else")) return new UnidadLexica(alex.fila(), sym.ELSE, "else");
	else if(alex.lexema().equals("while")) return new UnidadLexica(alex.fila(), sym.WHILE , "while");
	else if(alex.lexema().equals("for")) return new UnidadLexica(alex.fila(), sym.FOR, "for");
	else if(alex.lexema().equals("do")) return new UnidadLexica(alex.fila(), sym.DO , "do");
	else if(alex.lexema().equals("True")) return new UnidadLexica(alex.fila(), sym.TRUE, "true");
	else if(alex.lexema().equals("False")) return new UnidadLexica(alex.fila(), sym.FALSE, "false");
	else if(alex.lexema().equals("switch")) return new UnidadLexica(alex.fila(), sym.SWITCH, "switch");
	else if(alex.lexema().equals("case")) return new UnidadLexica(alex.fila(), sym.CASE, "case"); 
	else if(alex.lexema().equals("int")) return new UnidadLexica(alex.fila(), sym.IDINT, "int");
	else if(alex.lexema().equals("bool")) return new UnidadLexica(alex.fila(), sym.IDBOOL , "bool");
	else if(alex.lexema().equals("recives")) return new UnidadLexica(alex.fila(), sym.RECIVES , "recives");
	else if(alex.lexema().equals("returns")) return new UnidadLexica(alex.fila(), sym.RETURNS, "returns");
	else if(alex.lexema().equals("return")) return new UnidadLexica(alex.fila(), sym.RETURN, "return");
	else if(alex.lexema().equals("nothing")) return new UnidadLexica(alex.fila(), sym.NOTHING , "nothing");
	else if(alex.lexema().equals("call")) return new UnidadLexica(alex.fila(), sym.CALL , "call");
	else if(alex.lexema().equals("const")) return new UnidadLexica(alex.fila(), sym.CONST , "const");
	else if(alex.lexema().equals("AND")) return new UnidadLexica(alex.fila(), sym.AND , "AND");
	else if(alex.lexema().equals("OR")) return new UnidadLexica(alex.fila(), sym.OR , "OR");
	else return new UnidadLexica(alex.fila() , sym.ID , alex.lexema()); 
  }

  public UnidadLexica unidadPuntoComa(){
	  return new UnidadLexica(alex.fila(), sym.PUNTOCOMA , ";");	  
  }
  public UnidadLexica unidadComa(){
	  return new UnidadLexica(alex.fila(), sym.COMA , ",");  
  }
  
  public UnidadLexica unidadDosPuntos(){
	  return new UnidadLexica(alex.fila(), sym.DOSPUNTOS , ":");
  }
  
  public UnidadLexica unidadOpSuma(){
	  return new UnidadLexica(alex.fila(),sym.SUMA, "++");
  }
  
  public UnidadLexica unidadOpResta(){
	  return new UnidadLexica(alex.fila(), sym.RESTA , "--");
  }
  
  public UnidadLexica unidadOpMult(){
	  return new UnidadLexica(alex.fila(), sym.MULT , "*");
  }
  
  public UnidadLexica unidadOpDiv(){
	  return new UnidadLexica(alex.fila(), sym.DIV , "/");
  }
  
  public UnidadLexica unidadMenIg(){
	  return new UnidadLexica(alex.fila(), sym.MENORIG , "<=");
  }
  
  public UnidadLexica unidadMayIg(){
	  return new UnidadLexica(alex.fila(), sym.MAYIG , ">=");
  }
  
  public UnidadLexica unidadMen(){
	  return new UnidadLexica(alex.fila(), sym.MEN , "<");
  }
  
  public UnidadLexica unidadMay(){
	  return new UnidadLexica(alex.fila(), sym.MAY , ">");
  }
  
  public UnidadLexica unidadIg(){
	  return new UnidadLexica(alex.fila(), sym.IG , "=");
  }
  
  public UnidadLexica unidadNoIg(){
	  return new UnidadLexica(alex.fila(), sym.NOIG , "!=");
  }
  
  public UnidadLexica unidadNegar(){
	  return new UnidadLexica(alex.fila(), sym.NEGAR , "!!");
  }
  
  public UnidadLexica unidadAsig(){
	  return new UnidadLexica(alex.fila(), sym.ASIG , "->");  
  }
  
  public UnidadLexica unidadParAper(){
	  return new UnidadLexica(alex.fila(), sym.PARAP , "("); 
  }
  
  public UnidadLexica unidadParCierr(){
	  return new UnidadLexica(alex.fila(), sym.PARCE , ")"); 
  }
  
  public UnidadLexica unidadCorAper(){
	  return new UnidadLexica(alex.fila(), sym.CORAP , "["); 
  }
  
  public UnidadLexica unidadCorCierr(){
	  return new UnidadLexica(alex.fila(), sym.CORCE , "]"); 
  }
  
  public UnidadLexica unidadLlavAper(){
	  return new UnidadLexica(alex.fila(), sym.LLAVAP , "{"); 
  }
  
  public UnidadLexica unidadLlavCierr(){
	  return new UnidadLexica(alex.fila(), sym.LLAVCE , "}"); 
  }
}
