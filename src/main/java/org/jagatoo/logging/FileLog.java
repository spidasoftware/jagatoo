/**
 * Copyright (c) 2007-2009, JAGaToo Project Group all rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the 'Xith3D Project Group' nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) A
 * RISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE
 */
/**
 * :Id: FileLog.java,v 1.5 2003/02/24 00:13:53 wurp Exp $
 * 
 * :Log: FileLog.java,v $
 * Revision 1.5  2003/02/24 00:13:53  wurp
 * Formatted all java code for cvs (strictSunConvention.xml)
 * 
 * Revision 1.4  2001/06/20 04:05:42  wurp
 * added log4j.
 * 
 * Revision 1.3  2001/01/28 07:52:20  wurp
 * Removed <dollar> from Id and Log in log comments.
 * Added several new commands to AdminApp
 * Unfortunately, several other changes that I have lost track of.  Try diffing this
 * version with the previous one.
 * 
 * Revision 1.2  2000/12/16 22:07:33  wurp
 * Added Id and Log to almost all of the files that didn't have it.  It's
 * possible that the script screwed something up.  I did a commit and an update
 * right before I ran the script, so if a file is screwed up you should be able
 * to fix it by just going to the version before this one.
 */
package org.jagatoo.logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * This class implements the LogInterface and adds support for
 * writing logs to files.  The filename is specified in the constructor.
 * 
 * @author David Yazel
 * @author Marvin Froehlich (aka Qudus)
 */
public class FileLog implements LogInterface
{
    private int logLevel;
    private int channelFilter;
    private final FileOutputStream out;
    private final PrintStream prn;
    
    public final void setLogLevel( int logLevel )
    {
        this.logLevel = logLevel;
    }
    
    public final int getLogLevel()
    {
        return ( logLevel );
    }
    
    public final void setChannelFilter( int filter )
    {
        this.channelFilter = filter;
    }
    
    public final int getChannelFilter()
    {
        return ( channelFilter );
    }
    
    public final boolean acceptsChannel( LogChannel channel )
    {
        return ( ( channelFilter & channel.getID() ) > 0 );
    }
    
    /**
     * {@inheritDoc}
     */
    public void print( LogChannel channel, int logLevel, String message )
    {
        if ( ( acceptsChannel( channel ) ) && ( logLevel <= this.logLevel ) )
        {
            prn.print( message );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void println( LogChannel channel, int logLevel, String message )
    {
        if ( ( acceptsChannel( channel ) ) && ( logLevel <= this.logLevel ) )
        {
            prn.println( message );
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void flush()
    {
        prn.flush();
    }
    
    /**
     * {@inheritDoc}
     */
    public void close()
    {
        try
        {
            out.close();
            prn.close();
        }
        catch (Exception e)
        {
        }
    }
    
    public FileLog( int channelFilter, int logLevel, String filename ) throws FileNotFoundException
    {
        this.logLevel = logLevel;
        this.channelFilter = channelFilter;
        
        this.out = new FileOutputStream( filename );
        this.prn = new PrintStream( out, true );
    }
    
    public FileLog( int logLevel, String filename ) throws FileNotFoundException
    {
        this( 0xFFFFFFFF, logLevel, filename );
    }
    
    public FileLog( String filename ) throws FileNotFoundException
    {
        this( 0xFFFFFFFF, LogLevel.REGULAR, filename );
    }
}
