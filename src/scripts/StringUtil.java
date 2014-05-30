package scripts;

/*******************************************************************************
 * Copyright (c) 2004 Actuate Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Actuate Corporation  - initial API and implementation
 *******************************************************************************/

/**
 * Collection of string utilities.
 * 
 */

public class StringUtil
{

  /**
   * Trims the quotes.
   * <p>
   * For example,
   * <ul>
   * <li>("a.b") => a.b
   * <li>("a.b) => "a.b
   * <li>(a.b") => a.b"
   * </ul>
   * 
   * @param value
   *            the string may have quotes
   * @return the string without quotes
   */

  public static String trimQuotes( String value )
  {
    if ( value == null )
      return value;

    value = value.trim( );
    if ( value.startsWith( "\"" ) && value.endsWith( "\"" ) )
      return value.substring( 1, value.length( ) - 1 );
    
    return value;
  }

}
