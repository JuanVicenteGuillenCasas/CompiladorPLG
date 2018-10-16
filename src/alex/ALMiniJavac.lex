package alex;

import errors.GestionErrores;

%%
%cup
%line
%class AnalizadorLexico
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErrores errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public void fijaGestionErrores(GestionErrores errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digito = [0-9]
entero = [\+|\-]?{digito}+
identificador = {letra}({letra}|{digito})*
puntoComa = \;
coma = \,
dosPuntos = \:

separador = [ \t\r\b\n]
comentario = \@[^\n]* 
comentarioAmplio = "@%"(.|\n)*"%@"

operadorSuma = "++"
operadorResta = "--"
operadorMultiplicacion = \*
operadorDivision = /
operadorMenIg = "<="
operadorMayIg = ">="
operadorMen = \<
operadorMay = \>
operadorIg = \=
operadorNoIg = "!="
operadorNegar = "!!"
operadorAsig = "->"

parentesisApertura = \(
parentesisCierre = \)
corcheteApertura = \[
corcheteCierre = \]
LlaveApertura = \{
LlaveCierre = \}

%%
{separador}               {}
{comentario}              {}
{comentarioAmplio}        {}
{entero}                  {return ops.unidadInt();}
{identificador}           {return ops.unidadId();}
{puntoComa}               {return ops.unidadPuntoComa();}
{coma}                    {return ops.unidadComa();}
{dosPuntos}               {return ops.unidadDosPuntos();}
{operadorSuma}            {return ops.unidadOpSuma();}
{operadorResta}           {return ops.unidadOpResta();}
{operadorMultiplicacion}  {return ops.unidadOpMult();}
{operadorDivision}        {return ops.unidadOpDiv();}
{operadorMenIg}           {return ops.unidadMenIg();}
{operadorMayIg}           {return ops.unidadMayIg();}
{operadorMen}             {return ops.unidadMen();}
{operadorMay}             {return ops.unidadMay();}
{operadorIg}              {return ops.unidadIg();}
{operadorNoIg}            {return ops.unidadNoIg();}
{operadorNegar}           {return ops.unidadNegar();}
{operadorAsig}            {return ops.unidadAsig();}
{parentesisApertura}      {return ops.unidadParAper();}
{parentesisCierre}        {return ops.unidadParCierr();}
{corcheteApertura}        {return ops.unidadCorAper();}
{corcheteCierre}          {return ops.unidadCorCierr();}
{LlaveApertura}           {return ops.unidadLlavAper();}
{LlaveCierre}             {return ops.unidadLlavCierr();}
[^]              {errores.errorLexico(fila(),lexema());}  