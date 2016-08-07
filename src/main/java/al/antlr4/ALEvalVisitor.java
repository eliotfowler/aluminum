package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import al.antlr4.AluminumBaseVisitor;
import al.antlr4.AluminumParser.AddExpressionContext;
import al.antlr4.AluminumParser.AndExpressionContext;
import al.antlr4.AluminumParser.AssignmentContext;
import al.antlr4.AluminumParser.BlockContext;
import al.antlr4.AluminumParser.BooleanExpressionContext;
import al.antlr4.AluminumParser.ClassStatementContext;
import al.antlr4.AluminumParser.DivideExpressionContext;
import al.antlr4.AluminumParser.EqExpressionContext;
import al.antlr4.AluminumParser.ExpressionContext;
import al.antlr4.AluminumParser.ExpressionExpressionContext;
import al.antlr4.AluminumParser.FunctionCallContext;
import al.antlr4.AluminumParser.FunctionCallExpressionContext;
import al.antlr4.AluminumParser.FunctionDeclContext;
import al.antlr4.AluminumParser.GtEqExpressionContext;
import al.antlr4.AluminumParser.GtExpressionContext;
import al.antlr4.AluminumParser.IdentifierExpressionContext;
import al.antlr4.AluminumParser.IfStatementContext;
import al.antlr4.AluminumParser.LtEqExpressionContext;
import al.antlr4.AluminumParser.LtExpressionContext;
import al.antlr4.AluminumParser.ModulusExpressionContext;
import al.antlr4.AluminumParser.MultiplyExpressionContext;
import al.antlr4.AluminumParser.NilExpressionContext;
import al.antlr4.AluminumParser.NotEqExpressionContext;
import al.antlr4.AluminumParser.NotExpressionContext;
import al.antlr4.AluminumParser.NumberExpressionContext;
import al.antlr4.AluminumParser.OrExpressionContext;
import al.antlr4.AluminumParser.PowerExpressionContext;
import al.antlr4.AluminumParser.StatementContext;
import al.antlr4.AluminumParser.StringExpressionContext;
import al.antlr4.AluminumParser.SubtractExpressionContext;
import al.antlr4.AluminumParser.TernaryExpressionContext;
import al.antlr4.AluminumParser.UnaryMinusExpressionContext;
import al.antlr4.AluminumParser.WhileStatementContext;
import main.java.al.antlr4.core.ALBoolean;
import main.java.al.antlr4.core.ALInt;
import main.java.al.antlr4.core.ALString;

public class ALEvalVisitor extends AluminumBaseVisitor<ALObject> {
	private static ALReturnValue returnValue = new ALReturnValue();
	private ALScope scope;
	private Map<String, ALFunction> globalFunctions;
	private Map<String, ALClass> classes;
	
	public ALEvalVisitor(ALScope scope, Map<String, ALFunction> globalFunctions, Map<String, ALClass> classes) {
		this.scope = scope;
		this.globalFunctions = globalFunctions;
		this.classes = classes;
	}
	
	public ALEvalVisitor(ALEvalVisitor clone) {
		this.scope = clone.scope;
		this.globalFunctions = clone.globalFunctions;
		this.classes = clone.classes;
	}
	
	@Override
	public ALObject visitFunctionDecl(FunctionDeclContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// Check if inside a class
		boolean insideClass = false;
		ParserRuleContext parent = ctx.getParent();
		while (parent != null) {
			if (parent instanceof ClassStatementContext) {
				insideClass = true;
				break;
			}
			parent = parent.getParent();
		}
		
		if (insideClass) {
			
		}
		
		return ALObject.Void;
	}

	@Override
	public ALObject visitAssignment(AssignmentContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject newVal = visit(ctx.expression());
		String id = ctx.Identifier().getText();
		scope.assign(id,  newVal);
		
		return ALObject.Void;
	}

	@Override
	public ALObject visitIfStatement(IfStatementContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject expression = visit(ctx.expression());
		
		if (!(expression instanceof ALBoolean)) {
			throw new ALEvalException(ctx);
		}
		
		boolean boolValue = ((ALBoolean)expression).getValue();
		
		if (boolValue) {
			return visit(ctx.block());
		}
		
		return ALObject.Void;
	}

	@Override
	public ALObject visitWhileStatement(WhileStatementContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject expression = visit(ctx.expression());
		
		if (!(expression instanceof ALBoolean)) {
			throw new ALEvalException(ctx);
		}
		
		boolean boolValue = ((ALBoolean)expression).getValue();
		
		while (boolValue) {
			ALObject retVal = visit(ctx.block());
			if (retVal != ALObject.Void) {
				return retVal;
			}
			
			expression = visit(ctx.expression());
			boolValue = ((ALBoolean)expression).getValue();
		}
		
		return ALObject.Void;
	}


	@Override
	public ALObject visitLtExpression(LtExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		boolean lhsIsNumber = lhs instanceof ALInt;
		boolean rhsIsNumber = rhs instanceof ALInt;
		
		if (lhsIsNumber && rhsIsNumber) {
			boolean isLt = ((ALInt)lhs).getInt() < ((ALInt)rhs).getInt();
			return new ALBoolean(isLt); 
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitGtExpression(GtExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		boolean lhsIsNumber = lhs instanceof ALInt;
		boolean rhsIsNumber = rhs instanceof ALInt;
		
		if (lhsIsNumber && rhsIsNumber) {
			ALInt lInt = (ALInt)lhs;
			ALInt rInt = (ALInt)rhs;
			boolean isGt = lInt.getInt() > rInt.getInt();
			return new ALBoolean(isGt); 
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNotEqExpression(NotEqExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		boolean lhsIsNumber = lhs instanceof ALInt;
		boolean rhsIsNumber = rhs instanceof ALInt;
		
		if (lhsIsNumber && rhsIsNumber) {
			boolean isNe = ((ALInt)lhs).getInt() != ((ALInt)rhs).getInt();
			return new ALBoolean(isNe); 
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNumberExpression(NumberExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return new ALInt(Integer.valueOf(ctx.Number().getText()));
	}

	@Override
	public ALObject visitIdentifierExpression(IdentifierExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return scope.resolve(ctx.Identifier().getText());
	}

	@Override
	public ALObject visitModulusExpression(ModulusExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt(lhs.getInt() % rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitNotExpression(NotExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALBoolean value = (ALBoolean)visit(ctx.expression());
			return new ALBoolean(!value.getValue());
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitMultiplyExpression(MultiplyExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt(lhs.getInt() * rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitBooleanExpression(BooleanExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return new ALBoolean(Boolean.valueOf(ctx.getText()));
	}

	@Override
	public ALObject visitGtEqExpression(GtEqExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALBoolean(lhs.getInt() >= rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitDivideExpression(DivideExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt(lhs.getInt() / rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitOrExpression(OrExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALBoolean lhs = (ALBoolean)visit(ctx.expression(0));
			ALBoolean rhs = (ALBoolean)visit(ctx.expression(1));
			
			return new ALBoolean(lhs.getValue() || rhs.getValue());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitUnaryMinusExpression(UnaryMinusExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt value = (ALInt)visit(ctx.expression());
			return new ALInt(-value.getInt());
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitPowerExpression(PowerExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt((int)Math.pow(lhs.getInt(), rhs.getInt()));
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitEqExpression(EqExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs == null && rhs == null) {
			return new ALBoolean(true);
		} else if (lhs == null) {
			return new ALBoolean(false);
		} else {
			return new ALBoolean(lhs.equals(rhs));
		}
	}

	@Override
	public ALObject visitAndExpression(AndExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALBoolean lhs = (ALBoolean)visit(ctx.expression(0));
			ALBoolean rhs = (ALBoolean)visit(ctx.expression(1));
			return lhs.and(rhs);
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitStringExpression(StringExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		String text = ctx.getText();
		// Remove the quotes
		text = text.substring(1, text.length() - 1);
		text = text.replaceAll("////(.)", "$1");
		return new ALString(text);
	}

	@Override
	public ALObject visitExpressionExpression(ExpressionExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return visit(ctx.expression());
	}

	@Override
	public ALObject visitAddExpression(AddExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt(lhs.getInt() + rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitSubtractExpression(SubtractExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALInt(lhs.getInt() - rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return visit(ctx.functionCall());
	}
	
	@Override
	public ALObject visitFunctionCall(FunctionCallContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		List<ExpressionContext> args = new ArrayList<ExpressionContext>();
		
		// If there are arguments, parse them
		if (ctx.arguments() != null && ctx.arguments().exprList() != null) {
			args = ctx.arguments().exprList().expression();
		}
		
		ALFunction function;
		
		// Check if it's a global function
		if (ctx.Identifier().size() == 1) {
			// Global function
			if ((function = globalFunctions.get(ctx.Identifier(0).getText())) != null) {
				return function.invoke(args, this, scope);
			}
		} else if (ctx.Identifier().size() == 2) {
			// Class function
			
			// First check if we're instantiating an object
			if (classes.containsKey(ctx.Identifier(0).getText()) &&
					ctx.Identifier(1).getText().equals("create")) {
				ALClass clazz = classes.get(ctx.Identifier(0).getText());
				return ALClass.create(clazz, scope);
			} else {
				// the first identfier will get us the object to call
				ALObject object = scope.resolve(ctx.Identifier(0).getText());
				
				// the second will be the name of the function
				String name = ctx.Identifier(1).getText();
				
				// call the function
				return object.invokeFunction(name, args, this, scope);
			}
			
			
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNilExpression(NilExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		return ALObject.Nil;
	}

	@Override
	public ALObject visitLtEqExpression(LtEqExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALInt lhs = (ALInt)visit(ctx.expression(0));
			ALInt rhs = (ALInt)visit(ctx.expression(1));
			
			return new ALBoolean(lhs.getInt() <= rhs.getInt());			
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);
		}
	}

	@Override
	public ALObject visitTernaryExpression(TernaryExpressionContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		try {
			ALBoolean condition = (ALBoolean)visit(ctx.expression(0));
			
			if (condition.getValue()) {
				return visit(ctx.expression(1));
			} else {
				return visit(ctx.expression(2));
			}
		} catch (ClassCastException e) {
			throw new ALEvalException(ctx);	
		}
	}

	@Override
	public ALObject visitBlock(BlockContext ctx) {
//		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
		
		scope = new ALScope(scope);
		for (StatementContext statementCtx : ctx.statements().statement()) {
			visit(statementCtx);
		}
		
		ExpressionContext expressionCtx;
		if ((expressionCtx = ctx.statements().expression()) != null) {
			returnValue.value = visit(expressionCtx);
			scope = scope.parent();
			throw returnValue;
		}
		
		scope = scope.parent();
		return ALObject.Void;
	}
	
}
