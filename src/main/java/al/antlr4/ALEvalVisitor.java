package main.java.al.antlr4;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import al.antlr4.AluminumBaseVisitor;
import al.antlr4.AluminumParser.AddExpressionContext;
import al.antlr4.AluminumParser.AndExpressionContext;
import al.antlr4.AluminumParser.AssignmentContext;
import al.antlr4.AluminumParser.BlockContext;
import al.antlr4.AluminumParser.BooleanExpressionContext;
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

public class ALEvalVisitor extends AluminumBaseVisitor<ALObject> {
	private static ALReturnValue returnValue = new ALReturnValue();
	private ALScope scope;
	private Map<String, ALFunction> functions;
	
	public ALEvalVisitor(ALScope scope, Map<String, ALFunction> functions) {
		this.scope = scope;
		this.functions = functions;
	}
	
	@Override
	public ALObject visitFunctionDecl(FunctionDeclContext ctx) {
		return ALObject.Void;
	}

	@Override
	public ALObject visitAssignment(AssignmentContext ctx) {
		ALObject newVal = visit(ctx.expression());
		String id = ctx.Identifier().getText();
		scope.assign(id,  newVal);
		
		return ALObject.Void;
	}

	@Override
	public ALObject visitIfStatement(IfStatementContext ctx) {
		if (visit(ctx.expression()).asBoolean()) {
			return visit(ctx.block());
		}
		
		return ALObject.Void;
	}

	@Override
	public ALObject visitWhileStatement(WhileStatementContext ctx) {
		while (visit(ctx.expression()).asBoolean()) {
			ALObject retVal = visit(ctx.block());
			if (retVal != ALObject.Void) {
				return retVal;
			}
		}
		
		return ALObject.Void;
	}


	@Override
	public ALObject visitLtExpression(LtExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));		
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() < rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitGtExpression(GtExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() > rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNotEqExpression(NotEqExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() != rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNumberExpression(NumberExpressionContext ctx) {
		return new ALObject(Integer.valueOf(ctx.getText()));
	}

	@Override
	public ALObject visitIdentifierExpression(IdentifierExpressionContext ctx) {
		return scope.resolve(ctx.Identifier().getText());
	}

	@Override
	public ALObject visitModulusExpression(ModulusExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() % rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNotExpression(NotExpressionContext ctx) {
		ALObject initVal = visit(ctx.expression());
		
		if (!initVal.isBoolean()) {
			throw new ALEvalException(ctx);
		}
		
		return new ALObject(!initVal.asBoolean());
	}

	@Override
	public ALObject visitMultiplyExpression(MultiplyExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (!lhs.isNumber() || !rhs.isNumber()) {
			throw new ALEvalException(ctx);
		}

		return new ALObject(lhs.asNumber() * rhs.asNumber());
	}

	@Override
	public ALObject visitBooleanExpression(BooleanExpressionContext ctx) {
		return new ALObject(Boolean.valueOf(ctx.getText()));
	}

	@Override
	public ALObject visitGtEqExpression(GtEqExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() >= rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitDivideExpression(DivideExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() / rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitOrExpression(OrExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isBoolean() && rhs.isBoolean()) {
			return new ALObject(lhs.asBoolean() || rhs.asBoolean());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitUnaryMinusExpression(UnaryMinusExpressionContext ctx) {
		ALObject value = visit(ctx.expression());
		
		if (value.isNumber()) {
			return new ALObject(-value.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitPowerExpression(PowerExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject((int)Math.pow(lhs.asNumber(), rhs.asNumber()));
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitEqExpression(EqExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs == null && rhs == null) {
			return new ALObject(true);
		} else if (lhs == null) {
			return new ALObject(false);
		} else {
			return new ALObject(lhs.equals(rhs));
		}
	}

	@Override
	public ALObject visitAndExpression(AndExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isBoolean() && rhs.isBoolean()) {
			return new ALObject(lhs.asBoolean() && rhs.asBoolean());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitStringExpression(StringExpressionContext ctx) {
		String text = ctx.getText();
		// Remove the quotes
		text = text.substring(1, text.length() - 1);
		text = text.replaceAll("////(.)", "$1");
		return new ALObject(text);
	}

	@Override
	public ALObject visitExpressionExpression(ExpressionExpressionContext ctx) {
		return visit(ctx.expression());
	}

	@Override
	public ALObject visitAddExpression(AddExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() + rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitSubtractExpression(SubtractExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() - rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitFunctionCallExpression(FunctionCallExpressionContext ctx) {
		List<ExpressionContext> args = new ArrayList<ExpressionContext>();
		
		if (ctx.functionCall().arguments().exprList() != null) {
			args = ctx.functionCall().arguments().exprList().expression();
		}
		
		ALFunction function;
		if ((function = functions.get(ctx.functionCall().Identifier().getText())) != null) {
			return function.invoke(args, functions, scope);
		}
		
		throw new ALEvalException(ctx);
	}
	
	@Override
	public ALObject visitFunctionCall(FunctionCallContext ctx) {
		List<ExpressionContext> args = new ArrayList<ExpressionContext>();
		
		if (ctx.arguments().exprList() != null) {
			args = ctx.arguments().exprList().expression();
		}
		
		ALFunction function;
		if ((function = functions.get(ctx.Identifier().getText())) != null) {
			return function.invoke(args, functions, scope);
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitNilExpression(NilExpressionContext ctx) {
		return ALObject.Nil;
	}

	@Override
	public ALObject visitLtEqExpression(LtEqExpressionContext ctx) {
		ALObject lhs = visit(ctx.expression(0));
		ALObject rhs = visit(ctx.expression(1));
		
		if (lhs.isNumber() && rhs.isNumber()) {
			return new ALObject(lhs.asNumber() <= rhs.asNumber());
		}
		
		throw new ALEvalException(ctx);
	}

	@Override
	public ALObject visitTernaryExpression(TernaryExpressionContext ctx) {
		ALObject condition = visit(ctx.expression(0));
		
		if (!condition.isBoolean()) {
			throw new ALEvalException(ctx);			
		}
		
		if (condition.asBoolean()) {
			ALObject retVal = new ALObject(visit(ctx.expression(1)).asBoolean()); 
			return retVal;
		} else {
			ALObject retVal = new ALObject(visit(ctx.expression(2)).asBoolean()); 
			return retVal;
		}
	}

	@Override
	public ALObject visitBlock(BlockContext ctx) {
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
