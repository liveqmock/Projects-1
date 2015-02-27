package com.newsoft.frame.codegen.parameter;

/**
 * Define the parameter context factory, potential implementation includes:
 * create from database, create from PO class source.
 * 
 * @author guohb
 * 
 */
public interface ParamContextFactory {
	ParamContext create();
}
